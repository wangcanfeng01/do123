package com.wcf.funny.config.exception;

import com.wcf.funny.core.exception.errorcode.CoreCode;
import org.springframework.security.access.AccessDeniedException;

/**
 * @author WCF
 * @time 2018/12/28
 * @function 权限拒绝异常
 **/
public class FunnyAccessDeniedException extends AccessDeniedException {

    /**
     * 错误码
     */
    private String code;

    /**
     * 功能描述：根据自定义错误码和信息构造异常
     *
     * @param code
     * @param msg
     * @return
     * @author WCF
     * @time 2018/12/28 23:29
     * @since v1.0
     **/
    public FunnyAccessDeniedException(String code, String msg) {
        super(msg);
        this.code = code;
    }

    /**
     * 功能描述：根据自定义错误码和信息构造异常
     *
     * @param code 异常枚举
     * @return
     * @author WCF
     * @time 2018/12/28 23:29
     * @since v1.0
     **/
    public FunnyAccessDeniedException(CoreCode code){
        super(code.getReason());
        this.code=code.getCode();
    }

    /**
     * 功能描述：默认异常构造
     *
     * @param msg
     * @return
     * @author WCF
     * @time 2018/12/28 23:29
     * @since v1.0
     **/
    public FunnyAccessDeniedException(String msg) {
        super(msg);
    }

    public String getCode() {
        return code;
    }
}
