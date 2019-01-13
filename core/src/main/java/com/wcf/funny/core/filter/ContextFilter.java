package com.wcf.funny.core.filter;

import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author wangcanfeng
 * @time 2019/1/13
 * @function 上下文过滤器，用于拦截request和处理response
 **/
@Log4j2
public class ContextFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("context filter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }

    @Override
    public void destroy() {

    }
}
