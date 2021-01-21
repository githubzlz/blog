package com.zlz.blog.server.blog.service;

import com.zlz.blog.common.entity.blog.BlogTag;
import com.zlz.blog.common.response.ResultSet;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-12-12 11:51
 * @description 博客与标签关联表
 */
public interface BlogTagService {

    /**
     * 插入文章标签
     *
     * @param blogId
     * @param blogTags
     * @param request
     * @return
     */
    ResultSet<BlogTag> insertTagList(Long blogId, List<BlogTag> blogTags, HttpServletRequest request);
}
