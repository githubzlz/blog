package com.zlz.blog.server.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zlz.blog.common.constants.article.ArticleConstants;
import com.zlz.blog.common.entity.blog.Blog;
import com.zlz.blog.common.entity.blog.BlogContent;
import com.zlz.blog.common.entity.blog.BlogStatistics;
import com.zlz.blog.common.entity.category.Category;
import com.zlz.blog.common.entity.common.ExcludeItem;
import com.zlz.blog.common.entity.oauth.LoginUser;
import com.zlz.blog.common.entity.tag.Tag;
import com.zlz.blog.common.enums.article.ProvenanceEnum;
import com.zlz.blog.common.enums.article.VisibleStrategyEnum;
import com.zlz.blog.common.exception.BlogException;
import com.zlz.blog.common.response.PageInfo;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.common.util.PageUtil;
import com.zlz.blog.common.util.SqlResultUtil;
import com.zlz.blog.common.util.TokenUtil;
import com.zlz.blog.common.vos.blog.BlogVO;
import com.zlz.blog.server.blog.mapper.BlogMapper;
import com.zlz.blog.server.blog.service.*;
import com.zlz.blog.server.category.service.CategoryService;
import com.zlz.blog.server.tag.service.TagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 文章操作service实现类
 *
 * @author zhulinzhong
 * @version 2.0 CreateTime:2019/12/20 17:35
 */
