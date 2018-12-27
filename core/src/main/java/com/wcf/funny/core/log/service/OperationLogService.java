package com.wcf.funny.core.log.service;

import com.wcf.funny.core.exception.PgSqlException;
import com.wcf.funny.core.log.entity.OperationLogInfo;

import java.util.List;

/**
 * @author WCF
 * @time 2018/5/1
 * @why 操作日志信息
 **/
public interface OperationLogService {
    /**
     * @param action
     * @param ip
     * @param author
     * @return void
     * @note 插入新的操作日志
     * @author WCF
     * @time 2018/6/14 22:15
     * @since v1.0
     **/
    void insertLog(String action, String ip, String author) throws PgSqlException;

    /**
     * @param page
     * @param limit
     * @return java.util.List<com.wcf.hellohome.read.model.WcfOperationLogInfo>
     * @note 获取日志信息
     * @author WCF
     * @time 2018/6/14 22:16
     * @since v1.0
     **/
    List<OperationLogInfo> getLogs(int page, int limit) throws PgSqlException;
}
