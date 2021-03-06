package com.zlz.blog.server;

import com.zlz.fastdfs.config.FastdfsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * springboot启动类
 *
 * @author zhulinzhong
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class BlogManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogManageApplication.class, args);
    }
}
