package com.zlz.blog.server.user.service;

import com.zlz.blog.common.entity.user.SysRole;
import com.zlz.blog.common.response.ResultSet;

/**
 * created by zlz on 2021/1/6 16:11
 **/
public interface RoleService {

    /**
     * 查询用户的角色列表
     * @param userId
     * @return
     */
    ResultSet<SysRole> getRoleList(Long userId);
}
