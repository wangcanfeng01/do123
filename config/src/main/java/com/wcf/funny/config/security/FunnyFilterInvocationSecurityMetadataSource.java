package com.wcf.funny.config.security;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author wangcanfeng
 * @description
 * @Date Created in 17:29-2018/12/27
 */
//@Service
public class FunnyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Override
    public Collection<ConfigAttribute> getAttributes(Object obj) throws IllegalArgumentException {
        Collection<ConfigAttribute> collection = new ArrayList<>();
        FilterInvocation filterInvocation = (FilterInvocation) obj;
        //从数据库中加载拦截路径，然后创建对象
        RequestMatcher requestMatcher = new AntPathRequestMatcher("/myself");
        boolean b = requestMatcher.matches(filterInvocation.getRequest());
        if (b) {
            ConfigAttribute configAttribute = new SecurityConfig("ADMIN");
            collection.add(configAttribute);
        }

        return collection;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return new ArrayList<>();
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}