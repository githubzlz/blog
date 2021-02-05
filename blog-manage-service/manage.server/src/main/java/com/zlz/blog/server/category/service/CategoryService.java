package com.zlz.blog.server.category.service;

import com.zlz.blog.common.entity.blog.Blog;
import com.zlz.blog.common.entity.category.Category;
import com.zlz.blog.common.response.PageInfo;
import com.zlz.blog.common.response.ResultSet;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 分类管理
 *
 * @author peeterZ
 * @version 2.0 CreateTime:2021-02-01 11:52
 */
public interface CategoryService {

    /**
     * 分页查询，模糊查询
     * @param category
     * @return
     */
    ResultSet<PageInfo<Category>> getPageList(Category category);

    /**
     * 查询文章分类列表
     * @param category
     * @return
     */
    ResultSet<List<Category>> getList(Category category);

    /**
     * 添加新分类
     * @param category
     * @return
     */
    ResultSet<Category> createCategory(Category category);

    /**
     * 修改分类状态，名称
     * @param category
     * @return
     */
    ResultSet<Category> updateCategory(Category category);

    /**
     * 通过分类id查询其关联的文章信息
     * @param categoryId
     * @return
     */
    ResultSet<List<Blog>> queryCategoryBlog(Long categoryId);

    /**
     * 查询未被分类关联的文章
     * @return
     */
    ResultSet<List<Blog>> queryBlogFreedom();

    /**
     * 修改种类绑定的文章
     * @param category
     * @return
     */
    ResultSet<Category> updateCategoryBlog(Category category);
}
