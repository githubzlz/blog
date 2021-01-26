package com.zlz.blog.server.user.controller;

import com.zlz.blog.common.entity.user.LoginUser;
import com.zlz.blog.common.entity.user.SysPermission;
import com.zlz.blog.server.user.service.AuthenticationService;
import com.zlz.blog.server.user.service.LoginUserService;
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

    @GetMapping("/get/authenticationinfo/{username}")
    public LoginUser getAuthenticationInfo(@PathVariable String username){
        return userService.findByUsername(username);
    }

    @GetMapping("/get/permission/all")
    public List<SysPermission> getPermissionAll(){
        return authenticationService.getPermissionAll();
    }
}
