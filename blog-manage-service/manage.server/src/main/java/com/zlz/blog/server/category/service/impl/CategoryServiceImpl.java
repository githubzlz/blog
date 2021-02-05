package com.zlz.blog.server.category.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.sun.org.glassfish.external.amx.AMX;
import com.zlz.blog.common.entity.blog.Blog;
import com.zlz.blog.common.entity.category.Category;
import com.zlz.blog.common.entity.oauth.LoginUser;
import com.zlz.blog.common.enums.common.IsDeletedEnum;
import com.zlz.blog.common.exception.BlogException;
import com.zlz.blog.common.response.PageInfo;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.common.util.PageUtil;
import com.zlz.blog.common.util.SqlResultUtil;
import com.zlz.blog.common.util.TokenUtil;
import com.zlz.blog.server.category.mapper.CategoryMapper;
import com.zlz.blog.server.category.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 分类管理
 *
 * @author peeterZ
 * @version 2.0 CreateTime:2021-02-01 11:52
 */
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public ResultSet<PageInfo<Category>> getPageList(Category category) {

        Optional.ofNullable(category).orElseThrow(() -> new BlogException("缺少查询参数"));
        Optional.ofNullable(category.getPageInfo()).orElseThrow(() -> new BlogException("缺少查询参数"));
        LoginUser loginUser = Optional.ofNullable(TokenUtil.getLoginUser()).orElseThrow(() -> new BlogException("无法获取登录用户"));

        category.setCreator(loginUser.getId());
        category.setLevel(Optional.ofNullable(category.getLevel()).orElse(1));
        category.setParentId(Optional.ofNullable(category.getParentId()).orElse(0L));
        PageInfo<Category> pageInfo = category.getPageInfo();
        IPage<Category> categoryIpage = categoryMapper.selectPage(PageUtil.getIPage(pageInfo), category);

        return ResultSet.success("查询模块列表成功", PageUtil.setPageInfo(categoryIpage, pageInfo));
    }


    @Override
    public ResultSet<List<Category>> getList(Category category) {
        Optional.ofNullable(category).orElseThrow(() -> new BlogException("缺少查询参数"));
        LoginUser loginUser = Optional.ofNullable(TokenUtil.getLoginUser()).orElseThrow(() -> new BlogException("未获取到登录用户"));

        // 生成过滤条件
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_deleted", IsDeletedEnum.IS_NOT_DELETED_ENUM.getCode());
        queryWrapper.eq("creator", loginUser.getId());

        if(null != category.getParentId()){
            queryWrapper.eq("parent_id", category.getParentId());
        }
        if(null != category.getTitle()){
            queryWrapper.like("title", category.getTitle());
        }

        List<Category> categories = categoryMapper.selectList(queryWrapper);
        return ResultSet.success("查询文章列表成功", categories);

    }

    @Override
    public ResultSet<Category> createCategory(Category category) {

        Optional.ofNullable(category).orElseThrow(() -> new BlogException("缺少必要参数"));
        // parentId默认为0
        category.setParentId(Optional.ofNullable(category.getParentId()).orElse(0L));
        LoginUser loginUser = Optional.ofNullable(TokenUtil.getLoginUser()).orElseThrow(() -> new BlogException("无法获取登录用户"));

        //补全创建信息
        Date now = new Date();
        category.setCreator(loginUser.getId());
        category.setCreatedTime(now);
        category.setLastModifier(loginUser.getId());
        category.setLastModifiedTime(now);
        category.setLevelCode(getLevelCode(category.getParentId()));

        //插入数据
        int insert = categoryMapper.insert(category);
        return SqlResultUtil.isOneRow(insert, "模块列表创建成功", "模块列表创建失败");
    }

    @Override
    public ResultSet<Category> updateCategory(Category category) {
        Optional.ofNullable(category).orElseThrow(() -> new BlogException("缺少必要参数"));
        Optional.ofNullable(category.getId()).orElseThrow(() -> new BlogException("分类id不能为空"));
        LoginUser loginUser = Optional.ofNullable(TokenUtil.getLoginUser()).orElseThrow(() -> new BlogException("为获取到登陆用户信息"));

        // 补全信息
        category.setLastModifiedTime(new Date());
        category.setLastModifier(loginUser.getId());

        // 删除
        if(IsDeletedEnum.DELETED_ENUM.getCode().equals(category.getIsDeleted())){
            // 删除子级分类，删除关联关系
            categoryMapper.deleteCategoryBlogRelation(category.getId());
            categoryMapper.deleteCategoryAndChildren(category.getId());
            return ResultSet.success("删除成功");
        }else {
            // 修改信息
            int i = categoryMapper.updateById(category);
            return SqlResultUtil.isOneRow(i);
        }


    }

    @Override
    public ResultSet<List<Blog>> queryCategoryBlog(Long categoryId) {
        List<Blog> blogs = categoryMapper.selectBlogByCategoryId(categoryId);
        return ResultSet.success("查询分类文章列表成功", blogs);
    }

    @Override
    public ResultSet<List<Blog>> queryBlogFreedom() {
        LoginUser loginUser = Optional.ofNullable(TokenUtil.getLoginUser()).orElseThrow(() -> new BlogException("未获取到登录用户"));
        List<Blog> blogs = categoryMapper.selectBlogFreedom(loginUser.getId());
        return ResultSet.success("查询未分类文章列表成功", blogs);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultSet<Category> updateCategoryBlog(Category category) {

        // 参数检查
        Optional.ofNullable(category).orElseThrow(() -> new BlogException("缺少必要参数"));
        Optional.ofNullable(category.getId()).orElseThrow(() -> new BlogException("种类id不能为空"));

        // 清空关联关系
        int delete = categoryMapper.deleteCategoryBlog(category.getId());
        log.info("移除绑定关系：" + delete + " 条");
        // 添加关联关系
        if(null != category.getBlogs() && !category.getBlogs().isEmpty()){
            category.getBlogs().forEach(blog -> {
                blog.setCategorBlogId(IdWorker.getId());
            });
            int insert = categoryMapper.insertCategoryBlog(category.getId(), category.getBlogs());
            log.info("添加绑定关系：" + insert + " 条");
        }
        return ResultSet.success("绑定文章修改成功");
    }

    /**
     * 获取分级码
     * @param parentId
     * @return
     */
    private String getLevelCode(Long parentId){
        // 获取父级分级码，根据父级分级码生成本级的分级码
        // 获取子集最大的分级码并且加一
        String maxBrolevelCode = categoryMapper.getMaxBrolevelCode(parentId);
        if(StringUtils.isEmpty(maxBrolevelCode)){
            return "0001";
        }
        int s1 = maxBrolevelCode.length();
        BigDecimal levelCode = new BigDecimal(maxBrolevelCode);
        String levelCodeStr = levelCode.add(new BigDecimal("1")).toString();
        // 补齐0
        int s2 = levelCodeStr.length();
        for (int i = 0; i < s1-s2; i++) {
            levelCodeStr = "0".concat(levelCodeStr);
        }
        return levelCodeStr;
    }
}
