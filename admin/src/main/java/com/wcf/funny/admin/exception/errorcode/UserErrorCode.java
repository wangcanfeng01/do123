package com.wcf.funny.admin.exception.errorcode;

import com.wcf.funny.core.exception.errorcode.CoreCode;

/**
 * @author wangcanfeng
 * @time 2018/12/29
 * @function
 **/
public enum UserErrorCode implements CoreCode {
     //插入用户异常
    INSERT_USER_ERROR("0x02030001", "Insert user data into database failed"),
    //搜索用户异常
    SELECT_USER_ERROR("0x02030002", "Select user data from database failed"),
    //更新用户异常
    UPDATE_USER_ERROR("0x02030003", "Update user data into database failed"),
    //删除用户异常
    DELETE_USER_ERROR("0x02030004", "Delete user data from database failed"),
    //用户名已存在
    USER_ALREADY_EXIST("0x02030005", "The user name is already exist"),
    //角色查询失败
    SELECT_ROLR_ERROR("0x02030006","select role from db failed"),
    //角色插入失败
    INSETR_ROLR_ERROR("0x02030007","insert role from db failed"),
    //角色更新失败
    UPDATE_ROLR_ERROR("0x02030008","update role from db failed"),
    //角色删除失败
    DELETE_ROLR_ERROR("0x02030009","delete role from db failed"),
    //登录用户信息存在异常
    LOGIN_USER_INFO_ERROR("0x0203000A","the login user has something not right"),

    //登录记录插入失败
    INSERT_LOGIN_ERROR("0x0203000B","insert the user's login info error"),
    //登录记录查询失败
    SELECT_LOGIN_ERROR("0x0203000C","select the user's login info error"),
    // 用户类型不支持
    USER_TYPE_ERROR("0x0203000D","the input user type is not supported"),
    // 用户状态不支持
    USER_STATUS_ERROR("0x0203000E","the input user status is not supported"),
    // 插入用户详细信息异常
    INSERT_USER_DETAILS_ERROR("0x0203000F","insert user's details error"),
    // 修改用户详细信息异常
    UPDATE_USER_DETAILS_ERROR("0x02030010","update user's details error"),
    // 查询用户详细信息异常
    SELECT_USER_DETAILS_ERROR("0x02030011","select user's details error"),
    //原密码输入不正确
    SOURCE_PASSWORD_ERROR("0x02030012","user's source password is error"),
    ;

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
