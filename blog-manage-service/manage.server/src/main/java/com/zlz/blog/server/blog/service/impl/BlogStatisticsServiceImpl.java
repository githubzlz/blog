package com.zlz.blog.server.blog.service.impl;

import com.zlz.blog.common.entity.blog.BlogStatistics;
import com.zlz.blog.common.entity.oauth.LoginUser;
import com.zlz.blog.common.exception.BlogException;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.common.util.SqlResultUtil;
import com.zlz.blog.common.util.TokenUtil;
import com.zlz.blog.server.blog.mapper.BlogStatisticsMapper;
import com.zlz.blog.server.blog.service.BlogStatisticsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

/**
 * @author peeterZ
 * @version 2.0 CreateTime:2020-12-12 14:34
 * @description
 */
@Service
public class BlogStatisticsServiceImpl implements BlogStatisticsService {

    @Resource
    private BlogStatisticsMapper statisticsMapper;

    @Override
    public ResultSet<BlogStatistics> insertPublicInfo(BlogStatistics statistics, HttpServletRequest request) {
        //数据检查
        Optional.ofNullable(statistics).orElseThrow(() -> new BlogException("缺少关键数据"));
        Optional.ofNullable(statistics.getBlogId()).orElseThrow(() -> new BlogException("缺少关键数据"));
        LoginUser loginUser = Optional.ofNullable(TokenUtil.getLoginUser(request))
                .orElseThrow(() -> new BlogException("未获取到登录用户信息"));

        //补全数据
        statistics.setLastModifiedTime(new Date());
        statistics.setCreatedTime(new Date());
        statistics.setLastModifier(loginUser.getId());
        statistics.setCreator(loginUser.getId());

        return SqlResultUtil.isOneRow(statisticsMapper.insert(statistics));
    }
}
