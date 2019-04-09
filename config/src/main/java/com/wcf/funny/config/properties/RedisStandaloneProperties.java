package com.wcf.funny.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * @author wangcanfeng
 * @description redis配置信息
 * @Date Created in 11:07-2019/4/1
 */
@ConfigurationProperties(prefix = "spring.redis")
public class RedisStandaloneProperties {
    /**
     * redis的数据库索引
     */
    private int database = 0;

    /**
     * host地址，可以输入host的ip地址
     */
    private String host;

    /**
     * redis服务的端口号
     */
    private int port;

    /**
     * 连接超时时间
     */
    private Duration timeout;

    /**
     * lettuce的配置信息
     */
    private final Lettuce lettuce = new Lettuce();

    /**
     * lettuce的配置信息.
     */
    public static class Lettuce {

        /**
         * 停止服务的超时时间.
         */
        private Duration shutdownTimeout = Duration.ofMillis(100);

        /**
         * lettuce连接池配置信息.
         */
        private Pool pool;

        public Duration getShutdownTimeout() {
            return this.shutdownTimeout;
        }

        public void setShutdownTimeout(Duration shutdownTimeout) {
            this.shutdownTimeout = shutdownTimeout;
        }

        public Pool getPool() {
            return this.pool;
        }

        public void setPool(Pool pool) {
            this.pool = pool;
        }

    }

    /**
     * 连接池的配置信息.
     */
    public static class Pool {

        /**
         * 最大的空闲连接数
         */
        private int maxIdle;

        /**
         * 最少需要保持的空闲连接数
         */
        private int minIdle;

        /**
         * 最大活跃连接数
         */
        private int maxActive;

        /**
         * 最大等待时间，也就是阻塞时间
         */
        private Duration maxWait;

        public int getMaxIdle() {
            return this.maxIdle;
        }

        public void setMaxIdle(int maxIdle) {
            this.maxIdle = maxIdle;
        }

        public int getMinIdle() {
            return this.minIdle;
        }

        public void setMinIdle(int minIdle) {
            this.minIdle = minIdle;
        }

        public int getMaxActive() {
            return this.maxActive;
        }

        public void setMaxActive(int maxActive) {
            this.maxActive = maxActive;
        }

        public Duration getMaxWait() {
            return this.maxWait;
        }

        public void setMaxWait(Duration maxWait) {
            this.maxWait = maxWait;
        }

    }


    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Duration getTimeout() {
        return timeout;
    }

    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }

    public Lettuce getLettuce() {
        return lettuce;
    }
}
