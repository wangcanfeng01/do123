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
    // 关键字关联的文章数目不为0，无法删除
    KEYWORD_DELETE_DISABLE("0001001","can not delete the keyword because of the article count is not zero"),
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
    // 专题下文章数目不为0，无法删除
    CATEGORY_DELETE_DISABLE("0001001","can not delete the category because of the article count is not zero"),
    // 减少标签统计值异常
    REDUCE_META_COUNT_ERROR("00010001","reduce the count of metas failed"),
    // 增加标签统计值异常
    INCREASE_META_COUNT_ERROR("00010001","increase the count of metas failed"),
    // 查询标签信息异常
    SELECT_META_ERROR("00010001","select the meta error")
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
