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
    @Insert("insert into info_task (task_name, create_time, task_status, task_type, task_creator, task_result," +
            "  update_time, task_interval, task_group, trigger_time)" +
            " VALUES (#{taskName}, #{createTime}, #{taskStatus}, #{taskType}, #{taskCreator}, #{taskResult}," +
            "  #{updateTime}, #{taskInterval}, #{taskGroup}, #{triggerTime} )")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertTask(ScheduleTaskInfo info);

    /**
     * 功能描述：查询任务记录
     *
     * @author wangcanfeng
     * @time 2019/3/17 13:16
     * @since v1.0
     **/
    @Select(" SELECT id, task_name as taskName, create_time as createTime, task_status as taskStatus," +
            " task_type as taskType,  task_creator as taskCreator, task_result as taskResult, update_time as updateTime," +
            " task_interval as taskInterval, task_group as taskGroup, trigger_time as triggerTime" +
            " FROM info_task ORDER BY id desc")
    List<ScheduleTaskInfo> selectTaskList();

    /**
     * 功能描述： 更新任务记录信息
     *
     * @param info
     * @author wangcanfeng
     * @time 2019/3/17 13:37
     * @since v1.0
     **/
    @Update("update info_task set task_status=#{taskStatus}, task_result=#{taskResult}, update_time=#{updateTime}, " +
            "  task_group=#{taskGroup} where id=#{id}")
    void updateTask(ScheduleTaskInfo info);

    /**
     * 功能描述：根据id查询任务信息
     *
     * @param id
     * @author wangcanfeng
     * @time 2019/3/17 13:49
     * @since v1.0
     **/
    @Delete("delete from info_task where id=#{id}")
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
    @Select(" SELECT id, task_name as taskName, create_time as createTime, task_status as taskStatus," +
            " task_type as taskType,  task_creator as taskCreator, task_result as taskResult, update_time as updateTime," +
            " task_interval as taskInterval, task_group as taskGroup, trigger_time as triggerTime" +
            " FROM info_task where id=#{taskId}")
    ScheduleTaskInfo selectTaskById(Integer taskId);
}
