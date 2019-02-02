package com.wcf.funny.config.mvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;


/**
 * @author WCF
 * @time 2018/12/23
 * @why 功能：mcv的配置信息
 **/
@EnableWebMvc
@Configuration
public class FunnyWebMvcConfigurer implements WebMvcConfigurer {

    /**
     * @param registry
     * @return void
     * @note 设置路径映射
     * @author WCF
     * @time 2018/12/25 22:38
     * @since v1.0
     **/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/index.html").addResourceLocations("classpath:/index.html");
        registry.addResourceHandler("/static/css/**").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/static/fonts/**").addResourceLocations("classpath:/static/fonts/");
        registry.addResourceHandler("/static/img/**").addResourceLocations("classpath:/static/img/");
        registry.addResourceHandler("/static/js/**").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/upload/**").addResourceLocations("classpath:/");
    }
}
