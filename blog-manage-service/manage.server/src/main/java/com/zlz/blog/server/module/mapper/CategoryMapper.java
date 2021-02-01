package com.zlz.blog.server.module.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zlz.blog.common.entity.category.Category;

/**
 * 分类管理
 *
 * @author peeterZ
 * @version 2.0 CreateTime:2021-02-01 11:52
 */
public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * 分页查询分类
     * @param iPage
     * @param category
     * @return
     */
    IPage<Category> selectPage(IPage<Category> iPage, Category category);
}
