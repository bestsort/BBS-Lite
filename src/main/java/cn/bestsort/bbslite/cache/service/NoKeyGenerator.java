package cn.bestsort.bbslite.cache.service;

import java.lang.reflect.Method;

/**
 * TODO
 *
 * @author bestsort
 * @version 1.0
 * @date 2/4/20 10:49 AM
 */
public class NoKeyGenerator implements KeyGenerator {
    @Override
    public String generate(Object target, Method method, Object[] params) {
        return "";
    }
}
