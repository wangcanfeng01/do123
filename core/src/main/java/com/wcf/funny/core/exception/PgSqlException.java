package com.wcf.funny.core.exception;

import com.wcf.funny.core.exception.errorcode.CoreCode;

/**
 * @author WCF
 * @time 2018/6/12
 * @function: 功能：pg数据库异常
 **/
public class PgSqlException extends BaseException {

    /**
     * 携带错误信息
     *
     * @param msg
     */
    public PgSqlException(String msg) {
        super(msg);
        setCode("00000000");
    }

    /**
     * 抛出携带错误码和错误信息的异常
     *
     * @param code
     * @param msg
     */
    public PgSqlException(String code, String msg) {
        super(msg);
        setCode(code);
    }

    /**
     * @param errorMessage
     * @return
     * @note 通过枚举
     * @author WCF
     * @time 2018/6/13 22:48
     * @since v1.0
     **/
    public PgSqlException(CoreCode errorMessage,Throwable e) {
        super(errorMessage,e);
    }
}
