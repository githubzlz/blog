package com.zlz.blog.client;

import com.zlz.blog.common.entity.oauth.LoginUser;
import com.zlz.blog.common.entity.oauth.SysPermission;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020/2/28 16:26
 */
@FeignClient(name = "blog-manage")
public interface BlogManageClient{

    /**
     * 获取用户权限信息
     * @param username
     * @return
     */
    @GetMapping("/authentication/get/authenticationinfo/{username}")
    LoginUser getAuthenticationInfo(@PathVariable String username);

    /**
     * 获取所有的权限资源信息
     * @return
     */
    @GetMapping("/get/permission/all")
    List<SysPermission> getPermissionAll();
}
