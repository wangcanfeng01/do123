package com.wcf.funny.core.exception.errorcode;

/**
 * @author wangcanfeng
 * @time 2019/1/13
 * @function 操作日志相关的错误码信息
 **/
public enum LogErrorCode implements CoreCode {

     // 操作日志插入异常
    INSERT_OPERATION_ERROR("0x05010001", "Insert operation data into database failed"),
    // 查询操作日志异常
    SELECT_OPERATION_ERROR("0x05010002", "Select operation data from database failed"),
    // 更新操作日志异常
    UPDATE_OPERATION_ERROR("0x05010003", "Update operation data into database failed"),
    // 删除操作日志异常
    DELETE_OPERATION_ERROR("0x05010004", "Delete operation data from database failed");
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

    LogErrorCode(String code, String reason) {
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
