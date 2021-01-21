package com.zlz.blog.server.blog.controller;

import com.zlz.blog.common.entity.blog.Blog;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.server.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 文章操作的控制器层
 *
 * @author zhulinzhong
 * @version 1.0 CreateTime:2019/12/20 17:37
 */
@RequestMapping("/blog")
@RestController
public class BlogController {

    private BlogService blogService;

    @Autowired
    public void setBlogService(BlogService blogService){
        this.blogService = blogService;
    }

    /**
     * 新增文章
     * @param blog blog
     * @param request request
     * @return ResultSet
     */
    @PostMapping("/create")
    public ResultSet<Blog> insertArticle(@RequestBody Blog blog, HttpServletRequest request) {
        return blogService.insertBlog(blog, request);
    }

    /**
     * 修改文章
     * @param blog blog
     * @param request request
     * @return ResultSet
     */
    @PostMapping("/update")
    public ResultSet<Blog> updateArticle(@RequestBody Blog blog, HttpServletRequest request) {
        return blogService.updateBlog(blog, request);
    }

    /**
     * 修改文章（仅标题摘要）
     * @param blog blog
     * @param request request
     * @return ResultSet
     */
    @PostMapping("/titleorsummary")
    public ResultSet<Blog> updateArticleTitle(@RequestBody Blog blog, HttpServletRequest request) {
        return blogService.updateBlogTitle(blog, request);
    }

    /**
     * 分页查询,模糊查询
     * @param blog blog
     * @param request request
     * @return ResultSet
     */
    @PostMapping("/list")
    public ResultSet<Blog> selectAll(@RequestBody Blog blog, HttpServletRequest request) {
        return blogService.selectList(blog, request);
    }

    /**
     * 查询一篇文章
     *
     * @param id      id
     * @param request request
     * @return ResultSet
     */
    @GetMapping("/queryarticle/{id}")
    public ResultSet<Blog> selectArticle(@PathVariable("id") Long id, HttpServletRequest request) {
        return blogService.queryBlogById(id, request);
    }

    /**
     * 逻辑删除文章
     *
     * @param id      id
     * @param request request
     * @return ResultSet<Blog>
     */
    @DeleteMapping("/remove/{id}")
    public ResultSet<Blog> deleteArticle(@PathVariable("id") Long id, HttpServletRequest request) {
        return blogService.deleteBlog(id, request);
    }

    /**
     * 恢复已经删除的文章
     *
     * @param id      id
     * @param request request
     * @return ResultSet<Blog>
     */
    @GetMapping("/remove/revoke/{id}")
    public ResultSet<Blog> revokeDeletedArticle(@PathVariable("id") Long id, HttpServletRequest request) {
        return blogService.revokeDeletedBlog(id, request);
    }

//    /**
//     * 查询所有未推荐的文章
//     * @param param
//     * @param request
//     * @return
//     */
//    @GetMapping("/search/all")
//    public ResultSet<Blog> searchAll(@RequestParam(required = false) String param, HttpServletRequest request) {
//        return blogService.searchAll(param, request);
//    }
}
