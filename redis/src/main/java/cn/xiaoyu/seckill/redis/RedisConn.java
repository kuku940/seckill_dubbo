package cn.xiaoyu.seckill.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * redis连接信息配置类
 *
 * @author Roin_zhang
 * @date 2018/4/6 9:21
 */
@Component
@ConfigurationProperties(prefix = RedisConn.JEDIS_PREFIX)
public class RedisConn {
    public static final String JEDIS_PREFIX = "spring.redis";

    private String host;
    private int port;
    private int timeout;

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

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    @Override
    public String toString() {
        return "Redis [localhost=" + host + ", port=" + port + ", timeout=" + timeout + "]";
    }
}
