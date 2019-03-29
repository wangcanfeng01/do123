package com.wcf.funny.job.service.impl;

import com.wcf.funny.job.constant.*;
import com.wcf.funny.job.entity.ScheduleTaskInfo;
import com.wcf.funny.job.exception.TaskException;
import com.wcf.funny.job.exception.errorcode.TaskErrorCode;
import com.wcf.funny.job.service.JobScheduleService;
import com.wcf.funny.job.service.TaskLogService;
import com.wcf.funny.core.exception.CommonException;
import com.wcf.funny.core.exception.errorcode.CommonCode;
import com.wcf.funny.core.utils.UuidUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;

/**
 * @author wangcanfeng
 * @time 2019/3/16
 * @function 任务调度服务接口实现
 **/
@Service
public class JobScheduleServiceImpl implements JobScheduleService {


    /**
     * 因为在配置中设定了这个bean的名称，这里就需要指定bean的名称，不然启动就会报错
     */
    @Autowired
    @Qualifier("funnyScheduler")
    private Scheduler scheduler;

    @Autowired
    private TaskLogService taskLogService;


    /**
     * 功能描述: 添加简单任务
     *
     * @param info
     * @return:void
     * @since: v1.0
     * @Author:wangcanfeng
     * @Date: 2019/3/15 17:00
     */
    @Override
    public void addJob(ScheduleTaskInfo info) {
        // 创建任务信息
        JobBuilder jobBuilder = JobBuilder.newJob(TaskGroupMap.getJobClazz(info.getTaskGroup()))
                .withIdentity(info.getTaskName(), info.getTaskGroup());
        // 将任务类型字符串转成枚举，顺便检验是否类型是存在的
        TaskType type = TaskType.valueOfString(info.getTaskType());
        Trigger trigger = null;
        switch (type) {
            // 单次执行的任务
            case SINGLE: {
                Date date = new Date();
                if (!ObjectUtils.isEmpty(info.getTriggerTime())) {
                    //如果是定时到某个时间点的任务，任务状态设置为未开始
                    info.setTaskStatus(TaskStatus.UNSTART.getInfo().toString());
                    info.setTaskInterval(TaskInterval.ONCE.getInfo().toString());
                    date.setTime(info.getTriggerTime());
                } else {
                    info.setTaskInterval(TaskInterval.RIGHT_NOW.getInfo().toString());
                    // 如果没有设置定时时间，将点火时间设置为当前时间
                    info.setTriggerTime(System.currentTimeMillis());
                }
                trigger = TriggerBuilder.newTrigger()
                        .withIdentity(UuidUtils.generateShortUuid())
                        .startAt(date)
                        .build();
                break;
            }
            // 多次定时执行的任务
            case MULTI: {
                TaskInterval interval = TaskInterval.valueOfInterval(info.getTaskInterval());
                trigger = TaskTriggerMap.getCronTrigger(interval);
                break;
            }
        }
        //往数据库中插入任务记录
        taskLogService.insertTask(info);
        jobBuilder.usingJobData("taskInfo", info.toJson());
        JobDetail jobDetail = jobBuilder.build();
        doSchedule(jobDetail, trigger, info);
    }

    /**
     * 功能描述： 服务器重启之后恢复任务
     *
     * @param info
     * @author wangcanfeng
     * @time 2019/3/22 21:02
     * @since v1.0
     **/
    @Override
    public void restartJob(ScheduleTaskInfo info) {
        //利用已有的任务信息，创建新的任务信息对象
        JobDetail jobDetail = JobBuilder.newJob(TaskGroupMap.getJobClazz(info.getTaskGroup()))
                .withIdentity(info.getTaskName(), info.getTaskGroup())
                .usingJobData("taskInfo", info.toJson()).build();
        //创建点火器
       TaskInterval interval= TaskInterval.valueOfInterval(info.getTaskInterval());
        Trigger trigger = TaskTriggerMap.getCronTrigger(interval);
        doSchedule(jobDetail, trigger, info);
    }

    /**
     * 功能描述: 修改任务Trigger，即修改任务的定时机制
     * 当前方法还未支持使用，因为trigger是预置的不能自定义，修改了一个trigger之后会导致所有的trigger都变动
     *
     * @param info
     * @return:void
     * @since: v1.0
     * @Author:wangcanfeng
     * @Date: 2019/3/15 17:00
     */
    @Override
    public void modifyJob(ScheduleTaskInfo info) {
        throw new CommonException(CommonCode.UNSUPPORTED_METHOD);
    }

