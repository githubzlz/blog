package com.zlz.blog.server.oauth.service;

import com.zlz.blog.common.entity.oauth.SysPermission;

import java.util.List;

/**
 * @author peeterZ
 * @version 2.0 CreateTime:2021-01-22 11:39
 * @description 权限信息
 */
public interface AuthenticationService {

    /**
     * 获取所有的权限资源信息
     * @return
     */
    List<SysPermission> getPermissionAll();
}
