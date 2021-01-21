package com.zlz.blog.server.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zlz.blog.common.constants.article.ArticleConstants;
import com.zlz.blog.common.entity.blog.Blog;
import com.zlz.blog.common.entity.blog.BlogContent;
import com.zlz.blog.common.entity.blog.BlogStatistics;
import com.zlz.blog.common.entity.blog.BlogTag;
import com.zlz.blog.common.entity.common.ExcludeItem;
import com.zlz.blog.common.entity.user.LoginUser;
import com.zlz.blog.common.enums.article.ProvenanceEnum;
import com.zlz.blog.common.enums.article.VisibleStrategyEnum;
import com.zlz.blog.common.exception.BlogException;
import com.zlz.blog.common.response.PageInfo;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.common.util.PageUtil;
import com.zlz.blog.common.util.SqlResultUtil;
import com.zlz.blog.common.util.TokenUtil;
import com.zlz.blog.server.blog.mapper.BlogMapper;
import com.zlz.blog.server.blog.service.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 文章操作service实现类
 *
 * @author zhulinzhong
 * @version 2.0 CreateTime:2019/12/20 17:35
 */
@Service
public class BlogServiceImpl implements BlogService{

    @Resource
    private BlogAttachFileService blogAttachFileService;
    @Resource
    private BlogContentService blogContentService;
    @Resource
    private BlogStatisticsService blogStatisticsService;
    @Resource
    private BlogTagService blogTagService;
    @Resource
    private BlogMapper blogMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultSet<Blog> insertBlog(Blog blog, HttpServletRequest request) {
        if (null == blog || StringUtils.isEmpty(blog.getTitle())) {
            throw new BlogException("插入错误,数据为空");
        }

        //设置其他默认数据
        LoginUser user = TokenUtil.getLoginUser(request);

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
        insertBlogTag(blog.getId(), blog.getBlogTag(), request);

        return ResultSet.success("文章保存成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultSet<Blog> updateBlog(Blog blog, HttpServletRequest request) {
        LoginUser loginUser = TokenUtil.getLoginUser(request);
        if (StringUtils.isEmpty(loginUser.getUsername()) || null == blog.getId()) {
            return ResultSet.inputError();
        }

        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", blog.getId())
                .eq("user_id", loginUser.getId());

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

        return blogContentService.updateBody(blogContent, request);
    }

    @Override
    public ResultSet<Blog> updateBlogTitle(Blog blog, HttpServletRequest request) {
        //获取登录用户
        LoginUser loginUser = TokenUtil.getLoginUser(request);

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
        LoginUser loginUser = TokenUtil.getLoginUser(request);
        if (StringUtils.isEmpty(loginUser.getUsername()) || null == id) {
            return ResultSet.inputError();
        }

        Blog blogArticle = blogMapper.selectDetailById(id, loginUser.getId());
        if (null == blogArticle) {
            return ResultSet.outError();
        }

        return ResultSet.success(blogArticle);
    }

    @Override
    public ResultSet<Blog> selectList(Blog blog, HttpServletRequest request) {
        //获取当前登录用户
        LoginUser user = TokenUtil.getLoginUser(request);
        if (StringUtils.isEmpty(user.getUsername())) {
            return ResultSet.inputError();
        }

        //设置查询条件
        blog.setUserId(user.getId());

        //获取并设置筛选条件
        PageInfo<Blog> pageInfo = blog.getPageInfo();
        excludeColumn(pageInfo, blog);

        //返回结果转换并返回消息
        IPage<Blog> iPage = blogMapper.selectPage(PageUtil.getIPage(pageInfo), blog);
        return ResultSet.success(PageUtil.setPageInfo(iPage, blog.getPageInfo()));
    }

    @Override
    public ResultSet<Blog> deleteBlog(Long id, HttpServletRequest request) {
        //数据检查
        LoginUser loginUser = TokenUtil.getLoginUser(request);
        if (null == id || StringUtils.isEmpty(loginUser.getUsername())) {
            return ResultSet.inputError("删除失败,未找到文章或者登陆人为空");
        }

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
        //数据检查
        LoginUser loginUser = TokenUtil.getLoginUser(request);
        if (id == null || StringUtils.isEmpty(loginUser.getUsername())) {
            return ResultSet.inputError();
        }
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
        if (null == blog.getBlogContent()) {
            blogContent.setBlogId(blog.getId());
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
        ResultSet resultSet = blogContentService.insertBody(blogContent, request);
        if (!ResultSet.isSuccess(resultSet)) {
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
     * 插入标签，分类相关信息
     *
     * @param blogId blogId
     * @param blogTags blogTags
     */
    private void insertBlogTag(Long blogId, List<BlogTag> blogTags, HttpServletRequest request) {

        ResultSet<BlogTag> resultSet = blogTagService.insertTagList(blogId, blogTags, request);

        if (!ResultSet.isSuccess(resultSet)) {
            throw new BlogException("文章浏览信息插入失败");
        }
    }

    /**
     * 设置查询筛选条件
     *
     * @param pageInfo    pageInfo
     * @param blog blog
     */
    private void excludeColumn(PageInfo<Blog> pageInfo, Blog blog) {
        if (pageInfo.getExclude() != null && !pageInfo.getExclude().isEmpty()) {
            List<ExcludeItem> exclude = pageInfo.getExclude();
            List<ExcludeItem> needExclude = new ArrayList<>();
            PageInfo<Blog> newPageInfo = new PageInfo<>();
            exclude.forEach(item -> {
                if ("isDeleted".equals(item.getColumn()) && !StringUtils.isEmpty(item.getValue())) {
                    ExcludeItem excludeItem = new ExcludeItem();
                    excludeItem.setColumn("is_deleted");
                    excludeItem.setValue(item.getValue());
                    needExclude.add(excludeItem);
                } else if ("isShow".equals(item.getColumn()) && !StringUtils.isEmpty(item.getValue())) {
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
