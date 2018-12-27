package com.wcf.funny.core.log.service.impl;

import com.wcf.funny.core.exception.PgSqlException;
import com.wcf.funny.core.log.entity.OperationLogInfo;
import com.wcf.funny.core.log.service.OperationLogService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WCF
 * @time 2018/12/26
 * @function 日志持久化服务
 **/
@Service
@Log4j2
public class OprationLogServiceImpl implements OperationLogService {

    public void insertLog(String action, String ip, String author) throws PgSqlException {

    }

    public List<OperationLogInfo> getLogs(int page, int limit) throws PgSqlException {
        return null;
    }
}
