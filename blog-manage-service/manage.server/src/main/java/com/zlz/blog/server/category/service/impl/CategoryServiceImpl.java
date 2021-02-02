package com.zlz.blog.server.category.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zlz.blog.common.entity.category.Category;
import com.zlz.blog.common.entity.oauth.LoginUser;
import com.zlz.blog.common.exception.BlogException;
import com.zlz.blog.common.response.PageInfo;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.common.util.PageUtil;
import com.zlz.blog.common.util.SqlResultUtil;
import com.zlz.blog.common.util.TokenUtil;
import com.zlz.blog.server.category.mapper.CategoryMapper;
import com.zlz.blog.server.category.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

/**
 * 分类管理
 *
 * @author peeterZ
 * @version 2.0 CreateTime:2021-02-01 11:52
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public ResultSet<PageInfo<Category>> getPageList(Category category) {

        Optional.ofNullable(category).orElseThrow(() -> new BlogException("缺少查询参数"));
        Optional.ofNullable(category.getPageInfo()).orElseThrow(() -> new BlogException("缺少查询参数"));
        LoginUser loginUser = Optional.ofNullable(TokenUtil.getLoginUser()).orElseThrow(() -> new BlogException("无法获取登录用户"));

        category.setCreator(loginUser.getId());
        category.setLevel(Optional.ofNullable(category.getLevel()).orElse(0));
        category.setParentId(Optional.ofNullable(category.getParentId()).orElse(0L));
        PageInfo<Category> pageInfo = category.getPageInfo();
        IPage<Category> categoryIpage = categoryMapper.selectPage(PageUtil.getIPage(pageInfo), category);

        return ResultSet.success("查询模块列表成功", PageUtil.setPageInfo(categoryIpage, pageInfo));
    }

    @Override
    public ResultSet<Category> createCategory(Category category) {

        Optional.ofNullable(category).orElseThrow(() -> new BlogException("缺少必要参数"));
        LoginUser loginUser = Optional.ofNullable(TokenUtil.getLoginUser()).orElseThrow(() -> new BlogException("无法获取登录用户"));

        //补全创建信息
        Date now = new Date();
        category.setCreator(loginUser.getId());
        category.setCreatedTime(now);
        category.setLastModifier(loginUser.getId());
        category.setLastModifiedTime(now);

        //插入数据
        int insert = categoryMapper.insert(category);
        return SqlResultUtil.isOneRow(insert);
    }

    @Override
    public ResultSet<Category> updateCategory(Category category) {
        Optional.ofNullable(category).orElseThrow(() -> new BlogException("缺少必要参数"));
        Optional.ofNullable(category.getId()).orElseThrow(() -> new BlogException("分类id不能为空"));
        LoginUser loginUser = Optional.ofNullable(TokenUtil.getLoginUser()).orElseThrow(() -> new BlogException("为获取到登陆用户信息"));

        // 补全信息
        category.setLastModifiedTime(new Date());
        category.setLastModifier(loginUser.getId());

        // 修改信息
        int i = categoryMapper.updateById(category);
        return SqlResultUtil.isOneRow(i);
    }
}
