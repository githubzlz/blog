package com.zlz.blog.server.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zlz.blog.common.entity.user.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * created by zlz on 2021/1/6 16:11
 **/
@Mapper
public interface RoleMapper extends BaseMapper<SysRole> {

    /**
     * 根据用户id查询角色列表
     * @param id
     * @return
     */
    List<SysRole> getRoleListByUserId(Long id);
}
