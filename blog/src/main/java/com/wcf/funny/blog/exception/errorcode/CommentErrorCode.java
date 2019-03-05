package com.wcf.funny.blog.exception.errorcode;

import com.wcf.funny.core.exception.errorcode.CoreCode;

/**
 * @author wangcanfeng
 * @time 2019/2/18
 * @function 评论相关的错误码
 **/
public enum  CommentErrorCode implements CoreCode{
    //插入评论异常
    INSERT_COMMENT_ERROR("0x03020001", "Insert comment info into database failed"),
    //搜索评论异常
    SELECT_COMMENT_ERROR("0x03020002", "Select comment info from database failed"),
    //更新评论异常
    UPDATE_COMMENT_ERROR("0x03020003", "Update comment info into database failed"),
    //删除评论异常
    DELETE_COMMENT_ERROR("0x03020004", "Delete comment info from database failed"),
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
