package cn.bestsort.bbslite.cache;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * TODO
 * @author bestsort
 * @date 2019/12/4 下午7:46
 * @version 1.0
 */
@Slf4j
@Configuration
@Getter
@Setter
public class JedisConfig extends CachingConfigurerSupport {
    @Value("${spring.datasource.redis.port:6379}")
    private Integer port;
    @Value("${spring.datasource.redis.host:localhost}")
    private String host;
    @Value("${spring.datasource.redis.pool.max-active:16}")
    private Integer maxTotal;
    @Value("${spring.datasource.redis.pool.max-idle:0}")
    private Integer maxIdle;
    @Value("${spring.datasource.redis.pool.max-wait:50}")
    private Long maxWaitMillis;
    @Value("${spring.datasource.redis.password:}")
    private String password;

    public JedisConfig() {}
    /**设置*/
    @Bean
    public JedisPool redisPoolFactory(){
        if (password.isEmpty()){
            password = null;
        }
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        jedisPoolConfig.setMaxTotal(maxTotal);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig,host,port,1000,password);
        log.info("JedisPool build success!");
        log.info("Redis host：{}:{}",host,port);
        return  jedisPool;
    }
}
