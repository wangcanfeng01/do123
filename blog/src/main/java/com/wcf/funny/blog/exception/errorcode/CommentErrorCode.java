package com.wcf.funny.blog.exception.errorcode;

import com.wcf.funny.core.exception.errorcode.CoreCode;

/**
 * @author wangcanfeng
 * @time 2019/2/18
 * @function 评论相关的错误码
 **/
public enum  CommentErrorCode implements CoreCode{
    //插入评论异常
    INSERT_KEYWORD_ERROR("00000005", "Insert keyword info into database failed"),
    //搜索评论异常
    SELECT_KEYWORD_ERROR("00000006", "Select keyword info from database failed"),
    //更新评论异常
    UPDATE_KEYWORD_ERROR("00000007", "Update keyword info into database failed"),
    //删除评论异常
    DELETE_KEYWORD_ERROR("00000008", "Delete keyword info from database failed"),
    ;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 状态码
     */
    private String code;

    CommentErrorCode(String code, String msg) {
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
