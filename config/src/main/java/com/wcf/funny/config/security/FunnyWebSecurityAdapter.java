package com.wcf.funny.config.security;

import com.wcf.funny.config.security.handler.FunnyAuthenticationSuccessHandler;
import com.wcf.funny.config.security.provider.FunnyAuthenticationProvider;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

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
     * 权限过滤器
     */
    @Autowired
    private FunnySecurityFilterInterceptor securityFilterInterceptor;

    /**
     * 用户鉴权
     */
    @Autowired
    private FunnyAuthenticationProvider authenticationProvider;

    /**
     * 鉴权成功处理
     */
    @Autowired
    private FunnyAuthenticationSuccessHandler authenticationSuccessHandler;


    /**
     * 配置授权提供
     *
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    /**
     * @param http
     * @return void
     * @note 各种路径设置
     * @author WCF
     * @time 2018/6/12 0:13
     * @since v1.0
     **/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .addFilterBefore(securityFilterInterceptor, FilterSecurityInterceptor.class)
                .authorizeRequests()
                .antMatchers("/", "/show/files/**", "/read/all/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/user/**", "/register").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/home", true).successHandler(authenticationSuccessHandler)
                .failureUrl("/login?error=true").and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .and().httpBasic();
    }

    /**
     * @param web
     * @return void
     * @note 静态资源不进行拦截
     * @author WCF
     * @time 2018/6/12 0:15
     * @since v1.0
     **/
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.
                ignoring().antMatchers("/static/about/**")
                .and().ignoring().antMatchers("/static/admin/**")
                .and().ignoring().antMatchers("/static/blog/**")
                .and().ignoring().antMatchers("/static/common/**")
                .and().ignoring().antMatchers("/static/files/**")
                .and().ignoring().antMatchers("/static/home/**")
                .and().ignoring().antMatchers("/static/images/**")
                .and().ignoring().antMatchers("/static/pdfjs/**")
                .and().ignoring().antMatchers("/static/video/**");
    }
}