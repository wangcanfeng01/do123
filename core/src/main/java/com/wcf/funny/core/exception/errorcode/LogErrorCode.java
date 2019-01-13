package com.wcf.funny.core.exception.errorcode;

/**
 * @author wangcanfeng
 * @time 2019/1/13
 * @function 操作日志相关的错误码信息
 **/
public enum LogErrorCode implements CoreCode {

    INSERT_OPERATION_ERROR("00000011", "Insert operation data into database failed"),
    SELECT_OPERATION_ERROR("00000012", "Select operation data from database failed"),
    UPDATE_OPERATION_ERROR("00000013", "Update operation data into database failed"),
    DELETE_OPERATION_ERROR("00000014", "Delete operation data from database failed");
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
