package com.wcf.funny.admin.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wcf.funny.admin.constant.TaskResult;
import com.wcf.funny.admin.constant.TaskStatus;
import com.wcf.funny.admin.constant.TaskType;
import com.wcf.funny.admin.entity.ScheduleTaskInfo;
import com.wcf.funny.admin.exception.errorcode.TaskErrorCode;
import com.wcf.funny.admin.service.ServerInfoService;
import com.wcf.funny.admin.service.TaskLogService;
import lombok.extern.log4j.Log4j2;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author wangcanfeng
 * @time 2019/3/15
 * @function 系统信息统计任务
 **/
@Log4j2
@Component
public class SystemInfoStatisticJob implements Job {

    @Autowired
    private ServerInfoService serverInfoService;
    @Autowired
    private TaskLogService taskLogService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // 获取任务信息
        String json = jobExecutionContext.getMergedJobDataMap().getString("taskInfo");
        ScheduleTaskInfo task = JSON.parseObject(json, ScheduleTaskInfo.class);
        try {
            serverInfoService.insertServerInfo();
        } catch (Exception e) {
            log.error("task executes failed, details:" + json);
            //任务结果设置为失败
            task.setTaskResult(TaskResult.FAILED.getInfo().toString());
        }
        //如果是单次的任务
        if (TaskType.SINGLE.getInfo().equals(task.getTaskType())) {
            task.setTaskStatus(TaskStatus.FINISHED.getInfo().toString());
        }
        //更新任务信息,内部就将异常处理掉
        try {
            taskLogService.updateTask(task);
        } catch (Exception e) {
            log.error(TaskErrorCode.UPDATE_TASK_FAILED, e);
        }
    }
}
