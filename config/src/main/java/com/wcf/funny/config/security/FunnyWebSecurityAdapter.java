package com.wcf.funny.config.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author WCF
 * @time 2018/12/26
 * @function
 **/
@Configuration
@EnableWebSecurity
@Log4j2
public class FunnyWebSecurityAdapter extends WebSecurityConfigurerAdapter {



    /**
     * 配置授权提供
     *
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(myAuthenticationProvider);
        //如果直接使用默认的DaoAuthenticationProvider,就需要配置UserDetailsService，
        // 其中实现的loadUserByUsername方法，如果使用自己的provider就不必配置，因为密码验证是自己实现的，不是托管的
        //   auth.userDetailsService(this.mySafeDetailService);
    }
}
