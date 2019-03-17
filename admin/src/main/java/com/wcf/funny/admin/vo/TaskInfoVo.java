package com.wcf.funny.admin.vo;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/3/17
 * @function 任务视图信息
 **/
@Data
public class TaskInfoVo {
    /**
     * 序列号
     */
    private Integer id;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 任务状态
     */
    private String taskStatus;
    /**
     * 任务类型
     */
    private String taskType;
    /**
     * 任务创建者
     */
    private String taskCreator;
    /**
     * 任务执行结果
     */
    private String taskResult;
    /**
     * 任务更新时间
     */
    private String updateTime;
    /**
     * 任务执行周期
     */
    private String taskPeriod;
    /**
     * 任务分组
     */
    private String taskGroup;
    /**
     * 任务点火时间
     */
    private String triggerTime;
}
