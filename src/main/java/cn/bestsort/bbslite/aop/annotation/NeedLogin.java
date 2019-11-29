package cn.bestsort.bbslite.aop.annotation;

import java.lang.annotation.*;

/**
 * @author bestsort
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NeedLogin {
    String needLogin() default "";
}
