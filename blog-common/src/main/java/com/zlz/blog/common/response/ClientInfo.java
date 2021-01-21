package com.zlz.blog.common.response;

import java.io.Serializable;

/**
 * 客户端信息
 * @author zhulinzhong
 * @version 1.0 CreateTime:2019/10/28 17:36
 */
public class ClientInfo implements Serializable {

    private static final long serialVersionUID = 1115663724073338955L;

    private String clientId;
    private String secret;
    private String token;
    private String requestUrl;
    private String tokenName;

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
