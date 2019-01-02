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
        registry.addResourceHandler("/static/about/**").addResourceLocations("classpath:/static/about/");
        registry.addResourceHandler("/static/admin/**").addResourceLocations("classpath:/static/admin/");
        registry.addResourceHandler("/static/blog/**").addResourceLocations("classpath:/static/blog/");
        registry.addResourceHandler("/static/common/**").addResourceLocations("classpath:/static/common/");
        registry.addResourceHandler("/static/files/**").addResourceLocations("classpath:/static/files/");
        registry.addResourceHandler("/static/home/**").addResourceLocations("classpath:/static/home/");
        registry.addResourceHandler("/static/images/**").addResourceLocations("classpath:/static/images/");
        registry.addResourceHandler("/static/pdfjs/**").addResourceLocations("classpath:/static/pdfjs/");
    }
}
