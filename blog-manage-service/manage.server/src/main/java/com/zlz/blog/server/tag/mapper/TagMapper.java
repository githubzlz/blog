package com.zlz.blog.server.tag.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zlz.blog.common.entity.tag.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author peeterZ
 * @version 2.0 CreateTime:2021-02-04 16:55
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 批量插入
     * @param tags
     * @return
     */
    int insertList(@Param("tags") List<Tag> tags);

    /**
     * 插入标签文章关联关系
     * @param tags
     * @param blogId
     */
    void insertRelationWithBlog(@Param("tags") List<Tag> tags,@Param("blogId") Long blogId);

    /**
     * 删除文章的标签关联关系
     * @param blogId
     * @return
     */
    int deleteRelationWithBlog(Long blogId);
}
