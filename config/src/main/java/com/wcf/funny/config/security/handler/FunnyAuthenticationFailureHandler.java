package com.wcf.funny.config.security.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wcf.funny.config.exception.UserAuthException;
import com.wcf.funny.config.exception.errorcode.ConfigErrorCode;
import com.wcf.funny.core.annotation.FunnyHandler;
import com.wcf.funny.core.exception.errorcode.CoreCode;
import com.wcf.funny.core.reponse.BaseResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author wangcanfeng
 * @time 2019/1/6
 * @function 登录请求失败处理
 **/
@FunnyHandler
public class FunnyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    /**
     * 功能描述：  登录请求失败处理，返回失败信息
     *
     * @param request
     * @param response
     * @return void
     * @author wangcanfeng
     * @time 2019/1/6 21:28
     * @since v1.0
     **/
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException, ServletException {
        //设置返回格式
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        try (PrintWriter writer = response.getWriter()) {
            UserAuthException exception=(UserAuthException)e;
            String code = exception.getCode();
            String msg = exception.getMessage();
            BaseResponse res = new BaseResponse(code, msg);
            String json = JSON.toJSONString(res);
            writer.append(json);
        }
    }
}
