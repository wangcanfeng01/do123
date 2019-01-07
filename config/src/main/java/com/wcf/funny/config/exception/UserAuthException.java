package com.wcf.funny.config.exception;

import com.wcf.funny.core.exception.errorcode.CoreCode;
import org.springframework.security.core.AuthenticationException;

/**
 * @author wangcanfeng
 * @time 2019/1/6
 * @function 用户鉴权异常
 **/
public class UserAuthException extends AuthenticationException {

    private String code;

    public UserAuthException(CoreCode code) {
        super(code.getReason());
        this.code = code.getCode();
    }

    public UserAuthException(String code, String msg, Throwable t) {
        super(msg, t);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
