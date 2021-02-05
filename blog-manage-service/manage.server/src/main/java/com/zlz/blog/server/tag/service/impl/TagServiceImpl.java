package com.zlz.blog.server.tag.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.zlz.blog.common.entity.oauth.LoginUser;
import com.zlz.blog.common.entity.tag.Tag;
import com.zlz.blog.common.exception.BlogException;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.common.token.Token;
import com.zlz.blog.common.util.TokenUtil;
import com.zlz.blog.server.tag.mapper.TagMapper;
import com.zlz.blog.server.tag.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author peeterZ
 * @version 2.0 CreateTime:2021-02-04 16:55
 */
@Service
public class TagServiceImpl implements TagService {

    private TagMapper tagMapper;

    @Autowired
    public void setTagMapper(TagMapper tagMapper){
        this.tagMapper = tagMapper;
    }

    @Override
    public ResultSet<List<Tag>> queryTagList(Tag tag) {
        Optional.ofNullable(tag).orElseThrow(() -> new BlogException("缺少必要数据"));
        tag.setState(Optional.ofNullable(tag.getState()).orElse(1));
        LoginUser loginUser = Optional.ofNullable(TokenUtil.getLoginUser()).orElseThrow(() -> new BlogException("未获取到登录用户"));

        // 查询条件
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("creator", loginUser.getId());
        queryWrapper.eq("state", tag.getState());
        if(null != tag.getName()){
            queryWrapper.like("state", tag.getState());
        }
        List<Tag> tags = tagMapper.selectList(queryWrapper);
        return ResultSet.success("查询标签列表成功", tags);
    }

    @Override
    public ResultSet<List<Tag>> createTag(List<Tag> tags) {
        // 数据检查
        Optional.ofNullable(tags).orElseThrow(() -> new BlogException("缺少必要数据"));
        Optional.ofNullable(tags.get(0)).orElseThrow(() -> new BlogException("缺少必要数据"));
        LoginUser loginUser = Optional.ofNullable(TokenUtil.getLoginUser()).orElseThrow(() -> new BlogException("未获取到登录用户"));

        // 数据初始化
        tags.forEach(tag -> {
            Optional.ofNullable(tag.getName()).orElseThrow(() -> new BlogException("标签名不能为空"));
            tag.setId(IdWorker.getId());
            tag.setCreatedTime(new Date());
            tag.setLastModifiedTime(new Date());
            tag.setCreator(loginUser.getId());
            tag.setLastModifier(loginUser.getId());
        });

        int insert = tagMapper.insertList(tags);

        return ResultSet.success("新增文章标签成功", tags);
    }

    @Override
    public ResultSet<List<Tag>> addRelationWithBlog(List<Tag> tags, Long blogId) {
        tags.forEach(tag -> {
            tag.setTagBlogId(IdWorker.getId());
        });
        tagMapper.insertRelationWithBlog(tags, blogId);
        return ResultSet.success("添加关联成功");
    }

    @Override
    public ResultSet<Tag> deleteRelationWithBlog(Long blogId) {
        int deleted = tagMapper.deleteRelationWithBlog(blogId);
        return ResultSet.success("移除关联成功");
    }
}
