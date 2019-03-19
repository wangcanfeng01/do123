package com.wcf.funny.admin.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/3/15
 * @function 任务信息
 **/
@Data
public class ScheduleTaskInfo {
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
    private Integer createTime;
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
    private Integer updateTime;
    /**
     * 任务执行间隔
     */
    private String taskInterval;
    /**
     * 任务分组
     */
    private String taskGroup;
    /**
     * 任务点火时间
     */
    private Long triggerTime;

    public String toJson() {
        return JSON.toJSONString(this);
    }
}
