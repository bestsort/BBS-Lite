package cn.bestsort.bbslite.cache.aop.annotation;


import cn.bestsort.bbslite.cache.enums.CacheType;
import cn.bestsort.bbslite.cache.enums.Time;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;
/**
 * @author bestsort
 */
@Target({METHOD,ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Cache {
    /**
     * 自定义 key, 支持SpEL表达式
     * @return key
     */
    String key() default "";

    /**
     * key 生成器
     * @return keyGenerator class
     */
    String keyGenerator() default "defaultKeyGenerator";
    /**
     * 缓存类型 default/inc/flush/delete -> 默认/自增/刷新/删除
     * default --> 如果不存在则查询数据库,将查询结果加入redis中
     * inc --> 用于需要计数的场景, 配合step可以实现缓存的自增
     * dec --> 自减
     * flush --> 刷新当前缓存
     * delete --> 删除当前缓存
     * @return default
     */

    CacheType cacheType() default CacheType.DEFAULT;
    /**
     * seconds / minutes / hour / day
     * @return 时间类型
     */
    Time timeType() default Time.MINUTES;
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
    long min() default 3L;
    long max() default 6L;

    /**
     * 自增步长,默认为1
     * @return step
     */
    long step() default 1L;
}
