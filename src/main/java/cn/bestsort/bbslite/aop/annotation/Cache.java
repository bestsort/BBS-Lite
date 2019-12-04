package cn.bestsort.bbslite.aop.annotation;

import java.lang.annotation.*;

/**
 * @author bestsort
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {
    /**
     * 默认 秒/分/时/天 --> S/M/H/D
     * @return 时间类型
     */
    String type() default "M";
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
}
