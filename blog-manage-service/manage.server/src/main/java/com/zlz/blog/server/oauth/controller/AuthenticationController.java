package com.zlz.blog.server.oauth.controller;

import com.zlz.blog.common.entity.oauth.LoginUser;
import com.zlz.blog.common.entity.oauth.SysPermission;
import com.zlz.blog.server.config.RedisCacheHandler;
import com.zlz.blog.server.oauth.service.AuthenticationService;
import com.zlz.blog.server.oauth.service.LoginUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author peeterZ
 * @version 2.0 CreateTime:2021-01-21 11:58
 * @description
 */
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    private LoginUserService userService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private RedisCacheHandler handler;

    @GetMapping("/get/authenticationinfo/{username}")
    public LoginUser getAuthenticationInfo(@PathVariable String username){
        return userService.findByUsername(username);
    }

    /**
     * 获取所有的权限资源
     * @return
     */
    @GetMapping("/get/permission/all")
    public List<SysPermission> getPermissionAll(){
        return authenticationService.getPermissionAll();
    }

    /**
     * 刷新redis的缓存
     */
    @GetMapping("/redis/refresh")
    public void refreshCache(){
        handler.setRedisCache();
    }
}
