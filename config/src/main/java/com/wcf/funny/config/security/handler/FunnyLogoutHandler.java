package com.wcf.funny.config.security.handler;

import com.wcf.funny.core.annotation.FunnyHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wangcanfeng
 * @time 2019/1/11
 * @function
 **/
@FunnyHandler
public class FunnyLogoutHandler implements LogoutHandler {
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        try {
            response.sendRedirect("/home");
        } catch (IOException e) {

        }
    }
}
