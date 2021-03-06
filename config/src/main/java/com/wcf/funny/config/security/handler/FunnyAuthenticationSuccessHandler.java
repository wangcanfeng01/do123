package com.wcf.funny.config.security.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wcf.funny.admin.constant.UserConstant;
import com.wcf.funny.admin.entity.LoginUser;
import com.wcf.funny.admin.service.LoginUserService;
import com.wcf.funny.core.annotation.FunnyHandler;
import com.wcf.funny.core.constant.LogConstant;
import com.wcf.funny.core.exception.errorcode.CommonCode;
import com.wcf.funny.core.entity.OperationLogInfo;
import com.wcf.funny.core.service.OperationLogService;
import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.utils.FunnyTimeUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author WCF
 * @time 2018/3/4
 * @why 登录成功处理，这个类里面还可以设置登录成功后根据角色类型页面跳转
 **/
@Log4j2
@FunnyHandler
public class FunnyAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private LoginUserService loginUserService;

    private final static String DEFAULT_SUCCESS_URL = "/home";

    @PostConstruct
    public void init() {
        //设置一下默认的成功路径
        setDefaultTargetUrl(DEFAULT_SUCCESS_URL);
    }

    /**
     * 功能描述：  登录成功后的处理
     *
     * @return
     * @author WCF
     * @time 2018/12/28 23:33
     * @since v1.0
     **/
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        //设置返回格式
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        String targetUrl = determineTargetUrl(request, response);
        try (PrintWriter writer = response.getWriter()) {
            BaseResponse<String> res = new BaseResponse<>(CommonCode.DEFAULT_SUCCESS_CODE, targetUrl);
            String json = JSON.toJSONString(res);
            writer.append(json);
        } finally {
            String username=request.getParameter("username");
            String ip = request.getRemoteAddr();
            loginUserService.insertLoginUser(username,ip);
        }

    }
}