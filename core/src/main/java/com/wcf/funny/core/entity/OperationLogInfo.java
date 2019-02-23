package com.wcf.funny.core.entity;

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
     * 头像存储路径
     */
    private String facePath;

    /**
     * 操作对象
     */
    private String actionObject;

    /**
     * 操作者ip
     */
    private String ip;
    /**
     * 操作时间
     */
    private Integer createTime;
    /**
     * 操作内容信息
     */
    private String actionInfo;
    /**
     * 操作详情
     */
    private String details;

    /**
     * 操作结果 1表示成功  0表示失败
     */
    private Integer actionResult;
}