@Service
public class BlogServiceImpl implements BlogService{

//    @Resource
//    private BlogAttachFileService blogAttachFileService;
    @Resource
    private BlogContentService blogContentService;
    @Resource
    private BlogStatisticsService blogStatisticsService;
    @Resource
    private TagService tagService;
    @Resource
    private BlogMapper blogMapper;
    @Resource
    private CategoryService categoryService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultSet<Long> insertBlog(Blog blog, HttpServletRequest request) {

        // 数据检查
        Optional.ofNullable(blog).orElseThrow(() -> new BlogException("缺少必要数据"));
        Optional.ofNullable(blog.getTitle()).orElseThrow(() -> new BlogException("缺少必要数据"));
        LoginUser user = Optional.ofNullable(TokenUtil.getLoginUser(request))
                .orElseThrow(() -> new BlogException("未获取到登录用户信息"));

        //初始化文章信息
        blog.setUserId(user.getId());
        blog.setAuthor(user.getUsername());
        blog.setIsDeleted(ArticleConstants.IS_NOT_DELETED);
        //todo:预先设置为展示，方便测试
        blog.setIsShow(ArticleConstants.IS_SHOW);

        //补全创建人信息
        blog.setLastModifier(user.getId());
        blog.setLastModifiedTime(new Date());
        blog.setCreator(user.getId());
        blog.setCreatedTime(new Date());

        //插入主表数据
        int row = blogMapper.insert(blog);
        if (row != 1 || StringUtils.isEmpty(blog.getId())) {
            throw new BlogException("文章数据创建失败");
        }

        //插入文章正文相关内容
        insertArticleContent(blog, request);

        //插入文章浏览信息相关内容
        insertBlogInfos(blog, request);

        //插入文章标签
        insertBlogTag(blog.getId(), blog.getTags2());

        // 插入文章分类关系
        insertBlogCategory(blog, blog.getCategoryIds());
        return ResultSet.success("文章保存成功", blog.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultSet<Blog> updateBlog(Blog blog, HttpServletRequest request) {

        LoginUser user = Optional.ofNullable(TokenUtil.getLoginUser(request))
                .orElseThrow(() -> new BlogException("未获取到登录用户信息"));

        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", blog.getId())
                .eq("user_id", user.getId());

        Blog blogArticle = new Blog();
        blogArticle.setSummary(blog.getSummary());
        blogArticle.setTitle(blog.getTitle());
        blogArticle.setAuthor(blog.getAuthor());
        //仅可以为枚举指定的类型
        if (null != ProvenanceEnum.getEnumByCode(blog.getProvenance())) {
            blogArticle.setProvenance(blog.getProvenance());
        }
        if (null != VisibleStrategyEnum.getEnumByCode(blog.getVisibleStrategy())) {
            blogArticle.setVisibleStrategy(blog.getVisibleStrategy());
        }

        //修改文章信息
        int update = blogMapper.update(blogArticle, queryWrapper);
        if (update != 1 && update != 0) {
            throw new BlogException("文章数据修改失败");
        }

        if (blog.getBlogContent() == null) {
            return ResultSet.success();
        }

        BlogContent blogContent = blog.getBlogContent();
        blogContent.setBlogId(blog.getId());
        ResultSet<Blog> blogResultSet = blogContentService.updateBody(blogContent, request);
        if(!ResultSet.isSuccess(blogResultSet)){
            throw new BlogException(blogResultSet.getMessage());
        }

        // 修改种类绑定关系
        Category category = new Category();
        category.setId(blog.getCategoryIds().get(0));
        List<Blog> blogs = new ArrayList<>();
        blogs.add(blog);
        category.setBlogs(blogs);
        ResultSet<Category> categoryResultSet = categoryService.updateCategoryBlog(category);
        if(!ResultSet.isSuccess(categoryResultSet)){
            throw new BlogException(categoryResultSet.getMessage());
        }

        // 修改标签绑定关系
        insertBlogTag(blog.getId(), blog.getTags2());

        return ResultSet.success("修改文章信息成功");
    }

    @Override
    public ResultSet<Blog> updateBlogTitle(Blog blog, HttpServletRequest request) {

        LoginUser loginUser = Optional.ofNullable(TokenUtil.getLoginUser(request))
                .orElseThrow(() -> new BlogException("未获取到登录用户信息"));

        //创建修改实体
        Blog updateEntity = new Blog();
        updateEntity.setId(blog.getId());
        updateEntity.setTitle(blog.getTitle());
        updateEntity.setSummary(blog.getSummary());
        updateEntity.setLastModifier(loginUser.getId());
        updateEntity.setLastModifiedTime(new Date());

        //修改信息
        int i = blogMapper.updateById(updateEntity);
        return SqlResultUtil.isOneRow(i);
    }

    @Override
    public ResultSet<Blog> queryBlogById(Long id, HttpServletRequest request) {
        //数据检查
        LoginUser loginUser = Optional.ofNullable(TokenUtil.getLoginUser(request))
                .orElseThrow(() -> new BlogException("未获取到登录用户信息"));

        Blog blogArticle = blogMapper.selectDetailById(id, loginUser.getId());
        if (null == blogArticle) {
            return ResultSet.outError();
        }

        return ResultSet.success(blogArticle);
    }

    @Override
    public ResultSet<PageInfo<Blog>> selectList(BlogVO blog, HttpServletRequest request) {
        //获取当前登录用户
        LoginUser loginUser = Optional.ofNullable(TokenUtil.getLoginUser(request))
                .orElseThrow(() -> new BlogException("未获取到登录用户信息"));

        //设置查询条件
        blog.setUserId(loginUser.getId());

        //获取并设置筛选条件
        PageInfo<Blog> pageInfo = blog.getPageInfo();
        excludeColumn(pageInfo, blog);

        // 设置种类的帅选条件
        if(blog.getCategoryIds().isEmpty()){
            blog.setCategoryIds(null);
        }

        //返回结果转换并返回消息
        IPage<Blog> iPage = blogMapper.selectPage(PageUtil.getIPage(pageInfo), blog);
        return ResultSet.success(PageUtil.setPageInfo(iPage, blog.getPageInfo()));
    }

    @Override
    public ResultSet<Blog> deleteBlog(Long id, HttpServletRequest request) {
        //数据检查
        LoginUser loginUser = Optional.ofNullable(TokenUtil.getLoginUser(request))
                .orElseThrow(() -> new BlogException("未获取到登录用户信息"));

//        //查询文章是否推荐
//        QueryWrapper<BlogRecommend> recommendQueryWrapper = new QueryWrapper<>();
//        recommendQueryWrapper.eq("blog_id", id);
//        Integer integer = blogRecommendMapper.selectCount(recommendQueryWrapper);
//        if(integer != 0){
//            return ResultSet.error("已推荐文章无法删除,请先取消推荐");
//        }

        //设置条件
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id).eq("user_id", loginUser.getId());

        //删除
        int update = blogMapper.deleteById(id);

        return SqlResultUtil.isOneRow(update);
    }

    @Override
    public ResultSet<Blog> revokeDeletedBlog(Long id, HttpServletRequest request) {
        int i = blogMapper.revokeDelete(id);
        return SqlResultUtil.isOneRow(i);
    }

    @Override
    public ResultSet<Blog> searchAll(String param, HttpServletRequest request) {
        return null;
    }

    /**
     * 插入文章正文相关内容
     *
     * @param blog blog
     */
    private void insertArticleContent(Blog blog, HttpServletRequest request) {
        BlogContent blogContent = new BlogContent();
        // 若是传入的文章正文为空，则只是插入文章id，其他数据为空
        if (null == blog.getBlogContent()) {
            blogContent.setBlogId(blog.getId());
        // 若是传入的文章正文不为空，则初始化文章正文信息插入
        } else {
            blogContent = blog.getBlogContent();
            blogContent.setBlogId(blog.getId());
            //将md，html插入至内容表
            blogContent.setBlogId(blog.getId());
            // >> 10 = * 2的10次方
            double size = blogContent.getContentMd().getBytes().length >> 10;
            blogContent.setMdSize(size + "KB");
            size = blogContent.getContentHtml().getBytes().length >> 10;
            blogContent.setHtmlSize(size + "KB");
        }
        //插入
        ResultSet<BlogContent> blogContentResultSet = blogContentService.insertBody(blogContent, request);
        if (!ResultSet.isSuccess(blogContentResultSet)) {
            throw new BlogException("文章内容插入失败");
        }
    }

    /**
     * 插入文章非用户控制相关信息（浏览量等）
     *
     * @param blog blog
     */
    private void insertBlogInfos(Blog blog, HttpServletRequest request) {

        BlogStatistics statistics = new BlogStatistics();
        statistics.setBlogId(blog.getId());
        statistics.setStars(0L);
        statistics.setComments(0L);
        statistics.setGoods(0L);
        statistics.setCollect(0L);
        statistics.setReadings(0L);

        ResultSet<BlogStatistics> resultSet = blogStatisticsService.insertPublicInfo(statistics, request);
        if (!ResultSet.isSuccess(resultSet)) {
            throw new BlogException("文章浏览信息插入失败");
        }
    }

    /**
     * 插入标签
     *
     * @param blogId blogId
     * @param blogTags blogTags
     */
    private void insertBlogTag(Long blogId, List<Tag> blogTags) {

        // 插入文章标签
        List<Tag> insertTags = blogTags.stream().filter(data -> data.getId() == null).collect(Collectors.toList());
        if(!insertTags.isEmpty()){
            ResultSet<List<Tag>> tag = tagService.createTag(insertTags);
            if (!ResultSet.isSuccess(tag)) {
                throw new BlogException("文章标签插入失败");
            }
        }

        // 删除该文章有关的关联关系
        ResultSet<Tag> resultSet = tagService.deleteRelationWithBlog(blogId);

        // 插入文章标签关联关系
        ResultSet<List<Tag>> listResultSet = tagService.addRelationWithBlog(blogTags, blogId);

        if (!ResultSet.isSuccess(listResultSet)) {
            throw new BlogException("文章标签插入失败");
        }
    }

    /**
     * 插入文章分类关系
     * @param blog
     * @param categoryIds
     */
    private void insertBlogCategory(Blog blog, List<Long> categoryIds) {

        // 数据验证
        if(null == categoryIds || categoryIds.isEmpty()){
            return;
        }

        // 构造数据
        Category category = new Category();
        category.setId(categoryIds.get(0));
        ArrayList<Blog> blogs = new ArrayList<>();
        blogs.add(blog);
        category.setBlogs(blogs);

        // 插入文章分类关系
        ResultSet<Category> categoryResultSet = categoryService.updateCategoryBlog(category);
        if (!ResultSet.isSuccess(categoryResultSet)) {
            throw new BlogException("文章分类关联失败");
        }
    }

    /**
     * 设置查询筛选条件
     *
     * @param pageInfo    pageInfo
     * @param blog blog
     */
    private void excludeColumn(PageInfo<Blog> pageInfo, Blog blog) {
        final String isDeleted = "isDeleted";
        final String isShow = "isShow";
        if (pageInfo.getExclude() != null && !pageInfo.getExclude().isEmpty()) {
            List<ExcludeItem> exclude = pageInfo.getExclude();
            List<ExcludeItem> needExclude = new ArrayList<>();
            PageInfo<Blog> newPageInfo = new PageInfo<>();
            exclude.forEach(item -> {
                if (isDeleted.equals(item.getColumn()) && !StringUtils.isEmpty(item.getValue())) {
                    ExcludeItem excludeItem = new ExcludeItem();
                    excludeItem.setColumn("is_deleted");
                    excludeItem.setValue(item.getValue());
                    needExclude.add(excludeItem);
                } else if (isShow.equals(item.getColumn()) && !StringUtils.isEmpty(item.getValue())) {
                    ExcludeItem excludeItem = new ExcludeItem();
                    excludeItem.setColumn("is_show");
                    excludeItem.setValue(item.getValue());
                    needExclude.add(excludeItem);
                }
            });
            newPageInfo.setExclude(needExclude);
            blog.setPageInfo(newPageInfo);
        }
    }
}
