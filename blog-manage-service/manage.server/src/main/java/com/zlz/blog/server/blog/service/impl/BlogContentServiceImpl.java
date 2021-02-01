package com.zlz.blog.server.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zlz.blog.common.entity.blog.Blog;
import com.zlz.blog.common.entity.blog.BlogContent;
import com.zlz.blog.common.entity.oauth.LoginUser;
import com.zlz.blog.common.exception.BlogException;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.common.util.SqlResultUtil;
import com.zlz.blog.common.util.TokenUtil;
import com.zlz.blog.server.blog.mapper.BlogContentMapper;
import com.zlz.blog.server.blog.service.BlogContentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

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
        Optional.ofNullable(content).orElseThrow(() -> new BlogException("缺少必要数据"));
        Optional.ofNullable(content.getBlogId()).orElseThrow(() -> new BlogException("缺少必要数据"));
        LoginUser loginUser = Optional.ofNullable(TokenUtil.getLoginUser(request))
                .orElseThrow(() -> new BlogException("未获取到登陆人信息"));

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
        Optional.ofNullable(blogContent.getBlogId()).orElseThrow(() -> new BlogException("缺少必要数据"));
        LoginUser loginUser = Optional.ofNullable(TokenUtil.getLoginUser(request))
                .orElseThrow(() -> new BlogException("未获取到登陆人信息"));

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
