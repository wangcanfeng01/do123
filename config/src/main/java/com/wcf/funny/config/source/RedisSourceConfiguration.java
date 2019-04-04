package com.wcf.funny.config.source;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.lang.Nullable;

/**
 * @author wangcanfeng
 * @time 2019/3/30
 * @function redis配置信息
 **/
//@Configuration
//@EnableCaching
public class RedisSourceConfiguration extends CachingConfigurerSupport {



    @Nullable
    @Override
    public CacheManager cacheManager() {
        return super.cacheManager();
    }

    @Nullable
    @Override
    public CacheResolver cacheResolver() {
        return super.cacheResolver();
    }

    @Nullable
    @Override
    public KeyGenerator keyGenerator() {
        return super.keyGenerator();
    }

    @Nullable
    @Override
    public CacheErrorHandler errorHandler() {
        return super.errorHandler();
    }
}
