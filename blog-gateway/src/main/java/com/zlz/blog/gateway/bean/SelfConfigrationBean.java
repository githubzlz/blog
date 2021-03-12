package com.zlz.blog.gateway.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author peeterZ
 * @version 2.0 CreateTime:2021-01-30 09:25
 * @description 自定义配置
 */
@Configuration
public class SelfConfigrationBean {

    /**
     * gateway客户端id
     */
    @Value("${self.gateway.clientId}")
    public String clientId;

    /**
     * gateway客户端密码
     */
    @Value("${self.gateway.clientSecret}")
    public String clientSecret;

    /**
     * gateway登录地址
     */
    @Value("${self.gateway.loginUri}")
    public String loginUri;

    /**
     * gateway请求token地址
     */
    @Value("${self.gateway.tokenUri}")
    public String tokenUri;

    /**
     * gateway退出登录地址
     */
    @Value("${self.gateway.logoutUri}")
    public String logoutUri;

    /**
     * oauth服务端地址addr
     */
    @Value("${self.oauth.addr}")
    public String oauthAddr;

    /**
     * oauth验证客户端通过时，返回授权码的回调地址
     */
    @Value("${self.oauth.redirectUrl}")
    public String redirectUrl;

    /**
     * 去oauth申请授权码的地址
     */
    @Value("${self.oauth.getCodeUrl}")
    public String getCodeUrl;

    /**
     * oauth退出登录地址
     */
    @Value("${self.oauth.logoutUrl}")
    public String oauthLogoutUrl;

    @Value("${self.gateway.cros-url}")
    public String originUrl;
}
