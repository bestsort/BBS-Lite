package cn.bestsort.bbslite.config;

import cn.bestsort.bbslite.cache.FastJson2JsonRedisSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

import java.time.Duration;

/**
 * 不要导入com.alibaba.fastjson.support.spring.FastJsonRedisSerializer,自己实现反序列化工具
 * <p>千年大坑,切忌切忌</p>
 * @author bestsort
 */
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    @Bean
    public KeyGenerator myKeyGenerator() {
        return (target, method, params) -> {
            String buffer = target.getClass().getSimpleName();
            return buffer + "_" + method.getName() + "_"
              + StringUtils.arrayToDelimitedString(params, "_");
        };
    }


    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
        //Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
        //使用Fastjson2JsonRedisSerializer来序列化和反序列化redis的value值 by zhengkai
        FastJson2JsonRedisSerializer serializer = new FastJson2JsonRedisSerializer(Object.class);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(mapper);

        template.setValueSerializer(serializer);
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        FastJson2JsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJson2JsonRedisSerializer<>(Object.class);
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();
        configuration = configuration.serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(fastJsonRedisSerializer))
        .entryTtl(Duration.ofMinutes(5));
        return configuration;
    }
}
