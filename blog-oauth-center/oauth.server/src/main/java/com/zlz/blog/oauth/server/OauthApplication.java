package com.zlz.blog.oauth.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 程序入口
 * @author 12101
 */
@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = { "com.zlz.blog.client" })
public class OauthApplication {


    public static void main(String[] args) {
        SpringApplication.run(OauthApplication.class, args);
    }

}
