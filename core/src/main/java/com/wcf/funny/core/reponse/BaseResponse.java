package com.wcf.funny.core.reponse;

import com.wcf.funny.core.exception.errorcode.CommonCode;
import com.wcf.funny.core.exception.errorcode.CoreCode;

/**
 * @author wangcanfeng
 * @description 服务的通用返回对象
 * @date 2018-12-31 00:00:00
 */
public class BaseResponse<T> {
    private String code;
    private String msg;
    private T data;

    public BaseResponse(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public BaseResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseResponse(CoreCode coreCode, T data) {
        this.code = coreCode.getCode();
        this.msg = coreCode.getReason();
        this.data = data;
    }


    public static BaseResponse ok() {
        return new BaseResponse(CommonCode.DEFAULT_SUCCESS_CODE, null);
    }


    /**
     * 功能描述：  默认的返回正确结果
     *
     * @wangcanfeng     * @re2019/1/20 17:42thor wangcanfeng
* @param data2019/1/20 17:31
     * @since v1.0
     **/
    public BaseResponse(T data) {
        this.code = CommonCode.DEFAULT_SUCCESS_CODE.getCode();
        this.msg = CommonCode.DEFAULT_SUCCESS_CODE.getReason();
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
