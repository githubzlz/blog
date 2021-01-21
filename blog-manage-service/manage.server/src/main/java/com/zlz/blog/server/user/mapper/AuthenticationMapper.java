package com.zlz.blog.server.user.mapper;

import com.zlz.blog.common.entity.user.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * created by zlz on 2020/12/28 11:29
 **/
@Mapper
public interface AuthenticationMapper {

    /**
     * 获取用户权限信息
     * @param userId
     * @return
     */
    List<SysRole> getAuthenticationInfo(Long userId);

}
