package com.wcf.funny.admin.exception.errorcode;

import com.wcf.funny.core.exception.errorcode.CoreCode;

/**
 * @author wangcanfeng
 * @time 2018/12/29
 * @function
 **/
public enum UserErrorCode implements CoreCode {
    //插入用户异常
    INSERT_USER_ERROR("00000005", "Insert user data into database failed"),

    //搜索用户异常
    SELECT_USER_ERROR("00000006", "Select user data from database failed"),

    //更新用户异常
    UPDATE_USER_ERROR("00000007", "Update user data into database failed"),

    //删除用户异常
    DELETE_USER_ERROR("00000008", "Delete user data from database failed"),
    //用户名已存在
    USER_ALREADY_EXIST("00010001", "The user name is already exist");

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 状态码
     */
    private String code;

    UserErrorCode(String code, String msg) {
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