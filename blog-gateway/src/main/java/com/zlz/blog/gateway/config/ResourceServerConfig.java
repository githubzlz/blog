package com.zlz.blog.gateway.config;

import cn.hutool.json.JSONObject;
import com.zlz.blog.common.response.ResultSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * 资源服务器配置
 * @author 12101
 */
@Configuration
@EnableWebFluxSecurity
@Slf4j
public class ResourceServerConfig {

    @Resource
    private final AuthorizationManager authorizationManager;

    @Resource
    private final AuthorizationInfo authorizationInfo;

    public ResourceServerConfig(AuthorizationManager authorizationManager, AuthorizationInfo authorizationInfo) {
        this.authorizationManager = authorizationManager;
        this.authorizationInfo = authorizationInfo;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        String[] writeUrls = authorizationInfo.getWriteUrls().toArray(new String[0]);
        http.oauth2ResourceServer().jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter());
        http.oauth2ResourceServer().authenticationEntryPoint(authenticationEntryPoint());
        http.authorizeExchange()
                .pathMatchers(writeUrls).permitAll()
                .anyExchange().access(authorizationManager)
                .and()
                .exceptionHandling()
                //处理未认证
                .authenticationEntryPoint(authenticationEntryPoint())
                // 处理未授权
                .accessDeniedHandler(accessDeniedHandler())
                .and().csrf().disable();

        return http.build();
    }

    /**
     * 未授权
     *
     * @return
     */
    @Bean
    ServerAccessDeniedHandler accessDeniedHandler() {
        return (exchange, denied) -> Mono.defer(() -> Mono.just(exchange.getResponse()))
                .flatMap(response -> {
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                    response.getHeaders().set(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "*");
                    response.getHeaders().set(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:4200");
                    response.getHeaders().set(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "*");
                    response.getHeaders().set(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
                    String body = new JSONObject(ResultSet.error("访问未授权,若有权限,请尝试重新登录")).toString();
                    DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
                    return response.writeWith(Mono.just(buffer))
                            .doOnError(error -> DataBufferUtils.release(buffer));
                });
    }

    /**
     * token无效或者已过期自定义响应
     */
    @Bean
    ServerAuthenticationEntryPoint authenticationEntryPoint() {
        return (exchange, e) -> Mono.defer(() -> Mono.just(exchange.getResponse()))
            .flatMap(response -> {
                log.info(e.getMessage(), e.getClass());
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                response.getHeaders().set(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "*");
                response.getHeaders().set(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:4200");
                response.getHeaders().set(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "*");
                response.getHeaders().set(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
                String body = new JSONObject(ResultSet.error("TOKEN无效或者已过期,请尝试重新登录")).toString();
                DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
                return response.writeWith(Mono.just(buffer))
                        .doOnError(error -> DataBufferUtils.release(buffer));
            });
    }



    /**
     * @return
     * @link https://blog.csdn.net/qq_24230139/article/details/105091273
     * ServerHttpSecurity没有将jwt中authorities的负载部分当做Authentication
     * 需要把jwt的Claim中的authorities加入
     * 方案：重新定义R 权限管理器，默认转换器JwtGrantedAuthoritiesConverter
     */
    @Bean
    public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }
}