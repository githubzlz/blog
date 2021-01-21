package com.zlz.blog.server.blog.service;

import com.zlz.blog.common.entity.blog.Blog;
import com.zlz.blog.common.response.ResultSet;

import javax.servlet.http.HttpServletRequest;

/**
 * 文章操作的service接口
 *
 * @author zhulinzhong
 * @version 1.0 CreateTime:2019/12/20 17:34
 */
public interface BlogService {

    /**
     * 新增文章
     *
     * @param blog blog
     * @param request request
     * @return ResultSet<Blog>
     */
    ResultSet<Blog> insertBlog(Blog blog, HttpServletRequest request);

    /**
     * 修改文章
     *
     * @param blog blog
     * @param request request
     * @return ResultSet<Blog>
     */
    ResultSet<Blog> updateBlog(Blog blog, HttpServletRequest request);

    /**
     * 修改文章
     *
     * @param blog blog
     * @param request request
     * @return ResultSet<Blog>
     */
    ResultSet<Blog> updateBlogTitle(Blog blog, HttpServletRequest request);

    /**
     * 查看文章信息
     *
     * @param id      id
     * @param request 请求信息
     * @return ResultSet<Blog>
     */
    ResultSet<Blog> queryBlogById(Long id, HttpServletRequest request);

    /**
     * 查看所有文章信息
     *
     * @param blog 分页信息
     * @param request     请求信息
     * @return ResultSet<Blog>
     */
    ResultSet<Blog> selectList(Blog blog, HttpServletRequest request);

    /**
     * 删除文章(逻辑删除)
     *
     * @param id      id
     * @param request request
     * @return ResultSet<Blog>
     */
    ResultSet<Blog> deleteBlog(Long id, HttpServletRequest request);

    /**
     * 撤回已经删除的文章
     *
     * @param id
     * @param request
     * @return
     */
    ResultSet<Blog> revokeDeletedBlog(Long id, HttpServletRequest request);

    /**
     * 查询所有文章
     * @param param
     * @param request
     * @return
     */
    ResultSet<Blog> searchAll(String param, HttpServletRequest request);
}
