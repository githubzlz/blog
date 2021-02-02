package com.zlz.blog.server.oauth.service.impl;

import com.zlz.blog.common.entity.oauth.SysRole;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.server.oauth.mapper.RoleMapper;
import com.zlz.blog.server.oauth.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * created by zlz on 2021/1/6 16:12
 **/
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public ResultSet<List<SysRole>> getRoleList(Long userId) {

        Optional<Long> id = Optional.of(userId);

        List<SysRole> roleListByUserId = roleMapper.getRoleListByUserId(id.get());

        return ResultSet.success("查询角色列表成功", roleListByUserId);
    }
}
