package com.zlz.blog.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

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
