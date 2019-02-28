package com.wcf.funny.core.exception;


import com.wcf.funny.core.exception.errorcode.CoreCode;

/**
 * @author wangcanfeng
 * @time 2019/2/28
 * @function
 **/
public class PictureException extends BaseException {

    /**
     * 抛出携带错误码和错误信息的异常
     *
     * @param code
     * @param msg
     */
    public PictureException(String code, String msg) {
        super(msg);
        setCode(code);
    }

    /**
     * 抛出携带错误码和错误信息的异常
     *
     * @param coreCode
     */
    public PictureException(CoreCode coreCode) {
        super(coreCode);
    }
}
