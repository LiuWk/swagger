package com.nepenthe.demo.config.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置
 *
 * @author lwk
 * @date 2019-05-08 11:30
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                // 拦截的路径
                .addPathPatterns("/**")
                // 添加不拦截路径
                .excludePathPatterns("/","/user/login","/error","/static/**","/user/logout");
    }

}
