package com.wcf.funny.blog.exception.errorcode;

import com.wcf.funny.core.exception.errorcode.CoreCode;

/**
 * @author wangcanfeng
 * @time 2019/2/16
 * @function 标签异常信息
 **/
public enum  MetaErrorCode implements CoreCode{
    //插入关键字异常
    INSERT_KEYWORD_ERROR("00000005", "Insert keyword info into database failed"),
    //搜索关键字异常
    SELECT_KEYWORD_ERROR("00000006", "Select keyword info from database failed"),
    //更新关键字异常
    UPDATE_KEYWORD_ERROR("00000007", "Update keyword info into database failed"),
    //删除关键字异常
    DELETE_KEYWORD_ERROR("00000008", "Delete keyword info from database failed"),
    //该关键字已存在已存在
    KEYWORD_ALREADY_EXIST("00000008", "this keyword is already exist in database"),

    //插入专题异常
    INSERT_CATEGORY_ERROR("00000005", "Insert category info into database failed"),
    //搜索专题异常
    SELECT_CATEGORY_ERROR("00000006", "Select category info from database failed"),
    //更新专题异常
    UPDATE_CATEGORY_ERROR("00000007", "Update category info into database failed"),
    //删除专题异常
    DELETE_CATEGORY_ERROR("00000008", "Delete category info from database failed"),
    //该专题已存在已存在
    CATEGORY_ALREADY_EXIST("00000008", "this category is already exist in database"),
    ;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 状态码
     */
    private String code;

    MetaErrorCode(String code, String msg) {
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
