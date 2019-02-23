package com.wcf.funny.blog.exception.errorcode;

import com.wcf.funny.core.exception.errorcode.CoreCode;

/**
 * @author wangcanfeng
 * @time 2019/2/17
 * @function
 **/
public enum ArticleErrorCode implements CoreCode {
    //插入关键字异常
    INSERT_ARTICLE_ERROR("00000005", "Insert article info into database failed"),
    //搜索关键字异常
    SELECT_ARTICLE_ERROR("00000006", "Select article info from database failed"),
    //更新关键字异常
    UPDATE_ARTICLE_ERROR("00000007", "Update article info into database failed"),
    //删除关键字异常
    DELETE_ARTICLE_ERROR("00000008", "Delete article info from database failed"),
    //该文章标题已存在
    ARTICLE_TITLE_ALREADY_EXIST("00000008", "this article title is already exist in database"),
    // 只有作者能删除该文章
    ONLY_AUTHOR_CAN_DELETE("0000100001","only the article's author can delete this article"),
    //只有作者能修改文章
    ONLY_AUTHOR_CAN_MODIFY("0000100001","only the article's author can modify this article"),
    // 当前文章不存在
    ARTICLE_IS_NOT_EXIST("000010001","this article is not exist"),
    // 文章提交类型不支持
    UNSUPPROTED_POST_METHOD("0000010001","the article post method is unsupported")
    ;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 状态码
     */
    private String code;

    ArticleErrorCode(String code, String msg) {
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
