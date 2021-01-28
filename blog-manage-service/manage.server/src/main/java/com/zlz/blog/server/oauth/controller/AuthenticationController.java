package com.zlz.blog.server.oauth.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zlz.blog.common.entity.oauth.LoginUser;
import com.zlz.blog.common.entity.oauth.SysPermission;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.server.config.RedisCacheHandler;
import com.zlz.blog.server.oauth.service.AuthenticationService;
import com.zlz.blog.server.oauth.service.LoginUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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

    /**
     * 通过用户名获取用户信息
     * @param username
     * @return
     */
    @GetMapping("/get/authenticationinfo/{username}")
    public LoginUser getAuthenticationInfoByUsername(@PathVariable String username){
        return userService.findByUsername(username);
    }

    /**
     * 通过token获取用户信息
     * @return
     */
    @GetMapping("/get/authenticationinfo")
    public ResultSet<LoginUser> getAuthenticationInfoByToken(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        assert authorization != null;
        authorization = authorization.substring(7);
        DecodedJWT decoded = JWT.decode(authorization);
        String name = decoded.getClaims().get("user_name").asString();
        LoginUser byUsername = userService.findByUsername(name);
        if(null != byUsername){
            byUsername.setPassword(null);
            byUsername.setSalt(null);
            return ResultSet.success("获取登录用户信息成功", byUsername);
        }
        return ResultSet.error("获取登录用户信息失败");
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
