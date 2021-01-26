package com.zlz.blog.server.user.mapper;

import com.zlz.blog.common.entity.user.LoginUser;
import com.zlz.blog.common.entity.user.SysPermission;
import com.zlz.blog.common.entity.user.SysRole;
import com.zlz.blog.common.token.UserVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * created by zlz on 2020/12/28 11:29
 *
 * @author 12101*/
@Mapper
public interface AuthenticationMapper {

    /**
     * 获取用户权限信息
     * @param userId
     * @return
     */
    List<SysRole> getAuthenticationInfo(Long userId);

    /**
     * 获取所有的权限信息
     * @return
     */
    List<SysPermission> getAllPermission();
}
