package com.zlz.blog.server.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zlz.blog.common.entity.blog.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 文章操作的service接口
 *
 * @author zhulinzhong
 * @version 1.0 CreateTime:2019/12/20 17:34
 */
@Mapper
public interface BlogMapper extends BaseMapper<Blog> {

    /**
     * 查询blog详细信息
     * @param id
     * @param userId
     * @return
     */
    Blog selectDetailById(@Param("id") Long id, @Param("userId")Long userId);

    /**
     * 文章分页查询sql
     *
     * @param page    page
     * @param blog blog
     * @return IPage
     */
    IPage<Blog> selectPage(IPage<Blog> page, @Param("blog") Blog blog);

    int revokeDelete(Long id);
}
