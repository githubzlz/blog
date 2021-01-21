package com.zlz.blog.server.user.controller;

import com.zlz.blog.common.entity.user.LoginUser;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.server.user.service.UserManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理
 * created by zlz on 2021/1/5 19:58
 **/
@RestController
@RequestMapping("user")
public class UserManageController {

    @Autowired
    private UserManageService userManageService;

    @RequestMapping("/list")
    public ResultSet loginUserList(@RequestBody LoginUser loginUser){
        return userManageService.loginUserList(loginUser);
    }
}
