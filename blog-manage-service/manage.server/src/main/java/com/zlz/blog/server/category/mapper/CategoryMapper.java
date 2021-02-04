package com.zlz.blog.server.category.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zlz.blog.common.entity.blog.Blog;
import com.zlz.blog.common.entity.category.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 分类管理
 *
 * @author peeterZ
 * @version 2.0 CreateTime:2021-02-01 11:52
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * 分页查询分类
     * @param iPage
     * @param category
     * @return
     */
    IPage<Category> selectPage(IPage<Category> iPage,@Param("category") Category category);

    /**
     * 通过分类id查询文章集合
     * @param categoryId
     * @return
     */
    List<Blog> selectBlogByCategoryId(Long categoryId);

    /**
     * 查询当前登陆人未被关联过种类的文章
     * @param userId
     * @return
     */
    List<Blog> selectBlogFreedom(Long userId);

    /**
     * 更新种类文章绑定关系
     * @param categoryId
     * @param blogs
     * @return
     */
    int insertCategoryBlog(@Param("categoryId") Long categoryId,@Param("blogs") List<Blog> blogs);

    /**
     * 删除种类文章绑定关系
     * @param id
     * @return
     */
    int deleteCategoryBlog(Long id);

    /**
     * 获取兄弟节点的最大分级码
     * @param parentId
     * @return
     */
    String getMaxBrolevelCode(Long parentId);

    /**
     * ，该分类的所有子分类，以及
     * @param id
     * @return
     */
    int deleteCategoryAndChildren(Long id);

    /**
     * 删除分类与blog的关联关系
     * @param id
     * @return
     */
    int deleteCategoryBlogRelation(Long id);
}
