package com.wcf.funny.home.exception.errorcode;

import com.wcf.funny.core.exception.errorcode.CoreCode;

/**
 * @author wangcanfeng
 * @time 2019/4/14
 * @function
 **/
public enum  MessageErrorCode implements CoreCode{
    // 插入留言信息异常
    INSERT_MESSAGE_FAILED("0x06010001","insert message failed"),
    //查询留言信息异常
    SELECT_MESSAGE_FAILED("0x06010002","select message failed"),
    // 更新留言信息异常
    UPDATE_MESSAGE_FAILED("0x06010003","update message failed"),
    ;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 状态码
     */
    private String code;

    MessageErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    /**
     * @return java.lang.String
     * @note 获取状态码
     * @author WCF
     * @time 2018/12/28 22:49
     * @since v1.0
     **/
    @Override
    public String getCode() {
        return code;
    }

    /**
     * @return java.lang.String
     * @note 获取原因信息
     * @author WCF
     * @time 2018/12/28 22:49
     * @since v1.0
     **/
    @Override
    public String getReason() {
        return msg;
    }
}
