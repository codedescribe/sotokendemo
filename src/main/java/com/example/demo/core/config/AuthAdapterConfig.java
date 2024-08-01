package com.example.demo.core.config;

import com.example.demo.core.adapter.AuthConfigAdapter;
import com.example.demo.core.adapter.DefaultAuthConfigAdapter;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@Configuration
public class AuthAdapterConfig {

    @Bean
    @ConditionalOnMissingBean //确认该配置只会有一个bean实现
    public AuthConfigAdapter authConfigAdapter(){
        return new DefaultAuthConfigAdapter();
    }
}
