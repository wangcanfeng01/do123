package com.wcf.funny.job.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wcf.funny.job.constant.*;
import com.wcf.funny.job.entity.ScheduleTaskInfo;
import com.wcf.funny.job.exception.errorcode.TaskErrorCode;
import com.wcf.funny.job.mapper.TaskLogMapper;
import com.wcf.funny.job.service.TaskLogService;
import com.wcf.funny.job.vo.TaskInfoVo;
import com.wcf.funny.core.exception.PgSqlException;
import com.wcf.funny.core.utils.FunnyTimeUtils;
import com.wcf.funny.core.utils.I18Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/3/17
 * @function 任务记录接口实现类
 **/
@Service
public class TaskLogServiceImpl implements TaskLogService {

    @Autowired
    private TaskLogMapper taskLogMapper;

    /**
     * 功能描述：往数据库中插入一条任务记录
     *
     * @param info@author wangcanfeng
     * @time 2019/3/17 13:16
     * @since v1.0
     **/
    @Override
    public void insertTask(ScheduleTaskInfo info) {
        try {
            taskLogMapper.insertTask(info);
        } catch (Exception e) {
            throw new PgSqlException(TaskErrorCode.INSERT_TASK_FAILED, e);
        }
    }

    /**
     * 功能描述：查询任务记录
     *
     * @param currentPage
     * @param pageSize
     * @author wangcanfeng
     * @time 2019/3/17 13:16
     * @since v1.0
     **/
    @Override
    public PageInfo<TaskInfoVo> selectTaskList(Integer currentPage, Integer pageSize) {
        try {
            PageHelper.startPage(currentPage, pageSize);
            List<ScheduleTaskInfo> list = taskLogMapper.selectTaskList();
            return convertPage(list);
        } catch (Exception e) {
            throw new PgSqlException(TaskErrorCode.SEARCH_TASK_FAILED, e);
        }
    }

    /**
     * 功能描述： 更新任务记录信息
     *
     * @param info
     * @author wangcanfeng
     * @time 2019/3/17 13:37
     * @since v1.0
     **/
    @Override
    public void updateTask(ScheduleTaskInfo info) {
        try {
            taskLogMapper.updateTask(info);
        } catch (Exception e) {
            throw new PgSqlException(TaskErrorCode.UPDATE_TASK_FAILED, e);
        }
    }

    /**
     * 功能描述：根据id查询任务信息
     *
     * @param id
     * @author wangcanfeng
     * @time 2019/3/17 13:49
     * @since v1.0
     **/
    @Override
    public void deleteTaskById(Integer id) {
        try {
            taskLogMapper.deleteTaskById(id);
        } catch (Exception e) {
            throw new PgSqlException(TaskErrorCode.DELETE_TASK_FAILED, e);
        }
    }

    /**
     * 功能描述：查询未执行完的任务
     *
     * @author wangcanfeng
     * @time 2019/3/17 13:51
     * @since v1.0
     **/
    @Override
    public List<ScheduleTaskInfo> searchUnFinishedTask() {
        try {
            return taskLogMapper.searchUnFinishedTask();
        } catch (Exception e) {
            throw new PgSqlException(TaskErrorCode.SEARCH_TASK_FAILED, e);
        }
    }

    /**
     * 功能描述：  根据任务id查询任务信息
     *
     * @param taskId
     * @author wangcanfeng
     * @time 2019/3/17 14:58
     * @since v1.0
     **/
    @Override
    public ScheduleTaskInfo selectTaskById(Integer taskId) {
        try {
            return taskLogMapper.selectTaskById(taskId);
        } catch (Exception e) {
            throw new PgSqlException(TaskErrorCode.SEARCH_TASK_FAILED, e);
        }
    }


    /**
     * 功能描述：将数据库中的信息转成视图信息
     *
     * @param list
     * @author wangcanfeng
     * @time 2019/3/17 15:41
     * @since v1.0
     **/
    private PageInfo<TaskInfoVo> convertPage(List<ScheduleTaskInfo> list) {
        PageInfo<ScheduleTaskInfo> pageInfo = new PageInfo<>(list);
        PageInfo<TaskInfoVo> pageVo = new PageInfo<>();
        if (ObjectUtils.isEmpty(list)) {
            return pageVo;
        } else {
            List<TaskInfoVo> vos = new ArrayList<>();
            pageInfo.getList().forEach(task -> {
                TaskInfoVo vo = new TaskInfoVo();
                vo.setId(task.getId());
                vo.setTaskName(task.getTaskName());
                vo.setCreateTime(FunnyTimeUtils.getTimeByUnixTime(task.getCreateTime()));
                //任务状态信息翻译
                vo.setTaskStatus(I18Utils.getInfoTranslation(TaskStatus.valueOfStatus(task.getTaskStatus())));
                //任务类型翻译
                vo.setTaskType(I18Utils.getInfoTranslation(TaskType.valueOfString(task.getTaskType())));
                vo.setTaskCreator(task.getTaskCreator());
                //任务结果翻译
                vo.setTaskResult(I18Utils.getInfoTranslation(TaskResult.valueOfString(task.getTaskResult())));
                vo.setUpdateTime(FunnyTimeUtils.getTimeByUnixTime(task.getUpdateTime()));
                //任务周期翻译
                vo.setTaskPeriod(I18Utils.getInfoTranslation(TaskInterval.valueOfInterval(task.getTaskInterval())));
                //任务组翻译
                vo.setTaskGroup(I18Utils.getInfoTranslation(TaskGroup.valueOfGroup(task.getTaskGroup())));
                vo.setTriggerTime(FunnyTimeUtils.getTimeByMillsTime(task.getTriggerTime()));
                vos.add(vo);
            });
            pageVo.setTotal(pageInfo.getTotal());
            pageVo.setList(vos);
        }
        return pageVo;
    }
}
