package com.wcf.funny.config.security;

import com.wcf.funny.config.security.handler.FunnyAuthenticationFailureHandler;
import com.wcf.funny.config.security.handler.FunnyAuthenticationSuccessHandler;
import com.wcf.funny.config.security.handler.FunnyLogoutSuccessHandler;
import com.wcf.funny.config.security.provider.FunnyAuthenticationProvider;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
     * 是否开启权限
     */
    @Value("${open.security}")
    private String openSecurity;

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
     * 鉴权失败处理器
     */
    @Autowired
    private FunnyAuthenticationFailureHandler authenticationFailureHandler;

    /**
     * 退出登录处理
     */
    @Autowired
    private FunnyLogoutSuccessHandler logoutSuccessHandler;


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
        String[] paths;
        if (Boolean.valueOf(openSecurity)) {
            paths = new String[]{"/ui/user/get/login", "/ui/user/register","/ui/search/**", "/", "/home", "/index.html"};
        } else {
            //不开启权限的时候直接打开所有路径
            paths = new String[]{"/**"};
        }
        http
                .csrf().disable().cors().disable()
                .addFilterBefore(securityFilterInterceptor, FilterSecurityInterceptor.class)
                .authorizeRequests()
                .antMatchers(paths).permitAll()
                .anyRequest().authenticated().and()
                .formLogin().loginPage("/login").loginProcessingUrl("/ui/user/login").permitAll()
                .successHandler(authenticationSuccessHandler).failureHandler(authenticationFailureHandler).and()
                .logout().logoutUrl("/ui/user/logout").permitAll().logoutSuccessHandler(logoutSuccessHandler)
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
        web.ignoring().antMatchers("/templates/index.html")
                .and().ignoring().antMatchers("/highlightjs/**")
                .and().ignoring().antMatchers("/katex/**")
                .and().ignoring().antMatchers("/markdown/**")
                .and().ignoring().antMatchers("/static/css/**")
                .and().ignoring().antMatchers("/static/fonts/**")
                .and().ignoring().antMatchers("/static/img/**")
                .and().ignoring().antMatchers("/static/js/**")

                .and().ignoring().antMatchers("/favicon.ico")
                .and().ignoring().antMatchers("/upload/files/**")
                .and().ignoring().antMatchers("/upload/image/face/**")
                .and().ignoring().antMatchers("/upload/image/article/**")
                .and().ignoring().antMatchers("/upload/image/cover/article/**")
                .and().ignoring().antMatchers("/upload/image/cover/category/**");
    }
}