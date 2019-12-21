package cn.bestsort.bbslite.cache.service;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * TODO
 *
 * @author bestsort
 * @version 1.0
 * @date 12/16/19 8:44 AM
 */
public interface KeyGenerator {
    /**
     * @param target 要生成key的对象
     * @param method 所调用的方法
     * @param params 方法内参数
     * @return key
     */

    String generate(Object target, Method method, Object[] params);

}
