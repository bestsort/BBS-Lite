package cn.bestsort.bbslite.cache.aop.annotation;

import cn.bestsort.bbslite.cache.RedisCacheConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 允许使用 @Cache注解
 * @author bestsort
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RedisCacheConfig.class)
public @interface EnableRedisCache { }
