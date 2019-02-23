package com.wcf.funny.core.reponse;

import com.wcf.funny.core.exception.errorcode.CoreCode;

/**
 * @author wangcanfeng
 * @time 2019/2/23
 * @function 异常的返回对象
 **/
public class ErrorResponse<T> extends BaseResponse<T> {

    private Throwable throwable;

    public ErrorResponse(String code, String msg) {
        super(code, msg);
    }

    /**
     * 功能描述：  通过枚举返回异常对象
     *
     * @param coreCode
     * @author wangcanfeng
     * @time 2019/2/23 11:12
     * @since v1.0
     **/
    public static ErrorResponse error(CoreCode coreCode) {
        return new ErrorResponse(coreCode.getCode(), coreCode.getReason());
    }

    /**
     * 功能描述：  携带异常原因的异常返回结果
     *
     * @param coreCode
     * @param thr
     * @author wangcanfeng
     * @time 2019/2/23 11:12
     * @since v1.0
     **/
    public static ErrorResponse error(CoreCode coreCode, Throwable thr) {
        ErrorResponse error = new ErrorResponse(coreCode.getCode(), coreCode.getReason());
        error.setThrowable(thr);
        return error;

    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
