package com.zlz.blog.server.module.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zlz.blog.common.entity.module.ModuleMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * 模块菜单操作
 * created by zlz on 2020/12/22 19:10
 **/
@Mapper
public interface ModuleMenuMapper extends BaseMapper<ModuleMenu> {
}
