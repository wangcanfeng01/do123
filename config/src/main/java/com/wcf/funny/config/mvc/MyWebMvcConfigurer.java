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
public class MyWebMvcConfigurer implements WebMvcConfigurer {

    /**
     *@note 设置路径映射
     *@author WCF
     *@time 2018/12/25 22:38
     *@since v1.0
     * @param registry
     *@return void
     **/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/templates/");
        registry.addResourceHandler("/static/common/**").addResourceLocations("classpath:/static/common/");
        registry.addResourceHandler("/static/images/**").addResourceLocations("classpath:/static/images/");
    }
}
