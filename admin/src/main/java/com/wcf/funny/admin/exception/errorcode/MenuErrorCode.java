package com.wcf.funny.admin.exception.errorcode;

import com.wcf.funny.core.exception.errorcode.CoreCode;

/**
 * @author wangcanfeng
 * @time 2019/1/27
 * @function
 **/
public enum MenuErrorCode implements CoreCode {
    //插入菜单异常
    INSERT_MENU_ERROR("0x02010001", "Insert menu info into database failed"),
    //搜索菜单异常
    SELECT_MENU_ERROR("0x02010002", "Select menu info from database failed"),
    //更新菜单异常
    UPDATE_MENU_ERROR("0x02010003", "Update menu info into database failed"),
    //删除菜单异常
    DELETE_MENU_ERROR("0x02010004", "Delete menu info from database failed"),
    //菜单信息已存在
    MENU_ALREADY_EXIST("0x02010005", "this menu is already exist in database"),;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 状态码
     */
    private String code;

    MenuErrorCode(String code, String msg) {
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
