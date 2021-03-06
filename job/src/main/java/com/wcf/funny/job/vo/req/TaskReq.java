package com.wcf.funny.job.vo.req;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/3/16
 * @function 任务信息请求对象
 **/
@Data
public class TaskReq {
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 任务类型
     */
    private String taskType;
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
    private String triggerTime;
}
