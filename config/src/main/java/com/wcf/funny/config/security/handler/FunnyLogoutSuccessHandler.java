package com.wcf.funny.config.security.handler;

import com.alibaba.fastjson.JSON;
import com.wcf.funny.core.annotation.FunnyHandler;
import com.wcf.funny.core.constant.ActionObject;
import com.wcf.funny.core.constant.ActionResult;
import com.wcf.funny.core.constant.ActionType;
import com.wcf.funny.core.exception.errorcode.CommonCode;
import com.wcf.funny.core.log.entity.OperationLogInfo;
import com.wcf.funny.core.log.service.OperationLogService;
import com.wcf.funny.core.reponse.BaseResponse;
import com.wcf.funny.core.utils.FunnyTimeUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author wangcanfeng
 * @time 2019/1/11
 * @function 退出登录的处理器
 **/
@FunnyHandler
@Log4j2
public class FunnyLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private OperationLogService logService;

    private final static String DEFAULT_SUCCESS_URL = "/home";

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
        //设置返回格式
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            BaseResponse<String> res = new BaseResponse<>(CommonCode.DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_URL);
            String json = JSON.toJSONString(res);
            writer.append(json);
        } catch (IOException e) {
            log.error("logout failed, the details: " + e.getMessage());
        } finally {
            OperationLogInfo info = new OperationLogInfo();
            info.setActionResult(ActionResult.SUCCESS.getCode());
            info.setActionObject(ActionObject.USER.getObject());
            info.setIp(request.getRemoteHost());
            info.setActionType(ActionType.LOGOUT.getCode());
            info.setDetails("");
            info.setCreateTime(FunnyTimeUtils.nowUnix());
            info.setAuthorName(request.getRemoteUser());
            logService.insertLog(info);
        }
    }
}