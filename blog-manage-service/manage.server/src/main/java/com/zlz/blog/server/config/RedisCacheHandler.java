package com.zlz.blog.server.config;

import cn.hutool.json.JSONArray;
import com.zlz.blog.common.entity.oauth.BlogWriteUrlList;
import com.zlz.blog.common.entity.oauth.SysPermission;
import com.zlz.blog.server.oauth.service.AuthenticationService;
import com.zlz.blog.server.oauth.service.WriteUrlListService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author peeterZ
 * @version 2.0 CreateTime:2021-01-27 10:19
 * @description 启动完成之后初始化redis缓存
 */
@Component
public class RedisCacheHandler implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private AuthenticationService authenticationService;

    @Resource
    private WriteUrlListService writeUrlListService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.setRedisCache();
    }

    public void setRedisCache(){
        // 把所有的权限信息加载到redis
        List<SysPermission> permissionAll = authenticationService.getPermissionAll();
        JSONArray array = new JSONArray(permissionAll);
        stringRedisTemplate.opsForValue().set("PERMISSIONS", array.toString());

        // 路由白名单加载到redis
        List<BlogWriteUrlList> writeUrlList = writeUrlListService.getWriteUrlList();
        List<String> collect = writeUrlList.stream().map(BlogWriteUrlList::getUrl).collect(Collectors.toList());
        array = new JSONArray(collect);
        stringRedisTemplate.opsForValue().set("WRITELIST", array.toString());
    }
}
