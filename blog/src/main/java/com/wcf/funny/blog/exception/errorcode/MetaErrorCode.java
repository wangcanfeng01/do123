package com.wcf.funny.blog.exception.errorcode;

import com.wcf.funny.core.exception.errorcode.CoreCode;

/**
 * @author wangcanfeng
 * @time 2019/2/16
 * @function 标签异常信息
 **/
public enum  MetaErrorCode implements CoreCode{
     //插入关键字异常
    INSERT_KEYWORD_ERROR("0x03030001", "Insert keyword info into database failed"),
    //搜索关键字异常
    SELECT_KEYWORD_ERROR("0x03030002", "Select keyword info from database failed"),
    //更新关键字异常
    UPDATE_KEYWORD_ERROR("0x03030003", "Update keyword info into database failed"),
    //删除关键字异常
    DELETE_KEYWORD_ERROR("0x03030004", "Delete keyword info from database failed"),
    //该关键字已存在已存在
    KEYWORD_ALREADY_EXIST("0x03030005", "this keyword is already exist in database"),
    // 关键字关联的文章数目不为0，无法删除
    KEYWORD_DELETE_DISABLE("0x03030006","can not delete the keyword because of the article count is not zero"),
    //插入专题异常
    INSERT_CATEGORY_ERROR("0x03030007", "Insert category info into database failed"),
    //搜索专题异常
    SELECT_CATEGORY_ERROR("0x03030008", "Select category info from database failed"),
    //更新专题异常
    UPDATE_CATEGORY_ERROR("0x03030009", "Update category info into database failed"),
    //删除专题异常
    DELETE_CATEGORY_ERROR("0x0303000A", "Delete category info from database failed"),
    //该专题已存在已存在
    CATEGORY_ALREADY_EXIST("0x0303000B", "this category is already exist in database"),
    // 专题下文章数目不为0，无法删除
    CATEGORY_DELETE_DISABLE("0x0303000C","can not delete the category because of the article count is not zero"),
    // 减少标签统计值异常
    REDUCE_META_COUNT_ERROR("0x0303000D","reduce the count of metas failed"),
    // 增加标签统计值异常
    INCREASE_META_COUNT_ERROR("0x0303000E","increase the count of metas failed"),
    // 查询标签信息异常
    SELECT_META_ERROR("0x0303000F","select the meta error"),
    // 更新标签信息异常
    UPDATE_META_ERROR("0x03030010","update meta info error")
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
