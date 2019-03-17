package com.wcf.funny.admin.controller;

import com.wcf.funny.admin.constant.TaskGroup;
import com.wcf.funny.admin.constant.TaskInterval;
import com.wcf.funny.admin.constant.TaskResult;
import com.wcf.funny.admin.constant.TaskStatus;
import com.wcf.funny.admin.entity.ScheduleTaskInfo;
import com.wcf.funny.admin.service.JobScheduleService;
import com.wcf.funny.admin.vo.req.TaskReq;
import com.wcf.funny.core.utils.FunnyTimeUtils;
import com.wcf.funny.core.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangcanfeng
 * @time 2019/3/17
 * @function 任务调度controller
 **/
@RestController
@RequestMapping("/ui/task")
public class ScheduleTaskController {
    @Autowired
    private JobScheduleService scheduleService;

    /**
     * 功能描述：  创建新的任务
     *
     * @param req
     * @author wangcanfeng
     * @time 2019/3/17 12:51
     * @since v1.0
     **/
    @GetMapping("/addJob")
    public void addTask(@RequestBody TaskReq req) {
        ScheduleTaskInfo taskInfo = new ScheduleTaskInfo();
        taskInfo.setTaskName(req.getTaskName());
        taskInfo.setCreateTime(FunnyTimeUtils.nowUnix());
        // 默认任务开始状态为执行中
        taskInfo.setTaskStatus(TaskStatus.EXECUTING.getInfo().toString());
        //类型检测在service中，避免重复检测
        taskInfo.setTaskType(req.getTaskType());
        taskInfo.setTaskCreator(RequestUtils.getUserName());
        taskInfo.setUpdateTime(FunnyTimeUtils.nowUnix());
        // 检测任务的执行间隔
        TaskInterval interval = TaskInterval.valueOfInterval(req.getTaskInterval());
        taskInfo.setTaskInterval(interval.getInfo().toString());
        //检测任务组
        TaskGroup group = TaskGroup.valueOfGroup(req.getTaskGroup());
        taskInfo.setTaskGroup(group.getInfo().toString());
        taskInfo.setTriggerTime(FunnyTimeUtils.getMillsTime(req.getTriggerTime()));
        //默认任务结果为执行成功
        taskInfo.setTaskResult(TaskResult.SUCCESS.getInfo().toString());
        scheduleService.addJob(taskInfo);
    }

    @GetMapping("/select")
    public void select() {
//        scheduleService.searchJobByNameAndGroup("", "");
    }
}
