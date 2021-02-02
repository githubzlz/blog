package com.zlz.blog.server.category.controller;

import com.zlz.blog.common.entity.category.Category;
import com.zlz.blog.common.response.PageInfo;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.server.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 分类管理
 *
 * @author peeterZ
 * @version 2.0 CreateTime:2021-02-01 11:51
 */
@RequestMapping("/category")
@RestController
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    /**
     * 分页查询文章分类
     * @param category
     * @return
     */
    @PostMapping("/query/pagelist/me")
    public ResultSet<PageInfo<Category>> getPageList(@RequestBody Category category){
        return categoryService.getPageList(category);
    }

    /**
     * 修改分类数据
     * @param category
     * @return
     */
    @PostMapping("/create/me")
    public ResultSet<Category> createCategory(@RequestBody Category category){
        return categoryService.createCategory(category);
    }

    /**
     * 修改分类数据
     * @param category
     * @return
     */
    @PostMapping("/update/me")
    public ResultSet<Category> updateCategory(@RequestBody Category category){
        return categoryService.updateCategory(category);
    }
}
