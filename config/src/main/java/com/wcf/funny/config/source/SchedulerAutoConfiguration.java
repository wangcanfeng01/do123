package com.wcf.funny.config.source;

import org.quartz.Scheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @author wangcanfeng
 * @time 2019/3/15
 * @function 配置scheduler工厂和scheduler，这里可以注入预先准备好的executor
 * 或者使用系统默认的executor，都很方便，也可以把一些关键的参数放置在配置文件中，通过配置文件加载
 **/
@Configuration
public class SchedulerAutoConfiguration {

    @Bean(name = "SchedulerFactory")
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setAutoStartup(true);
        //这里如果不配置任务池，它就会默认加载SimpleThreadPool
        //factory.setTaskExecutor();
        return factory;
    }

    @Bean(name = "funnyScheduler")
    public Scheduler scheduler() {
        return schedulerFactoryBean().getScheduler();
    }
}
