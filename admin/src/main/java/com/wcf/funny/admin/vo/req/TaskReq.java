package com.wcf.funny.admin.vo.req;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/3/16
 * @function 任务信息请求对象
 **/
@Data
public class TaskReq {
    /**
     * 序列号
     */
    private Integer id;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 任务类型
     */
    private String taskType;
    /**
     * 任务执行结果
     */
    private String taskResult;
    /**
     * 任务执行间隔
     */
    private String taskInterval;

    /**
     * 任务分组
     */
    private String taskGroup;

    /**
     * 点火时间
     */
    private String TriggerTime;
}
