package com.wcf.funny.core.exception.errorcode;

/**
 * @author WCF
 * @time 2018/12/26
 * @function 错误信息枚举
 **/
public enum  ErrorMessage implements CoreCode{
    UNKNOWN_ERROR("00000000", "Unknown error","未知异常"),

    INSERT_ARTICLE_ERROR("00000001", "Insert article data into database failed","插入文章异常"),
    SELECT_ARTICLE_ERROR("00000002", "Select article data from database failed","查询文章异常"),
    UPDATE_ARTICLE_ERROR("00000003", "Update article data into database failed","更新文章异常"),
    DELETE_ARTICLE_ERROR("00000004", "Delete article data from database failed","删除文章异常"),

    INSERT_USER_ERROR("00000005", "Insert user data into database failed","插入用户异常"),
    SELECT_USER_ERROR("00000006", "Select user data from database failed","搜索用户异常"),
    UPDATE_USER_ERROR("00000007", "Update user data into database failed","更新用户异常"),
    DELETE_USER_ERROR("00000008", "Delete user data from database failed","删除用户异常"),

    INSERT_COMMENT_ERROR("00000009", "Insert comment data into database failed","插入评论异常"),
    SELECT_COMMENT_ERROR("00000000A", "Select comment data from database failed","搜索评论异常"),
    UPDATE_COMMENT_ERROR("00000000B", "Update comment data into database failed","更新评论异常"),
    DELETE_COMMENT_ERROR("00000000C", "Delete comment data from database failed","删除评论异常"),

    INSERT_META_ERROR("00000000D", "Insert data meta into database failed","插入标签异常"),
    SELECT_META_ERROR("00000000E", "Select meta data from database failed","搜索标签异常"),
    UPDATE_META_ERROR("00000000F", "Update meta data into database failed","更新标签异常"),
    DELETE_META_ERROR("000000010", "Delete meta data from database failed","删除标签异常"),

    INSERT_OPERATION_ERROR("00000011", "Insert operation data into database failed","插入操作日志异常"),
    SELECT_OPERATION_ERROR("00000012", "Select operation data from database failed","搜索操作日志异常"),
    UPDATE_OPERATION_ERROR("00000013", "Update operation data into database failed","更新操作日志异常"),
    DELETE_OPERATION_ERROR("00000014", "Delete operation data from database failed","删除操作日志异常"),

    INSERT_PICTURE_ERROR("00000015", "Insert operation data into database failed","插入图片异常"),
    SELECT_PICTURE_ERROR("00000016", "Select operation data from database failed","搜索图片异常"),
    UPDATE_PICTURE_ERROR("00000017", "Update operation data into database failed","更新图片异常"),
    DELETE_PICTURE_ERROR("00000018", "Delete operation data from database failed","删除图片异常"),

    INSERT_VERSION_ERROR("00000019", "Insert operation data into database failed","插入版本异常"),
    SELECT_VERSION_ERROR("0000001A", "Select operation data from database failed","搜索版本异常"),
    UPDATE_VERSION_ERROR("0000001B", "Update operation data into database failed","更新版本异常"),
    DELETE_VERSION_ERROR("0000001C", "Delete operation data from database failed","删除版本异常"),

    USER_ALREADY_EXIST("00010001", "The user name is already exist","用户名已存在"),
    USER_PRIVILEGE_LESS("00010002", "The user's privilege is not enough","用户权限不足");

    ErrorMessage(String code, String reason, String chinese) {
        this.code = code;
        this.reason = reason;
        this.chinese = chinese;
    }

    /**
     * 原因
     */
    private String reason;

    /**
     * 中文信息
     */
    private String chinese;
    /**
     * 错误码
     */
    private String code;

    public String getReason() {
        return reason;
    }

    public String getCode() {
        return code;
    }

    public String getChinese() {
        return chinese;
    }

    @Override
    public String toString() {
        return "[code: " + this.code + ", reason: " + this.reason + "]";
    }
}
