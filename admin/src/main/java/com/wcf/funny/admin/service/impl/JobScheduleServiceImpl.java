package com.wcf.funny.admin.service.impl;

import com.wcf.funny.admin.constant.*;
import com.wcf.funny.admin.entity.ScheduleTaskInfo;
import com.wcf.funny.admin.exception.TaskException;
import com.wcf.funny.admin.exception.errorcode.TaskErrorCode;
import com.wcf.funny.admin.job.TestJob;
import com.wcf.funny.admin.service.JobScheduleService;
import com.wcf.funny.admin.service.TaskLogService;
import com.wcf.funny.core.exception.CommonException;
import com.wcf.funny.core.exception.errorcode.CommonCode;
import com.wcf.funny.core.utils.FunnyTimeUtils;
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
        JobDetail jobDetail = JobBuilder.newJob(TaskGroupMap.getJobClazz(info.getTaskGroup()))
                .withIdentity(info.getTaskName() + "_" + FunnyTimeUtils.now(), info.getTaskGroup())
                .build();
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
                    date.setTime(info.getTriggerTime());
                }else {
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
                trigger = TaskTriggerMap.getCronTrigger(info.getTaskInterval());
                break;
            }
        }
        //往数据库中插入任务记录
        taskLogService.insertTask(info);
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            //任务执行结果设置为失败
            info.setTaskResult(TaskResult.FAILED.getInfo().toString());
            info.setTaskStatus(TaskStatus.FINISHED.getInfo().toString());
            taskLogService.updateTask(info);
            throw new TaskException(TaskErrorCode.TASK_EXECUTE_FAILED, e);
        }
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
            scheduler.deleteJob(key);
        } catch (SchedulerException e) {
            throw new TaskException(TaskErrorCode.DELETE_TASK_FAILED, e);
        }
        taskLogService.deleteTaskById(taskId);
    }

}
