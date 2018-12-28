package com.wcf.funny.config.security.provider;

import com.wcf.funny.core.annotation.FunnyProvider;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @author WCF
 * @time 2018/12/26
 * @function 鉴权处理器
 **/
@FunnyProvider
public class FunnyAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}