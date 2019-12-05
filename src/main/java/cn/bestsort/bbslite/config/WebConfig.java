package cn.bestsort.bbslite.config;

import cn.bestsort.bbslite.interceptor.SessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * Web 本地配置(SpringBoot 的坑点,需要自己指定资源文件位置)
 * @author bestsort
 * @date 19-8-31 下午7:25
 * @version 1.0
 */

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private SessionInterceptor sessionInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/public/");
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        //拦截除静态资源以外的 Url
        //不拦截的列表
        List<String> exclude = new ArrayList<>(4);
        exclude.add("/js/**");
        exclude.add("/css/**");
        exclude.add("/favicon.ico");
        exclude.add("/");
        //未登陆拦截器
        registry.addInterceptor(sessionInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(exclude);
    }
}
