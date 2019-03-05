package com.wcf.funny.core.exception.errorcode;

/**
 * @author wangcanfeng
 * @time 2019/2/23
 * @function 文件上传错误码
 **/
public enum  FileUploadErrorCode implements CoreCode{
     // 插入图片记录信息异常
    FILE_INFO_INSERT_ERROR("0x05030001", "insert the file information to db failed"),
    // 删除图片记录信息异常
    FILE_INFO_DELETE_ERROR("0x05030002", "delete the file information to db failed"),
    // 查询图片记录信息异常
    FILE_INFO_SELECT_ERROR("0x05030003", "select the file information to db failed"),
    // 图片复制到指定文件夹异常
    FILE_COPY_TO_DIRECTORY_ERROR("0x05030004","copy file to directory failed"),
    // 文件路径不能为空
    FILE_PATH_NULL("0x05030005","the file path can not be null"),
    ;
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

    FileUploadErrorCode(String code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "[code: " + this.code + ", reason: " + this.reason + "]";
    }
}
