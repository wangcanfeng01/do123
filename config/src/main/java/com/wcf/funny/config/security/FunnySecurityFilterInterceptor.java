package com.wcf.funny.config.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author wangcanfeng
 * @description 权限过滤器
 * @Date Created in 11:53-2018/12/27
 */
@Log4j2
//@Configuration
public class FunnySecurityFilterInterceptor extends AbstractSecurityInterceptor implements Filter {

    @Autowired
    private FunnyFilterInvocationSecurityMetadataSource securityMetadataSource;

    @Autowired
    private FunnyAccessDecisionManager accessDecisionManager;
    @Autowired
    public void setAccessDecisionManager(){
        super.setAccessDecisionManager(accessDecisionManager);
    }

    /**
     * 功能描述: 过滤器初始化提示
     *
     * @param filterConfig
     * @return:void
     * @since: v1.0
     * @Author:wangcanfeng
     * @Date: 2018/12/27 15:03
     */
    @Override
    public void init(FilterConfig filterConfig) {
        log.info("my security interceptor is initialized");
    }



    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(servletRequest, servletResponse, filterChain);
        invoke(fi);
    }

    private void invoke(FilterInvocation fi) throws IOException, ServletException {
        //fi里面有一个被拦截的url
//里面调用MyInvocationSecurityMetadataSource的getAttributes(Object object)这个方法获取fi对应的所有权限
//再调用MyAccessDecisionManager的decide方法来校验用户的权限是否足够
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
//执行下一个拦截器
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return securityMetadataSource;
    }
}