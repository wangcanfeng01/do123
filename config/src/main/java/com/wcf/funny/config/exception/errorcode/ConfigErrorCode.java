package com.wcf.funny.config.exception.errorcode;

import com.wcf.funny.core.exception.errorcode.CoreCode;

/**
 * @author WCF
 * @time 2018/12/28
 * @function
 **/
public enum ConfigErrorCode implements CoreCode {
    USER_PRIVILEGE_LESS("00010002", "The user's privilege is not enough");

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 状态码
     */
    private String code;

    ConfigErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getReason() {
        return msg;
    }
}
