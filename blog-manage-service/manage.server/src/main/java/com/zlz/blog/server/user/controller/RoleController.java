package com.zlz.blog.server.user.controller;

import com.zlz.blog.common.entity.user.SysRole;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.server.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by zlz on 2021/1/6 16:09
 **/
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 查询用户的角色列表
     * @param userId
     * @return
     */
    @GetMapping("/list/{userId}")
    public ResultSet<SysRole> getRoleList(@PathVariable("userId")Long userId){
        return roleService.getRoleList(userId);
    }
}
