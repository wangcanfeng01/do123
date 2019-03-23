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
import com.wcf.funny.core.entity.CodeAndName;
import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.reponse.ListResponse;
import com.wcf.funny.core.reponse.PageResponse;
import com.wcf.funny.core.utils.FunnyTimeUtils;
import com.wcf.funny.core.utils.I18Utils;
import com.wcf.funny.core.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        //给任务加上时间后缀
        taskInfo.setTaskName(req.getTaskName()+ "_" + FunnyTimeUtils.onlyTime());
        taskInfo.setCreateTime(FunnyTimeUtils.nowUnix());
        // 默认任务开始状态为执行中
        taskInfo.setTaskStatus(TaskStatus.EXECUTING.getInfo().toString());
        //类型检测在service中，避免重复检测
        taskInfo.setTaskType(req.getTaskType());
        taskInfo.setTaskCreator(RequestUtils.getUserName());
        taskInfo.setUpdateTime(FunnyTimeUtils.nowUnix());
        // 检测任务的执行间隔,间隔类型检测在service中，避免重复检测
        taskInfo.setTaskInterval(req.getTaskInterval());
        //检测任务组
        TaskGroup group = TaskGroup.valueOfGroup(req.getTaskGroup());
        taskInfo.setTaskGroup(group.getInfo().toString());
        if(!ObjectUtils.isEmpty(req.getTriggerTime())){
            taskInfo.setTriggerTime(FunnyTimeUtils.getMillsTimeUseTime(req.getTriggerTime()));
        }
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
    @PutMapping("/pauseJob")
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
    @DeleteMapping("/deleteJob")
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
    @PutMapping("/resumeJob")
    public BaseResponse resumeJob(@RequestParam("taskId") Integer taskId) {
        scheduleService.resumeJobById(taskId);
        return BaseResponse.ok();
    }

    /**
     * 功能描述：  获取执行周期的列表
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/19 21:56
     * @since v1.0
     **/
    @GetMapping("/intervalList")
    public BaseResponse<List<CodeAndName>> intervalList() {
        List<CodeAndName> codeAndNames = new ArrayList<>();
        TaskInterval[] intervals = TaskInterval.values();
        for (TaskInterval interval : intervals) {
            CodeAndName codeAndName = new CodeAndName();
            codeAndName.setName(I18Utils.getInfoTranslation(interval));
            codeAndName.setCode(interval.getInfo().toString());
            codeAndNames.add(codeAndName);
        }
        return new ListResponse<>(codeAndNames);
    }

    /**
     * 功能描述：  获取任务组的列表
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/19 21:56
     * @since v1.0
     **/
    @GetMapping("/groupList")
    public BaseResponse<List<CodeAndName>> groupList() {
        List<CodeAndName> codeAndNames = new ArrayList<>();
        TaskGroup[] groups = TaskGroup.values();
        for (TaskGroup group : groups) {
            CodeAndName codeAndName = new CodeAndName();
            codeAndName.setName(I18Utils.getInfoTranslation(group));
            codeAndName.setCode(group.getInfo().toString());
            codeAndNames.add(codeAndName);
        }
        return new ListResponse<>(codeAndNames);
    }
}
