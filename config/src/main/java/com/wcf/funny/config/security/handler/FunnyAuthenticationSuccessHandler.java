package com.wcf.funny.config.security.handler;

import com.alibaba.fastjson.JSON;
import com.wcf.funny.core.annotation.FunnyHandler;
import com.wcf.funny.core.constant.ActionObject;
import com.wcf.funny.core.constant.ActionResult;
import com.wcf.funny.core.constant.ActionType;
import com.wcf.funny.core.exception.errorcode.CommonCode;
import com.wcf.funny.core.entity.OperationLogInfo;
import com.wcf.funny.core.service.OperationLogService;
import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.utils.FunnyTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

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
@FunnyHandler
public class FunnyAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private OperationLogService logService;

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
            OperationLogInfo info = new OperationLogInfo();
            info.setActionResult(ActionResult.SUCCESS.getCode());
            info.setActionObject(ActionObject.USER.getObject());
            info.setIp(request.getRemoteHost());
            info.setActionType(ActionType.LOGIN.getCode());
            info.setDetails("");
            info.setCreateTime(FunnyTimeUtils.nowUnix());
            info.setAuthorName(request.getRemoteUser());
            logService.insertLog(info);
        }

    }
}