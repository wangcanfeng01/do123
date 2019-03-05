package com.wcf.funny.config.exception.errorcode;

import com.wcf.funny.core.exception.errorcode.CoreCode;

/**
 * @author WCF
 * @time 2018/12/28
 * @function
 **/
public enum ConfigErrorCode implements CoreCode {
    // 用户权限不足
    USER_PRIVILEGE_LESS("0x04010001", "The user's privilege is not enough"),
    // 用户权限异常
    USER_PERMISSION_ERROR("0x04010002", "the user's permission is not right, detail: %s"),
    // 该用户不存在
    USER_NOT_FOUND("0x04010003", "the user is not found in db"),
    // 用户名或密码为空
    NAME_OR_PASSWORD_NULL("0x04010004", "name or password is null,please check it"),
    // 用户密码错误
    PASSWORD_IS_ERROR("0x04010005", "password is not right");


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
