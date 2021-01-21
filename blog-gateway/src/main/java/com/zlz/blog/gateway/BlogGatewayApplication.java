package com.zlz.blog.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 网关项目（路由，鉴权）
 * used：
 * nacos discovery（服务发现），
 * nacos config（配置中心），
 * security oauth2.0（鉴权），
 * webFlux（服务）
 * @author zPeet
 */
@SpringBootApplication
@EnableDiscoveryClient
public class BlogGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogGatewayApplication.class, args);
    }

}
