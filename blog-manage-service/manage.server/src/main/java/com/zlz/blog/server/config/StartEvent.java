package com.zlz.blog.server.config;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.zlz.blog.common.entity.user.SysPermission;
import com.zlz.blog.server.user.service.AuthenticationService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author peeterZ
 * @version 2.0 CreateTime:2021-01-27 10:19
 * @description 启动完成之后初始化redis缓存
 */
@Component
public class StartEvent implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private AuthenticationService authenticationService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<SysPermission> permissionAll = authenticationService.getPermissionAll();
        JSONArray array = new JSONArray(permissionAll);
        stringRedisTemplate.opsForValue().set("PERMISSIONS", array.toString());
    }
}
