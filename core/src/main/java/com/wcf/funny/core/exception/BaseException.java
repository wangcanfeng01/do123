package com.wcf.funny.core.exception;

import com.wcf.funny.core.exception.errorcode.CoreCode;

/**
 * @author WCF
 * @time 2018/6/15
 * @why 功能：基础异常
 **/
public abstract class BaseException extends RuntimeException {
    /**
     * 错误码
     */
    private String code;

    /**
     * 功能描述：  枚举构造函数
     *
     * @param coreCode
     * @return
     * @authorwangcanfengg
     * @2018/12/29123:5723:56
     * @sin* @param coreCode
     */
     public BaseException(CoreCode coreCode) {
        super(coreCode.getReason());
        this.code = coreCode.getCode();
    }

    /**
     * 功能描述：  携带异常信息的构造函数
     *
     * @param coreCode
     * @return
     * @author wangcanfeng
     * @time 2018/12/29 23:56
     * @since v1.0
     **/
    public BaseException(CoreCode coreCode, Throwable e) {
        super(coreCode.getReason(), e);
        this.code = coreCode.getCode();
    }


    /**
     * @param msg
     * @return
     * @note 错误信息
     * @authoWCFCF 2018/6/1581:015 1:00
     * @si* @param msg
     **/
    public BaseException(String msg) {
        super(msg);
    }

    /**
     * @return
     * @note 错误信息
     * @author WCF
     * @time 2018/6/15 1:00
     * @since v1.0
     **/
    public BaseException() {
        super();
    }


    /**
     * @param
     * @return java.lang.String
     * @note 获取错误码
     * @author WCF
     * @time 2018/6/14 22:35
     * @since v1.0
     **/
    public String getCode() {
        return this.code;
    }


    /**
     * @param code
     * @return void
     * @note 错误码
     * @author WCF
     * @time 2018/6/15 1:02
     * @since v1.0
     **/
    public void setCode(String code) {
        this.code = code;
    }
}
