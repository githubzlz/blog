package com.zlz.blog.gateway.bean;

import cn.hutool.json.JSONArray;
import com.zlz.blog.common.entity.oauth.SysPermission;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 保存权限信息的bean
 * @author peeterZ
 * @version 2.0 CreateTime:2021-01-26 16:41
 */
@Component
public class AuthorizationInfo {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private final Map<String, List<String>> permissions = new HashMap<>();

    public Map<String, List<String>> getPermissions() {
        String s = stringRedisTemplate.opsForValue().get("PERMISSIONS");
        JSONArray array = new JSONArray(s);
        List<SysPermission> sysPermissions = array.toList(SysPermission.class);
        this.setPermissions(sysPermissions);
        return permissions;
    }

    /**
     * 从缓存中获取所有的权限信息
     * @param permissions
     */
    public void setPermissions(List<SysPermission> permissions) {
        if (permissions != null){
            for (SysPermission permission : permissions) {
                if(null == permission || StringUtils.isEmpty(permission.getEname()) || StringUtils.isEmpty(permission.getUrl())){
                    continue;
                }
                if(permissions.contains(permission.getEname())){
                    List<String> list = this.permissions.get(permission.getEname());
                    list.add(permission.getUrl());
                }else {
                    List<String> list = new ArrayList<>();
                    list.add(permission.getUrl());
                    this.permissions.put(permission.getEname(), list);
                }
            }
        }
    }

    /**
     * 从缓存中获取白名单
     * @return
     */
    public List<String> getWriteUrls() {
        String s = stringRedisTemplate.opsForValue().get("WRITELIST");
        JSONArray array = new JSONArray(s);
        return array.toList(String.class);
    }
}
