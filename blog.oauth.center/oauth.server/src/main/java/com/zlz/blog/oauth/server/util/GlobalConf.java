package com.zlz.blog.oauth.server.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-06-12 08:26
 * @description
 */
@Component
public class GlobalConf {

    @Value("${gateway.server.url}")
    private String gatewayUrl;

    @Value("localhost")
    private String myHost;

    @Value("${server.port}")
    private String myPort;

    @Value("${gateway.server.port}")
    private Integer gatewayPort;

    public String getGatewayUrl() {
        return gatewayUrl;
    }

    public void setGatewayUrl(String gatewayUrl) {
        this.gatewayUrl = gatewayUrl;
    }

    public String getMyHost() {
        return myHost;
    }

    public void setMyHost(String myHost) {
        this.myHost = myHost;
    }

    public String getMyPort() {
        return myPort;
    }

    public void setMyPort(String myPort) {
        this.myPort = myPort;
    }

    public Integer getGatewayPort() {
        return gatewayPort;
    }

    public void setGatewayPort(Integer gatewayPort) {
        this.gatewayPort = gatewayPort;
    }
}
