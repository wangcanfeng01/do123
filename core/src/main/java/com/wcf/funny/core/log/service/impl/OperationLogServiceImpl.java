package com.wcf.funny.core.log.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wcf.funny.core.exception.PgSqlException;
import com.wcf.funny.core.exception.errorcode.LogErrorCode;
import com.wcf.funny.core.log.entity.OperationLogInfo;
import com.wcf.funny.core.log.mapper.OperationLogMapper;
import com.wcf.funny.core.log.service.OperationLogService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author WCF
 * @time 2018/12/26
 * @function 日志持久化服务
 **/
@Service
@Log4j2
public class OperationLogServiceImpl implements OperationLogService {
    @Autowired
    private OperationLogMapper logMapper;

    /**
     * @param logInfo 日志信息
     * @return void
     * @note 插入新的操作日志
     * @author WCF
     * @time 2018/6/14 22:15
     * @since v1.0
     **/
    public void insertLog(OperationLogInfo logInfo) {
        try {
            logMapper.insertLog(logInfo);
        } catch (Exception e) {
            throw new PgSqlException(LogErrorCode.INSERT_OPERATION_ERROR, e);
        }
    }

    /**
     * @param page
     * @param limit
     * @return java.util.List<com.wcf.hellohome.read.model.WcfOperationLogInfo>
     * @note 获取日志信息
     * @author WCF
     * @time 2018/6/14 22:16
     * @since v1.0
     **/
    public PageInfo<OperationLogInfo> getLogs(int page, int limit) {
        //设置分页
        PageHelper.startPage(page, limit);
        try {
            return new PageInfo<>(logMapper.getLogsList());
        } catch (Exception e) {
            throw new PgSqlException(LogErrorCode.SELECT_OPERATION_ERROR, e);
        }
    }

    /**
     * @param page  页码
     * @param limit 单页大小
     * @param start 起始时间
     * @param end   结束时间
     * @return java.util.List<com.wcf.hellohome.read.model.WcfOperationLogInfo>
     * @note 获取日志信息
     * @author WCF
     * @time 2018/6/14 22:16
     * @since v1.0
     **/
    @Override
    public PageInfo<OperationLogInfo> getLogsByTime(int page, int limit, int start, int end) {
        //设置分页
        PageHelper.startPage(page, limit);
        try {
            return new PageInfo<>(logMapper.getLogsListByTime(start,end));
        } catch (Exception e) {
            throw new PgSqlException(LogErrorCode.SELECT_OPERATION_ERROR, e);
        }
    }
}
