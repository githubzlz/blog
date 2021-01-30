package com.zlz.blog.gateway.filter;

import cn.hutool.json.JSONObject;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.gateway.bean.SelfConfigrationBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 登录功能处理
 * @author peeterZ
 * @version 2.0 CreateTime:2021-01-21 15:20
 * @description
 */
@Component
@Slf4j
public class LoginHandlerFilterConfigration implements GlobalFilter, Ordered {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private SelfConfigrationBean selfConfigration;

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        URI uri = exchange.getRequest().getURI();
        String path = uri.getPath();

        // 登录请求重定向到授权中心登录
        if(selfConfigration.loginUri.equals(path)){
            return fallBackToGetCode(exchange);
        }

        // 获取token的请求
        if(selfConfigration.tokenUri.equals(path)){
            // 根据code去获取token添加到缓存中然后返回给前端
            String query = uri.getQuery();
            String[] split = query.split("=");
            String code = split[1];
            // 拿到授权码，去获取token
            String token = tokenHandler(code);
            String resultMessage;
            // 接口返回的message处理
            if(StringUtils.isEmpty(token)){
                resultMessage = new JSONObject(ResultSet.loginError("登录失败！")).toString();
            } else {
                Map<String, String> result = new HashMap<>(1);
                result.put("token", token);
                resultMessage = new JSONObject(ResultSet.success("查询成功", result)).toString();
            }
            return fastFinish(exchange, resultMessage);
        }

        // 退出登录
        if(selfConfigration.logoutUri.equals(path)){
            // gateway清除token，token从缓存中清除之后，使用原有的token无法通过gateway的权限认证
            removeToken(exchange);
            // 告诉前端oauth的退出地址，由前端将页面转到oauth退出登录页面
            // 前端使用ajax请求，重定向无法实现页面的跳转，所以将跳转地址告诉前端由前端去处理是否退出oauth的登录
            String message = new JSONObject(ResultSet.success(selfConfigration.oauthLogoutUrl)).toString();
            return fastFinish(exchange, message);
        }
        return chain.filter(exchange);
    }

    /**
     * 移除缓存中的token
     * @param exchange
     */
    private void removeToken(ServerWebExchange exchange){
        String accessToken = exchange.getRequest().getHeaders().getFirst("Authorization");
        stringRedisTemplate.delete("TOKEN:" + accessToken);
    }

    /**
     * 根据授权中心登陆成功之后重定向传递过来的code去请求token
     * @param code
     * @return
     */
    private String tokenHandler(String code){
        try {
            // 拼接请求token的地址和参数
            String requestTokenAddr = selfConfigration.oauthAddr + "/oauth/token?grant_type=authorization_code"
                    + "&code=" + code
                    + "&client_id=" + selfConfigration.clientId
                    + "&client_secret=" + selfConfigration.clientSecret
                    + "&redirect_uri=" + selfConfigration.redirectUrl;

            // 请求授权服务获取token
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(requestTokenAddr, null, String.class);
            String body = stringResponseEntity.getBody();
            log.info(body);
            String accessToken = new JSONObject(body).getStr("access_token");

            // 将token放入缓存
            stringRedisTemplate.opsForValue().set("TOKEN:Bearer " + accessToken, accessToken, 60*24, TimeUnit.MINUTES);
            return accessToken;
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 重定向到授权中心，去登录，授权。
     * 获取code，授权码
     * @param exchange
     * @return
     */
    private Mono<Void> fallBackToGetCode(ServerWebExchange exchange){
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.FOUND);
        response.getHeaders();
        response.getHeaders().set("Location", selfConfigration.getCodeUrl);
        return exchange.getResponse().setComplete();
    }

    /**
     * 重定向到前端设置token的url
     * @param exchange
     * @return
     */
    private Mono<Void> fallBackToClient(ServerWebExchange exchange, String url){
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.FOUND);
        response.getHeaders().set("Location", url);
        return exchange.getResponse().setComplete();
    }

    /**
     * 结束请求处理，直接返回信息
     * @param exchange
     * @return
     */
    private Mono<Void> fastFinish(ServerWebExchange exchange, String message){
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        DataBuffer dataBuffer = response.bufferFactory().allocateBuffer().write(message.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(dataBuffer));
    }
}
