package com.wcf.funny.config.security;

import com.wcf.funny.config.exception.FunnyAccessDeniedException;
import com.wcf.funny.config.exception.errorcode.ConfigErrorCode;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author WCF
 * @time 2018/12/26
 * @function 用于决策是否用户的角色能通过当前的拦截器
 **/
//@FunnyManager
@Log4j2
public class FunnyAccessDecisionManager implements AccessDecisionManager {
    /**
     * @param authentication   传入的角色信息
     * @param object 这里没用到
     * @param configAttributes 从数据库里面加载的当前拦截的角色列表
     * @return void
     * @note
     * @author WCF
     * @time 2018/11/18 18:21
     * @since v1.0
     **/
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {
        if (null == configAttributes || configAttributes.size() <= 0) {
            return;
        }
        String needRole;
        //获取到传入的角色列表信息，然后和库里面的信息进行对比
        //authentication是登录角色传入的角色信息
        //和从数据库加载的角色列表configAttributes做比较，判断是否有符合条件的角色信息
        for (Iterator<ConfigAttribute> iter = configAttributes.iterator(); iter.hasNext(); ) {
            needRole = iter.next().getAttribute();
            for (GrantedAuthority ga : authentication.getAuthorities()) {
                if (needRole.trim().equals(ga.getAuthority().trim())) {
                    return;
                }
            }
        }
        log.error(ConfigErrorCode.USER_PRIVILEGE_LESS);
        //这里放自定义异常，可以将权限出错后跳转到自定义的无权限页面
        throw new FunnyAccessDeniedException(ConfigErrorCode.USER_PRIVILEGE_LESS);
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}