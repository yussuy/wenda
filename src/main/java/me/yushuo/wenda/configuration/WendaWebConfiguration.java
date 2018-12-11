package me.yushuo.wenda.configuration;

import me.yushuo.wenda.interceptor.LoginRequestInterceptor;
import me.yushuo.wenda.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Component
public class WendaWebConfiguration extends WebMvcConfigurationSupport {
    @Autowired
    PassportInterceptor passportInterceptor;

    @Autowired
    LoginRequestInterceptor loginRequestInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);
        registry.addInterceptor(loginRequestInterceptor).addPathPatterns("/user/*");
        super.addInterceptors(registry);
    }
}
