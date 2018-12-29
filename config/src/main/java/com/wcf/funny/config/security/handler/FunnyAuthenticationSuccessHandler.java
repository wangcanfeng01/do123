package com.wcf.funny.config.security.handler;

import com.wcf.funny.core.annotation.FunnyHandler;
import com.wcf.funny.core.log.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author WCF
 * @time 2018/3/4
 * @why 登录成功处理，这个类里面还可以设置登录成功后根据角色类型页面跳转
 **/
@FunnyHandler
public class FunnyAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private OperationLogService logService;

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
        //用户信息来自 provider 中的 UsernamePasswordAuthenticationToken(userInfo, password, authorities)
        //所以可以强制转换类型
//        UserDetailsInfo userDetailsInfo = (UserDetailsInfo) authentication.getPrincipal();
//        try {
//            wcfOperationService.insertLog("用户登录", request.getRemoteAddr(), userDetailsInfo.getNickname());
//        } catch (PgSqlException e) {
//            log.error(ErrorMessage.INSERT_USER_ERROR,e);
//        }
//        log.info("此次登录用户：" + userDetailsInfo.getUsername());
        super.onAuthenticationSuccess(request, response, authentication);
    }
}