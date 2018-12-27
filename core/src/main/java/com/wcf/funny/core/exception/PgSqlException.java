package com.wcf.funny.core.exception;

import com.wcf.funny.core.exception.errorcode.ErrorMessage;

/**
 * @author WCF
 * @time 2018/6/12
 * @function: 功能：pg数据库异常
 **/
public class PgSqlException extends BaseException {
    /**
     * 抛出默认异常
     */
    public PgSqlException() {
        super();
        setCode("00000000");
        setChinese("默认数据库异常");
        setMsg("Default sql error");
    }

    /**
     * 携带错误信息
     *
     * @param msg
     */
    public PgSqlException(String msg) {
        super(msg);
        setChinese("");
        setMsg(msg);
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
        setChinese("");
        setMsg(msg);
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
    public PgSqlException(ErrorMessage errorMessage) {
        super(errorMessage.getReason());
        setChinese(errorMessage.getChinese());
        setMsg(errorMessage.getReason());
        setCode(errorMessage.getCode());
    }
}
