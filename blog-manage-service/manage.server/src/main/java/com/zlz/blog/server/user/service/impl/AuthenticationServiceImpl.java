package com.zlz.blog.server.user.service.impl;

import cn.hutool.json.JSONArray;
import com.zlz.blog.common.entity.user.SysPermission;
import com.zlz.blog.server.user.mapper.AuthenticationMapper;
import com.zlz.blog.server.user.service.AuthenticationService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author peeterZ
 * @version 2.0 CreateTime:2021-01-22 11:40
 * @description
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Resource
    private AuthenticationMapper authenticationMapper;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public List<SysPermission> getPermissionAll() {
        return authenticationMapper.getAllPermission();
    }

    @Override
    public boolean refreshPermission() {
        List<SysPermission> permissionAll = this.getPermissionAll();
        JSONArray array = new JSONArray(permissionAll);
        redisTemplate.opsForValue().set("PERMISSIONS",array.toString());
        return true;
    }
}
