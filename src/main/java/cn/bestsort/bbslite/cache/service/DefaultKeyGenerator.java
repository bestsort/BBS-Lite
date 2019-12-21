package cn.bestsort.bbslite.cache.service;

import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * TODO
 *
 * @author bestsort
 * @version 1.0
 * @date 12/16/19 8:51 AM
 */
@Component
public class DefaultKeyGenerator implements KeyGenerator {
    @Override
    public String generate(Object target, Method method, Object... params) {
        //这里采用StringBuffer而不是StringBuilder以保证线程安全
        StringBuffer key = new StringBuffer(target.getClass().getSimpleName()).append(":");
        key.append(method.getName())
        .append(":");
        for (Object obj : params) {
            key.append(obj.toString());
        }
        return key.toString();
    }
}
