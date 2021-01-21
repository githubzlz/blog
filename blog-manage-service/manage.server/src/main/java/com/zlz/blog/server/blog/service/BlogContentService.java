package com.zlz.blog.server.blog.service;

import com.zlz.blog.common.entity.blog.Blog;
import com.zlz.blog.common.entity.blog.BlogContent;
import com.zlz.blog.common.response.ResultSet;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-12-12 11:50
 * @description 博客文章内容
 */
public interface BlogContentService {


    /**
     * 插入文章内容
     * @param blogContent
     * @param request
     * @return
     */
    ResultSet<BlogContent> insertBody(BlogContent blogContent, HttpServletRequest request);

    /**
     * 修改文章内容
     * @param blogContent
     * @param request
     * @return
     */
    ResultSet<Blog> updateBody(BlogContent blogContent, HttpServletRequest request);
}
