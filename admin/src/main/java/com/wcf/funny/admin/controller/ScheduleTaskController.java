package com.wcf.funny.admin.controller;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.admin.constant.TaskGroup;
import com.wcf.funny.admin.constant.TaskInterval;
import com.wcf.funny.admin.constant.TaskResult;
import com.wcf.funny.admin.constant.TaskStatus;
import com.wcf.funny.admin.entity.ScheduleTaskInfo;
import com.wcf.funny.admin.service.JobScheduleService;
import com.wcf.funny.admin.service.TaskLogService;
import com.wcf.funny.admin.vo.TaskInfoVo;
import com.wcf.funny.admin.vo.req.TaskReq;
import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.reponse.PageResponse;
import com.wcf.funny.core.utils.FunnyTimeUtils;
import com.wcf.funny.core.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    private TaskLogService taskLogService;

    /**
     * 功能描述：  创建新的任务
     *
     * @param req
     * @author wangcanfeng
     * @time 2019/3/17 12:51
     * @since v1.0
     **/
    @PostMapping("/addJob")
    public BaseResponse addTask(@RequestBody TaskReq req) {
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
        return BaseResponse.ok();
    }

    /**
     * 功能描述：  返回任务列表分页信息
     *
     * @param currentPage
     * @param pageSize
     * @author wangcanfeng
     * @time 2019/3/17 16:08
     * @since v1.0
     **/
    @GetMapping("/taskList")
    public BaseResponse<List<TaskInfoVo>> taskList(@RequestParam("currentPage") Integer currentPage,
                                                   @RequestParam("pageSize") Integer pageSize) {
        PageInfo<TaskInfoVo> pageInfo = taskLogService.selectTaskList(currentPage, pageSize);
        return new PageResponse<>(pageInfo);
    }

    /**
     * 功能描述：  暂停任务
     *
     * @param taskId
     * @author wangcanfeng
     * @time 2019/3/17 16:13
     * @since v1.0
     **/
    @GetMapping("/pauseJob")
    public BaseResponse pauseJob(@RequestParam("taskId") Integer taskId) {
        scheduleService.pauseJobById(taskId);
        return BaseResponse.ok();
    }


    /**
     * 功能描述：删除任务
     *
     * @param taskId
     * @author wangcanfeng
     * @time 2019/3/17 16:15
     * @since v1.0
     **/
    @GetMapping("/deleteJob")
    public BaseResponse deleteJob(@RequestParam("taskId") Integer taskId) {

        scheduleService.deleteJobById(taskId);
        return BaseResponse.ok();
    }

    /**
     * 功能描述：恢复暂停的任务
     *
     * @param taskId
     * @author wangcanfeng
     * @time 2019/3/17 16:15
     * @since v1.0
     **/
    @GetMapping("/resumeJob")
    public BaseResponse resumeJob(@RequestParam("taskId") Integer taskId) {
        scheduleService.resumeJobById(taskId);
        return BaseResponse.ok();
    }
}