    /**
     * 功能描述: 暂停任务，只支持定时任务的暂停，不支持单次任务，单次任务需要interrupt
     *
     * @return:void
     * @since: v1.0
     * @Author:wangcanfeng
     * @Date: 2019/3/15 17:00
     */
    @Override
    public void pauseJobById(Integer taskId) {
        //先查询任务记录
        ScheduleTaskInfo taskInfo = taskLogService.selectTaskById(taskId);
        if (TaskType.SINGLE.getInfo().equals(taskInfo.getTaskType())) {
            //如果是单次执行的任务，不支持暂停
            throw new TaskException(TaskErrorCode.TASK_UNSUPPORTED_PAUSED);
        }
        if (ObjectUtils.isEmpty(taskInfo)) {
            throw new TaskException(TaskErrorCode.TASK_LOG_NOT_EXIST);
        }
        JobKey key = new JobKey(taskInfo.getTaskName(), taskInfo.getTaskGroup());
        try {
            // 查看任务池中任务是否在存在，不存在直接抛出异常
            JobDetail detail = scheduler.getJobDetail(key);
            if (ObjectUtils.isEmpty(detail)) {
                throw new TaskException(TaskErrorCode.TASK_LOG_NOT_EXIST);
            }
            scheduler.pauseJob(key);
        } catch (SchedulerException e) {
            throw new TaskException(TaskErrorCode.TASK_PAUSE_FAILED, e);
        }
        // 任务状态设置成暂停
        taskInfo.setTaskStatus(TaskStatus.PAUSED.getInfo().toString());
        taskLogService.updateTask(taskInfo);
    }

    /**
     * 功能描述: 从暂停状态中恢复定时任务运行
     *
     * @return:void
     * @since: v1.0
     * @Author:wangcanfeng
     * @Date: 2019/3/15 17:00
     */
    @Override
    public void resumeJobById(Integer taskId) {
        //先查询任务记录
        ScheduleTaskInfo taskInfo = taskLogService.selectTaskById(taskId);
        if (ObjectUtils.isEmpty(taskInfo)) {
            throw new TaskException(TaskErrorCode.TASK_LOG_NOT_EXIST);
        }
        JobKey key = new JobKey(taskInfo.getTaskName(), taskInfo.getTaskGroup());
        try {
            scheduler.resumeJob(key);
        } catch (SchedulerException e) {
            throw new TaskException(TaskErrorCode.TASK_RESUME_FAILED, e);
        }
        // 任务状态设置成执行中
        taskInfo.setTaskStatus(TaskStatus.EXECUTING.getInfo().toString());
        taskLogService.updateTask(taskInfo);
    }

    /**
     * 功能描述: 删除任务
     *
     * @return:void
     * @since: v1.0
     * @Author:wangcanfeng
     * @Date: 2019/3/15 17:00
     */
    @Override
    public void deleteJobById(Integer taskId) {
        //先查询任务记录
        ScheduleTaskInfo taskInfo = taskLogService.selectTaskById(taskId);
        if (ObjectUtils.isEmpty(taskInfo)) {
            throw new TaskException(TaskErrorCode.TASK_LOG_NOT_EXIST);
        }
        JobKey key = new JobKey(taskInfo.getTaskName(), taskInfo.getTaskGroup());
        try {
            // 如果还在任务池中则进行删除
            JobDetail detail = scheduler.getJobDetail(key);
            if (!ObjectUtils.isEmpty(detail)) {
                scheduler.deleteJob(key);
            }
        } catch (SchedulerException e) {
            throw new TaskException(TaskErrorCode.DELETE_TASK_FAILED, e);
        }
        taskLogService.deleteTaskById(taskId);
    }

    /**
     * 功能描述：  将任务委托给调度器
     *
     * @param detail
     * @param trigger
     * @param info
     * @author wangcanfeng
     * @time 2019/3/22 21:10
     * @since v1.0
     **/
    private void doSchedule(JobDetail detail, Trigger trigger, ScheduleTaskInfo info) {
        try {
            scheduler.scheduleJob(detail, trigger);
        } catch (SchedulerException e) {
            //任务执行结果设置为失败
            info.setTaskResult(TaskResult.FAILED.getInfo().toString());
            info.setTaskStatus(TaskStatus.FINISHED.getInfo().toString());
            taskLogService.updateTask(info);
            throw new TaskException(TaskErrorCode.TASK_EXECUTE_FAILED, e);
        }
    }

}
