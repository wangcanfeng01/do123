package com.wcf.funny.config.security.provider;

import com.wcf.funny.admin.constant.UserConstant;
import com.wcf.funny.config.exception.UserAuthException;
import com.wcf.funny.admin.vo.UserDetailsVo;
import com.wcf.funny.config.exception.errorcode.ConfigErrorCode;
import com.wcf.funny.core.annotation.FunnyProvider;
import com.wcf.funny.core.utils.MD5Utils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Collection;

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
        if (ObjectUtils.isEmpty(details)) {
            throw new UserAuthException(ConfigErrorCode.USER_NOT_FOUND);
        }
        boolean isPass = false;
        if (name.equals(UserConstant.VISITOR_NAME)) {
            isPass = true;
        } else {
            isPass = checkPassword(details.getPassword(), (String) authentication.getCredentials());
        }
        if (isPass) {
            //这里需要提取从数据库中获取到角色信息
            Collection<GrantedAuthority> roles = details.getAuthorities();
            Authentication auth = new UsernamePasswordAuthenticationToken(details.getUsername(), details.getPassword(), roles);
            return auth;
        }
        return null;
    }

    private boolean checkPassword(String password, String authPassword) {
        if (ObjectUtils.isEmpty(password) || ObjectUtils.isEmpty(authPassword)) {
            throw new UserAuthException(ConfigErrorCode.NAME_OR_PASSWORD_NULL);
        }
        authPassword = MD5Utils.encode(authPassword);
        if (password.equals(authPassword)) {
            return true;
        } else {
            throw new UserAuthException(ConfigErrorCode.PASSWORD_IS_ERROR);
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}