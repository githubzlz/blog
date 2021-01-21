package com.zlz.blog.server.module.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zlz.blog.common.entity.module.Module;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * created by zlz on 2020/12/21 9:59
 **/
@Mapper
public interface ModuleMapper extends BaseMapper<Module> {

    /**
     * 分页查询
     * @param iPage
     * @param module
     * @return
     */
    IPage<Module> selectPage(IPage<Module> iPage, Module module);
}
