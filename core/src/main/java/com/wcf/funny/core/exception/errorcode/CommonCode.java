package com.wcf.funny.core.exception.errorcode;

public enum  CommonCode implements CoreCode{

     //成功
    DEFAULT_SUCCESS_CODE("0","success"),
    // 时间格式异常
    TIME_FORMAT_ERROR("0x05020001","time format is not right"),
    // 不支持的图片类型
    UNSUPPORTED_PICTURE_TYPE("0x05020002","unsupported picture type"),
    // 类名不能为空
    CLASS_NAME_NULL("0x05020003","target class can not be null"),
    // bean的名称不能为空
    BEAN_NAME_NULL("0x05020004","bean name can not be null"),
    // 找不到目标类
    CAN_NOT_FIND_CLASS("0x05020005","bean name can not be null"),
    // 找不到bean名称对应的类
    CAN_NOT_FIND_BEAN("0x05020006","bean name can not be null"),;

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
