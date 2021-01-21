package com.zlz.blog.oauth.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 程序入口
 * @author 12101
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OauthApplication {


    public static void main(String[] args) {
        SpringApplication.run(OauthApplication.class, args);
    }

}
