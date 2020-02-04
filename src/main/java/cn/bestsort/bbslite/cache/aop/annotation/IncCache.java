package cn.bestsort.bbslite.cache.aop.annotation;

import cn.bestsort.bbslite.cache.enums.CacheType;
import cn.bestsort.bbslite.cache.enums.Time;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.METHOD;

/**
 * @author bestsort
 */
@Target(METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Cache
public @interface IncCache {
    /**
     * 自增步长
     * @return step
     */
    @AliasFor("step")
    long value() default 1;
    @AliasFor("value")
    long step() default 1;

    String keyGenerator() default "noKeyGenerator";

    /**
     * seconds / minutes / hour / day
     * @return 时间类型
     */
    Time timeType() default Time.HOUR;
    /**
     * nx: key不存在时再进行缓存 k-v
     * xx: key存在时再缓存 k-v
     * @return 缓存策略
     */
    String strategy() default "nx";
    /**
     * @return 是否采用随机TTL(时间范围在[min,max])之间
     */
    boolean random() default true;

    /**
     * 随机TTL的边界,若 max < min 则会默认max = min + 3;
     * @return 边界值
     */
    long min() default 10L;
    long max() default 12L;

    /**
     * 自定义 key, 支持SpEL表达式
     * @return key
     */
    String key() default "";
    CacheType cacheType() default CacheType.INC;
}
