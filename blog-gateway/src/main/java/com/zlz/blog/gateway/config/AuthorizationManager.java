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
                    log.info("角色权限："+ role);
                    return checkPath(path, strings);
                })
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

    /**
     * 路由与权限资源匹配检查
     * @param uri
     * @param paths
     * @return
     */
    private boolean checkPath(String uri, List<String> paths){
        log.info("检查开始：");
        if(paths == null || paths.isEmpty() || StringUtils.isEmpty(uri)){
            return false;
        }
        for (String pPath : paths) {
            log.info("路由："+uri+",权限路由："+pPath);
            if("/**".equals(pPath) || pPath.equals(uri)){
                return true;
            }

            // 将路由地址拆分，逐级匹配，若是全部匹配成功则返回成功，否则返回失败
            boolean matching = true;
            String[] pps = pPath.split("/");
            String[] us = uri.split("/");
            // uri的级数小于权限路由的级数 p:"/test" pp:"/test/1"
            boolean isBreak1 = pps.length > us.length;
            // 权限路由的最后一级不是模糊匹配，并且uri与权限路由的长度不同，直接结束本次匹配
            boolean isBreak2 = !"**".equals(pps[pps.length-1]) && pps.length != us.length;
            if(isBreak1 || isBreak2){
                break;
            }

            for (int i = 0; i < us.length; i++) {
                // 匹配到/** 直接返回验证成功，不再需要检验之后的路由地址
                if("**".equals(pps[i])){
                    return true;
                }

                // 匹配到/* 本次检验直接返回验证成功，继续进行之后的检验
                if("*".equals(pps[i])){
                    matching = true;
                    continue;
                }

                // 出现不匹配路由地址的直接结束判断
                matching = pps[i].equals(us[i]);
                if(!matching){
                    break;
                }
            }
            if(matching){
                return matching;
            }
        }
        return false;
    }
}
