package com.zlz.blog.server.category.controller;

import com.zlz.blog.common.entity.blog.Blog;
import com.zlz.blog.common.entity.category.Category;
import com.zlz.blog.common.response.PageInfo;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.server.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PostMapping("/me/query/pagelist")
    public ResultSet<PageInfo<Category>> getPageList(@RequestBody Category category){
        return categoryService.getPageList(category);
    }

    /**
     * 查询所有文章分类
     * @param category
     * @return
     */
    @PostMapping("/me/query/list")
    public ResultSet<List<Category>> getList(@RequestBody(required = false) Category category){
        return categoryService.getList(category);
    }

    /**
     * 修改分类数据
     * @param category
     * @return
     */
    @PostMapping("/me/create")
    public ResultSet<Category> createCategory(@RequestBody Category category){
        return categoryService.createCategory(category);
    }

    /**
     * 修改分类数据
     * @param category
     * @return
     */
    @PostMapping("/me/update")
    public ResultSet<Category> updateCategory(@RequestBody Category category){
        return categoryService.updateCategory(category);
    }

    /**
     * 通过分类id查询其关联的文章信息
     * @param categoryId
     * @return
     */
    @GetMapping("/all/query/blog/{id}")
    public ResultSet<List<Blog>> queryCategoryBlog(@PathVariable("id") Long categoryId){
        return categoryService.queryCategoryBlog(categoryId);
    }

    /**
     * 查询未被分类关联的文章
     * @return
     */
    @GetMapping("/me/query/freeblog")
    public ResultSet<List<Blog>> queryBlogFreedom(){
        return categoryService.queryBlogFreedom();
    }

    /**
     * 修改种类绑定的文章
     * @param category
     * @return
     */
    @PostMapping("/me/update/categoryblog")
    public ResultSet<Category> updateCategoryBlog(@RequestBody Category category){
        return categoryService.updateCategoryBlog(category);
    }
}
