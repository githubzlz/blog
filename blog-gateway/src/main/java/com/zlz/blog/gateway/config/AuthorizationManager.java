package com.zlz.blog.gateway.config;

/**
 * 鉴权管理器
 * @author peeterZ
 * @version 2.0 CreateTime:2021-01-18 17:35
 * @description
 */

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 鉴权管理器
 * @author 12101
 */
@Component
@AllArgsConstructor
@Slf4j
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Resource
    private AuthorizationInfo authorizationInfo;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        String path = request.getURI().getPath();

        // 对应跨域的预检请求直接放行
        if (request.getMethod() == HttpMethod.OPTIONS) {
            return Mono.just(new AuthorizationDecision(true));
        }

        // token为空拒绝访问
        String token = request.getHeaders().getFirst("Authorization");
        if (StringUtils.isBlank(token)) {
            return Mono.just(new AuthorizationDecision(false));
        }

        // 去redis检查token,redis中不存在的token，即认为是过时的token
        token = stringRedisTemplate.opsForValue().get("TOKEN:" + token);
        if(StringUtils.isEmpty(token)){
            return Mono.just(new AuthorizationDecision(false));
        }

        // 从缓存取资源权限角色关系列表
        Map<String, List<String>> permissions = authorizationInfo.getPermissions();

        // 检查改路径是否存在于用户的可访问资源中
        return mono
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(role -> {
                    // 根据用户权限获取用户可以通过的资源列表
                    List<String> strings = permissions.get(role);
                    System.out.println("角色权限："+ role);
                    return checkPath(path, strings);
                })
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

    private boolean checkPath(String path, List<String> paths){
        log.info("检查开始：");
        if(paths == null || paths.isEmpty() || StringUtils.isEmpty(path)){
            return false;
        }
        for (String pPath : paths) {
            log.info("路由："+path+",权限路由："+pPath);
            if("/**".equals(pPath)){
                return true;
            }
            if(pPath.length() >= 4){
                // 最后三位为 "/**"
                String substring = pPath.substring(pPath.length() - 3, pPath.length());
                if("/**".equals(substring) && path.length() >= pPath.length()-1){
                    // 检查前面的几位是否匹配
                    pPath = pPath.substring(0, pPath.length() - 3);
                    String subPath = path.substring(0, pPath.length());
                    if(pPath.equals(subPath)){
                        return true;
                    }
                }
            }
            if(pPath.equals(path)){
                return true;
            }
        }
        return false;
    }
}