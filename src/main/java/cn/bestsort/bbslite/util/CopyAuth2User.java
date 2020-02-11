package cn.bestsort.bbslite.util;

import cn.bestsort.bbslite.pojo.model.OAuth2User;
import cn.bestsort.bbslite.pojo.model.User;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.config.AopNamespaceHandler;
import org.springframework.aop.config.AopNamespaceUtils;

/**
 * @author bestsort
 */
public class CopyAuth2User {
    public User copy2User(OAuth2User auth){
        User user = new User();
        user.setBio(auth.getRemark());
        user.setName(auth.getNickname());
        user.setAvatarUrl(auth.getAvatar());
        user.setGmtModified(System.currentTimeMillis());
        user.setEmail(auth.getEmail());
        user.setHtmlUrl(auth.getBlog());
        user.setGmtCreate(user.getGmtModified());
        return user;
    }
}
