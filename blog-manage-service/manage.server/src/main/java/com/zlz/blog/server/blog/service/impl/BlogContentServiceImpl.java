package com.zlz.blog.server.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zlz.blog.common.entity.blog.Blog;
import com.zlz.blog.common.entity.blog.BlogContent;
import com.zlz.blog.common.entity.user.LoginUser;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.common.util.SqlResultUtil;
import com.zlz.blog.common.util.TokenUtil;
import com.zlz.blog.server.blog.mapper.BlogContentMapper;
import com.zlz.blog.server.blog.service.BlogContentService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author peeterZ
 * @version 2.0 CreateTime:2020-12-12 14:29
 * @description 博客文章内容
 */
@Service
public class BlogContentServiceImpl implements BlogContentService {

    @Resource
    private BlogContentMapper contentMapper;

    @Override
    public ResultSet<BlogContent> insertBody(BlogContent content, HttpServletRequest request) {
        //数据检查
        if (null == content || null == content.getBlogId()) {
            return ResultSet.inputError();
        }
        LoginUser loginUser = TokenUtil.getLoginUser(request);
        if (StringUtils.isEmpty(loginUser.getUsername())) {
            return ResultSet.error("未找到登录用户");
        }

        //补全数据
        content.setCreatedTime(new Date());
        content.setLastModifiedTime(new Date());
        content.setCreator(loginUser.getId());
        content.setLastModifier(loginUser.getId());

        //持久层数据处理并返回结果
        return SqlResultUtil.isOneRow(contentMapper.insert(content));

    }

    @Override
    public ResultSet<Blog> updateBody(BlogContent blogContent, HttpServletRequest request) {
        //数据检查
        if (null == blogContent.getBlogId()) {
            return ResultSet.inputError();
        }
        //获取登陆人
        LoginUser loginUser = TokenUtil.getLoginUser(request);
        if (StringUtils.isEmpty(loginUser.getUsername())) {
            return ResultSet.error();
        }

        // >> 10 = * 2的10次方
        if (null != blogContent.getContentHtml()) {
            double htSize = blogContent.getContentHtml().getBytes().length >> 10;
            blogContent.setHtmlSize(htSize + "KB");
        }
        if (null != blogContent.getContentMd()) {
            double mdSize = blogContent.getContentMd().getBytes().length >> 10;
            blogContent.setMdSize(mdSize + "KB");
        }

        //补全数据
        blogContent.setLastModifiedTime(new Date());
        blogContent.setLastModifier(loginUser.getId());

        //设置查询条件
        QueryWrapper<BlogContent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("blog_id", blogContent.getBlogId())
                .eq("creator", loginUser.getId());
        return SqlResultUtil.isOneRow(contentMapper.update(blogContent, queryWrapper));
    }
}
