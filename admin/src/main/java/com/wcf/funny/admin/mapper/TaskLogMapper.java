package com.wcf.funny.admin.mapper;

import com.wcf.funny.admin.entity.ScheduleTaskInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/3/17
 * @function 任务记录映射器
 **/
@Mapper
public interface TaskLogMapper {
    /**
     * 功能描述：往数据库中插入一条任务记录
     *
     * @param info
     * @author wangcanfeng
     * @time 2019/3/17 13:16
     * @since v1.0
     **/
    @Insert("")
    void insertTask(ScheduleTaskInfo info);

    /**
     * 功能描述：查询任务记录
     *
     * @author wangcanfeng
     * @time 2019/3/17 13:16
     * @since v1.0
     **/
    @Select("")
    List<ScheduleTaskInfo> selectTaskList();

    /**
     * 功能描述： 更新任务记录信息
     *
     * @param info
     * @author wangcanfeng
     * @time 2019/3/17 13:37
     * @since v1.0
     **/
    @Update("")
    void updateTask(ScheduleTaskInfo info);

    /**
     * 功能描述：根据id查询任务信息
     *
     * @param id
     * @author wangcanfeng
     * @time 2019/3/17 13:49
     * @since v1.0
     **/
    @Delete("")
    void deleteTaskById(Integer id);

    /**
     * 功能描述：查询未执行完的任务
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/17 13:51
     * @since v1.0
     **/
    @Select("")
    List<ScheduleTaskInfo> searchUnFinishedTask();

    /**
     * 功能描述：  根据任务id查询任务信息
     *
     * @param taskId
     * @author wangcanfeng
     * @time 2019/3/17 14:58
     * @since v1.0
     **/
    @Select("")
    ScheduleTaskInfo selectTaskById(Integer taskId);
}
