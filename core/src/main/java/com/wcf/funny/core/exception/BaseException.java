package com.wcf.funny.core.exception;

/**
 * @author WCF
 * @time 2018/6/15
 * @why 功能：基础异常
 **/
public class BaseException extends Exception{
    /**
     * 错误信息
     */
    private String msg;
    /**
     * 错误码
     */
    private String code;

    /**
     * 中文异常信息
     */
    private String chinese;

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
     * @note 获取信息
     * @author WCF
     * @time 2018/6/14 22:35
     * @since v1.0
     **/
    public String getMsg() {
        return this.msg;
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
     * @param
     * @return java.lang.String
     * @note 获取中文异常信息
     * @author WCF
     * @time 2018/6/15 0:49
     * @since v1.0
     **/
    public String getChinese() {
        return this.chinese;
    }

    /**
     * @param msg
     * @return void
     * @note 错误信息
     * @author WCF
     * @time 2018/6/15 1:02
     * @since v1.0
     **/
    public void setMsg(String msg) {
        this.msg = msg;
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

    /**
     * @param chinese
     * @return void
     * @note 中文错误信息
     * @author WCF
     * @time 2018/6/15 1:02
     * @since v1.0
     **/
    public void setChinese(String chinese) {
        this.chinese = chinese;
    }


}
