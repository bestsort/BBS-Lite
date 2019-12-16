package cn.bestsort.bbslite.cache;

import java.lang.reflect.Method;

/**
 * TODO
 *
 * @author bestsort
 * @version 1.0
 * @date 12/16/19 8:44 AM
 */

public interface KeyGeneratorInterface {
    /**
     * @param target 要生成key的对象
     * @param method 所调用的方法
     * @param params 方法内参数
     * @return key
     */
    Object generate(Object target, Method method, Object... params);

    /**
     * @param prefix 前缀
     * @param params 参数
     * @return key
     */
    Object generate(String prefix,Object... params);
}
