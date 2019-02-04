package com.wcf.funny.core.exception;

import com.wcf.funny.core.exception.errorcode.CoreCode;

/**
 * @author wangcanfeng
 * @time 2019/2/4
 * @function 通用时间异常处理
 **/
public class TimeException extends BaseException {

    /**
     * 抛出携带错误码和错误信息的异常
     *
     * @param code
     * @param msg
     */
    public TimeException(String code, String msg) {
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
    public TimeException(CoreCode errorMessage, Throwable e) {
        super(errorMessage,e);
    }
}
