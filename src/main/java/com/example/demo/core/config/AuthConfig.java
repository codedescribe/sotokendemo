package com.example.demo.core.config;

import cn.hutool.core.util.ArrayUtil;
import com.example.demo.core.adapter.AuthConfigAdapter;
import com.example.demo.core.adapter.DefaultAuthConfigAdapter;
import com.example.demo.core.filter.AuthFilter;
import jakarta.servlet.DispatcherType;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@AllArgsConstructor
public class AuthConfig {
    private final AuthFilter authFilter;

    @Bean
    @Lazy //使用时才注册
    public FilterRegistrationBean<AuthFilter> filterRegistrationBean(AuthConfigAdapter authConfigAdapter){
        FilterRegistrationBean<AuthFilter> registration = new FilterRegistrationBean<>();
        // 添加过滤器
        registration.setFilter(authFilter);
        // 设置过滤路径，/*所有路径
//        registration.addUrlPatterns(ArrayUtil.toArray(authConfigAdapter.pathPatterns(), String.class));
        registration.setName("authFilter");
        // 设置优先级
        registration.setOrder(0);
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        return registration;
    }
}
