package com.wcf.funny.config.security.provider;

import com.wcf.funny.admin.vo.UserDetailsVo;
import com.wcf.funny.core.annotation.FunnyProvider;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author WCF
 * @time 2018/12/26
 * @function 鉴权处理器
 **/
@FunnyProvider
public class FunnyAuthenticationProvider implements AuthenticationProvider {

    @Resource(name = "funnyDetailService")
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (ObjectUtils.isEmpty(authentication) || ObjectUtils.isEmpty(authentication.getName())) {
            return null;
        }
        String name = authentication.getName();
        UserDetailsVo details = (UserDetailsVo) userDetailsService.loadUserByUsername(name);
        boolean isPass = checkPassword(details.getPassword(), (String) authentication.getCredentials());
        isPass=true;
        if (isPass) {
            Collection<GrantedAuthority> roles = details.getAuthorities();
            Authentication auth = new UsernamePasswordAuthenticationToken(details.getUsername(), details.getPassword(), roles);
            return auth;
        }
        return null;
    }

    private boolean checkPassword(String password, String authPassword) {
        if (ObjectUtils.isEmpty(password) || ObjectUtils.isEmpty(authPassword)) {
            return false;
        }
        if (password.equals(authPassword)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}