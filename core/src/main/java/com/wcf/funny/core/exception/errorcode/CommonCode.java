package com.wcf.funny.core.exception.errorcode;

public enum  CommonCode implements CoreCode{

    DEFAULT_SUCCESS_CODE("0","success"),
    TIME_FORMAT_ERROR("00000001001","time format is not right"),
    UNSUPPORTED_PICTURE_TYPE("000010001","unsupported picture type");

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
    CommonCode(String code, String reason) {
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
