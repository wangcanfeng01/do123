package com.wcf.funny.admin.service;

import com.github.pagehelper.PageInfo;
import com.wcf.funny.admin.entity.ScheduleTaskInfo;
import com.wcf.funny.admin.vo.TaskInfoVo;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/3/17
 * @function 任务记录信息接口
 **/
public interface TaskLogService {

    /**
     * 功能描述：往数据库中插入一条任务记录
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/17 13:16
     * @since v1.0
     **/
    void insertTask(ScheduleTaskInfo info);

    /**
     * 功能描述：查询任务记录
     *
     * @param currentPage
     * @param pageSize
     * @author wangcanfeng
     * @time 2019/3/17 13:16
     * @since v1.0
     **/
    PageInfo<TaskInfoVo> selectTaskList(Integer currentPage, Integer pageSize);

    /**
     * 功能描述： 更新任务记录信息
     *
     * @param info
     * @author wangcanfeng
     * @time 2019/3/17 13:37
     * @since v1.0
     **/
    void updateTask(ScheduleTaskInfo info);

    /**
     * 功能描述：根据id查询任务信息
     *
     * @param id
     * @author wangcanfeng
     * @time 2019/3/17 13:49
     * @since v1.0
     **/
    void deleteTaskById(Integer id);

    /**
     * 功能描述：查询未执行完的任务
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/17 13:51
     * @since v1.0
     **/
    List<ScheduleTaskInfo> searchUnFinishedTask();

    /**
     * 功能描述：  根据任务id查询任务信息
     *
     * @param taskId
     * @author wangcanfeng
     * @time 2019/3/17 14:58
     * @since v1.0
     **/
    ScheduleTaskInfo selectTaskById(Integer taskId);
}
