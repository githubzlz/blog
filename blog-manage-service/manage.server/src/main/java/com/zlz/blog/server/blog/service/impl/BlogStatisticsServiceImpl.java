package com.zlz.blog.server.blog.service.impl;

import com.zlz.blog.common.entity.blog.BlogStatistics;
import com.zlz.blog.common.entity.user.LoginUser;
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
        if (null == statistics || null == statistics.getBlogId()) {
            return ResultSet.inputError();
        }
        LoginUser loginUser = TokenUtil.getLoginUser(request);
        if (StringUtils.isEmpty(loginUser.getUsername())) {
            return ResultSet.error("未找到登录用户");
        }

        //补全数据
        statistics.setLastModifiedTime(new Date());
        statistics.setCreatedTime(new Date());
        statistics.setLastModifier(loginUser.getId());
        statistics.setCreator(loginUser.getId());

        return SqlResultUtil.isOneRow(statisticsMapper.insert(statistics));
    }
}
