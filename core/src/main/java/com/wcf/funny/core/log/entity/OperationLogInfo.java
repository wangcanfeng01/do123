package com.wcf.funny.core.log.entity;

import lombok.Data;

/**
 * @author WCF
 * @time 2018/12/26
 * @function 操作日志
 **/
@Data
public class OperationLogInfo {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 操作类型
     */
    private Integer actionType;

    /**
     * 操作名
     */
    private String authorName;

    /**
     * 操作对象
     */
    private String object;

    /**
     * 操作者ip
     */
    private String ip;
    /**
     * 操作时间
     */
    private Integer createTime;
    /**
     * 操作详情
     */
    private String details;

    /**
     * 操作结果
     */
    private Integer actionResult;
}
