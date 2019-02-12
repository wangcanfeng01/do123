package com.wcf.funny.config.security.handler;

import com.wcf.funny.core.annotation.FunnyHandler;
import com.wcf.funny.core.constant.ActionObject;
import com.wcf.funny.core.constant.ActionResult;
import com.wcf.funny.core.constant.ActionType;
import com.wcf.funny.core.log.entity.OperationLogInfo;
import com.wcf.funny.core.log.service.OperationLogService;
import com.wcf.funny.core.utils.FunnyTimeUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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


    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
        try {
            response.sendRedirect("/home");
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
