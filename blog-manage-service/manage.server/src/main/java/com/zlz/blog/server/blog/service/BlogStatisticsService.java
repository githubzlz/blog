package com.zlz.blog.server.blog.service;

import com.zlz.blog.common.entity.blog.BlogStatistics;
import com.zlz.blog.common.response.ResultSet;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-12-12 11:50
 * @description 博客统计信息
 */
public interface BlogStatisticsService {

    /**
     * 插入文章的统计信息
     * @param statistics
     * @param request
     * @return
     */
    ResultSet<BlogStatistics> insertPublicInfo(BlogStatistics statistics, HttpServletRequest request);
}
