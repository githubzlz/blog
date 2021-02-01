package com.zlz.blog.server.module.service;

import com.zlz.blog.common.entity.category.Category;
import com.zlz.blog.common.response.PageInfo;
import com.zlz.blog.common.response.ResultSet;

import javax.servlet.http.HttpServletRequest;

/**
 * 分类管理
 *
 * @author peeterZ
 * @version 2.0 CreateTime:2021-02-01 11:52
 */
public interface CategoryService {

    /**
     * 分页查询，模糊查询
     * @param module
     * @return
     */
    ResultSet<PageInfo<Category>> getPageList(Category module);

    /**
     * 添加新分类
     * @param request
     * @param module
     * @return
     */
    ResultSet<Category> createCategory(HttpServletRequest request, Category module);
}
