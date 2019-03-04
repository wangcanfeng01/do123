package com.wcf.funny.config.security.handler;

import com.alibaba.fastjson.JSON;
import com.wcf.funny.config.exception.UserAuthException;
import com.wcf.funny.core.annotation.FunnyHandler;
import com.wcf.funny.core.constant.LogConstant;
import com.wcf.funny.core.entity.OperationLogInfo;
import com.wcf.funny.core.service.OperationLogService;
import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.utils.FunnyTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private OperationLogService logService;
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
        try (PrintWriter writer = response.getWriter()) {
            UserAuthException exception=(UserAuthException)e;
            String code = exception.getCode();
            String msg = exception.getMessage();
            BaseResponse res = new BaseResponse(code, msg);
            String json = JSON.toJSONString(res);
            writer.append(json);
        }finally {
            OperationLogInfo info = new OperationLogInfo();
            info.setActionResult(LogConstant.ActionResult.FAILED);
            info.setActionObject(LogConstant.ActionObject.USER);
            info.setIp(request.getRemoteHost());
            info.setActionType(LogConstant.ActionType.LOGIN);
            info.setDetails("");
            info.setCreateTime(FunnyTimeUtils.nowUnix());
            info.setAuthorName(request.getRemoteUser());
            logService.insertLog(info);
        }
    }
}
