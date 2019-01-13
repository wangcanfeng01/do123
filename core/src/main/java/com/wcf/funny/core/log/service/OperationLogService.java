package com.wcf.funny.core.log.service;

import com.github.pagehelper.PageInfo;
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
     * @param logInfo 日志信息
     * @return void
     * @note 插入新的操作日志
     * @author WCF
     * @time 2018/6/14 22:15
     * @since v1.0
     **/
    void insertLog(OperationLogInfo logInfo) ;

    /**
     * @param page
     * @param limit
     * @return java.util.List<com.wcf.hellohome.read.model.WcfOperationLogInfo>
     * @note 获取日志信息
     * @author WCF
     * @time 2018/6/14 22:16
     * @since v1.0
     **/
    PageInfo<OperationLogInfo> getLogs(int page, int limit);
}
