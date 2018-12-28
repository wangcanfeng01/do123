package com.wcf.funny.config.security.handler;

import com.wcf.funny.core.annotation.FunnyHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

/**
 * @author wangcanfeng
 * @description 被拒绝访问后跳转处理
 * @Date Created in 16:53-2018/12/27
 */
@FunnyHandler
public class FunnyAccessDeniedHandler extends AccessDeniedHandlerImpl {
    @Autowired
    public void setError() {
        super.setErrorPage("/500");
    }
}