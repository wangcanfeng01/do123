package com.wcf.funny.config.source;

import com.wcf.funny.core.utils.I18Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * @author wangcanfeng
 * @time 2019/3/4
 * @function 多语言资源自动载入
 **/
@Configuration
public class MessageSourceAutoConfiguration {
    /**
     * 多语言的资源文件路径
     */
    @Value("${spring.messages.basename}")
    private String path;

    /**
     * 功能描述：  加载一下语言资源
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/4 22:03
     * @since v1.0
     **/
    @Bean("messageSource")
    @ConditionalOnMissingBean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        String[] paths=path.split(";");
        messageSource.setBasenames(paths);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setFallbackToSystemLocale(false);
        I18Utils.setMessageSource(messageSource);
        return messageSource;
    }
}
