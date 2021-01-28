package com.zlz.blog.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author peeterZ
 * @version 2.0 CreateTime:2021-01-26 16:58
 * @description
 */
@Configuration
public class BeanConfiguration {

    @Bean
    public AuthorizationInfo getAuthorizationInfo(){
        return new AuthorizationInfo();
    }
}
