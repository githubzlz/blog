package com.zlz.blog.server.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zlz.blog.common.entity.blog.BlogTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-12-12 11:51
 * @description 博客与标签关联表
 */
@Mapper
public interface BlogTagMapper extends BaseMapper<BlogTag> {

    /**
     * 批量插入标签关联信息
     * @param blogTags blogTags
     * @param blogId blogId
     * @return
     */
    int insertList(@Param("blogId") Long blogId, @Param("blogTags") List<BlogTag> blogTags);
}
