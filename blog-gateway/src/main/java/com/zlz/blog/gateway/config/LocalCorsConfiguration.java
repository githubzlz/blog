package com.zlz.blog.gateway.config;

import com.zlz.blog.gateway.bean.SelfConfigrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

import javax.annotation.Resource;

/**
 * 允许跨域的配置
 * @author peeterZ
 * @version 2.0 CreateTime:2021-01-26 16:58
 * @description
 */
@Configuration
public class LocalCorsConfiguration {

    @Resource
    private SelfConfigrationBean selfConfigration;

    /**
     * 配置跨域
     * 前端请求严格模式下，allowedOrigins 不能为*
     * @return
     */
    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // cookie跨域
        config.setAllowCredentials(Boolean.TRUE);
        config.addAllowedMethod("*");
        config.addAllowedOrigin(selfConfigration.redirectUrl);
        config.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }
}
