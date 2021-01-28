package com.zlz.blog.gateway.filter;

import cn.hutool.json.JSONObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author peeterZ
 * @version 2.0 CreateTime:2021-01-21 15:20
 * @description 登录逻辑处理
 */
@Component
@Slf4j
public class LoginHandlerFilterConfigration implements GlobalFilter, Ordered {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private ReactiveJwtAuthenticationConverterAdapter jwtAuthenticationConverter;

    @Value("${self.login.loginUrl}")
    private String loginUrl;
    @Value("${self.login.codeUrl}")
    private String codeUrl;

    @Value("${self.token.getCodeUrl}")
    private String getCodeUrl;
    @Value("${self.token.tokenUrl}")
    private String tokenUrl;
    @Value("${self.token.frontendUrl}")
    private String frontendUrl;

    private String userInfoUrl = "/login/user";

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        URI uri = exchange.getRequest().getURI();
        String path = uri.getPath();

        // 登录请求重定向到授权中心登录
        if(loginUrl.equals(path)){
            return fallBack(exchange);
        }

        // 获取到token之后回调前端的地址，去告诉前端token
        if(codeUrl.equals(path)){
            String query = uri.getQuery();
            String[] split = query.split("=");
            String code = split[1];
            String token = getToken(code);
            return fallBack(exchange, token);
        }
        return chain.filter(exchange);
    }

    /**
     * 根据授权中心登陆成功之后重定向传递过来的code去请求token
     * @param code
     * @return
     */
    private String getToken(String code){
        try {
            String uri = "http://localhost:8081/oauth/token?grant_type=authorization_code&code="
                    + code + "&client_id=dev&client_secret=123456&redirect_uri=http://localhost:8080/login/code";

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(uri, null, String.class);
            String body = stringResponseEntity.getBody();
            log.info(body);
            String accessToken = new JSONObject(body).getStr("access_token");
            stringRedisTemplate.opsForValue().set("TOKEN:Bearer " + accessToken, accessToken, 60*24, TimeUnit.MINUTES);
            return accessToken;
        }catch (Exception e){
            e.printStackTrace();
            return "获取token失败";
        }
    }

    /**
     * 重定向到授权中心，去登录，授权。
     * 获取code，授权码
     * @param exchange
     * @return
     */
    private Mono<Void> fallBack(ServerWebExchange exchange){
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.FOUND);
        response.getHeaders();
        response.getHeaders().set("Location", getCodeUrl);
        return exchange.getResponse().setComplete();
    }

    /**
     * 重定向到前端设置token的url
     * @param exchange
     * @param token
     * @return
     */
    private Mono<Void> fallBack(ServerWebExchange exchange, String token){
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.FOUND);
        response.getHeaders().set("Location", frontendUrl + "?access_token=" + token);
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
