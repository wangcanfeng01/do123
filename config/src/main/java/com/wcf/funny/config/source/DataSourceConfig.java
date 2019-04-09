package com.wcf.funny.config.source;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author WCF
 * @time 2018/3/20
 * @why 自定义数据库连接池
 **/
@Configuration
@Log4j2
public class DataSourceConfig {
    /**
     * 数据库url
     */
    @Value("${spring.datasource.url}")
    private String dbUrl;

    /**
     * 数据库用户名
     */
    @Value("${spring.datasource.username}")
    private String username;

    /**
     * 数据库密码
     */
    @Value("${spring.datasource.password}")
    private String password;

    /**
     * 驱动器名称
     */
    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;

    /**
     * 初始池大小
     */
    @Value("${spring.datasource.initialSize}")
    private int initialSize;

    /**
     * 最小句柄
     */
    @Value("${spring.datasource.minIdle}")
    private int minIdle;

    /**
     * 最大的活者的连接
     */
    @Value("${spring.datasource.maxActive}")
    private int maxActive;

    /**
     * 最大等待时间
     */
    @Value("${spring.datasource.maxWait}")
    private int maxWait;

    /**
     *@note 设置主连接池
     *@author WCF
     *@time 2018/6/11 23:55
     *@since v1.0
     * @param
     *@return javax.sql.DataSource
     **/
    @Bean
    @Primary
    public DataSource dataSource(){
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(this.dbUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);
        //configuration
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        log.info("dataBase initialized successfully");
        return datasource;
    }
}
