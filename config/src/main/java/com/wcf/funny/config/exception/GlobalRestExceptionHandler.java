package com.wcf.funny.config.exception;

import com.wcf.funny.core.exception.BaseException;
import com.wcf.funny.core.exception.PgSqlException;
import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.utils.I18Utils;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author WCF
 * @time 2018/12/28
 * @function rest请求统一异常管理
 **/
@Log4j2
@RestControllerAdvice
public class GlobalRestExceptionHandler {

    /**
     * 功能描述：  返回用户基本信息持久化异常
     *
     * @param e
     * @author wangcanfeng
     * @time 2019/1/13 22:27
     * @since v1.0
     **/
    @ExceptionHandler(value = {BaseException.class})
    public BaseResponse unknownException(BaseException e) {
        log.error("error details :" + e.getMessage());
        //传往前端的信息做一下多语言翻译
        String message = I18Utils.getErrorMessage(e.getCode(), e.getMessage());
        return new BaseResponse(e.getCode(), message);
    }

    /**
     * 功能描述：  返回用户基本信息持久化异常
     *
     * @param e
     * @author wangcanfeng
     * @time 2019/1/13 22:27
     * @since v1.0
     **/
    @ExceptionHandler(value = {PgSqlException.class})
    public BaseResponse pgSqlException(PgSqlException e) {
        log.error("error details :" + e.getCause().getMessage());
        return new BaseResponse(e.getCode(), e.getMessage());
    }

}
