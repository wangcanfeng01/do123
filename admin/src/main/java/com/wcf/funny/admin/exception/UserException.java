package com.wcf.funny.admin.exception;

import com.wcf.funny.core.exception.BaseException;
import com.wcf.funny.core.exception.errorcode.CoreCode;

/**
 * @author wangcanfeng
 * @time 2018/12/29
 * @function 用户信息异常
 **/
public class UserException extends BaseException {
    /**
     * @param errorMessage
     * @return
     * @note 通过枚举
     * @author WCF
     * @time 2018/6/13 22:48
     * @since v1.0
     **/
    public UserException(CoreCode errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    /**
     * @param errorMessage
     * @return
     * @note 通过枚举
     * @author WCF
     * @time 2018/6/13 22:48
     * @since v1.0
     **/
    public UserException(CoreCode errorMessage) {
        super(errorMessage);
    }

}
