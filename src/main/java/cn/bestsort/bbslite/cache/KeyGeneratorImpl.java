package cn.bestsort.bbslite.cache;

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
public class KeyGeneratorImpl implements KeyGeneratorInterface {
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
    @Override
    public String generate(String prefix, Object... params) {
        StringBuffer key = new StringBuffer(prefix);
        for (Object obj : params) {
            key.append(obj.toString());
        }
        return key.toString();
    }
}
