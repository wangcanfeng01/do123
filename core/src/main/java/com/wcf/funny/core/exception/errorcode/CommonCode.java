package com.wcf.funny.core.exception.errorcode;

public enum  CommonCode implements CoreCode{

     //成功
    DEFAULT_SUCCESS_CODE("0","success"),
    // 时间格式异常
    TIME_FORMAT_ERROR("0x05020001","time format is not right"),
    // 不支持的图片类型
    UNSUPPORTED_PICTURE_TYPE("0x05020002","unsupported picture type");

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
