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
     * 操作
     */
    private String action;

    /**
     * 操作者名
     */
    private String author;

    /**
     * 操作模块
     */
    private String module;

    /**
     * 操作者ip
     */
    private String ip;
    /**
     * 操作时间
     */
    private Integer createTime;
}
