package com.zlz.blog.server.tag.service;

import com.zlz.blog.common.entity.tag.Tag;
import com.zlz.blog.common.response.ResultSet;

import java.util.List;

/**
 * @author peeterZ
 * @version 2.0 CreateTime:2021-02-04 16:54
 */
public interface TagService {

    /**
     * 查询标签列表
     * @param tag
     * @return
     */
    ResultSet<List<Tag>> queryTagList(Tag tag);

    /**
     * 创建新的标签
     * @param tags
     * @return
     */
    ResultSet<List<Tag>> createTag(List<Tag> tags);

    /**
     * 插入关联关系
     * @param tags
     * @param blogId
     * @return
     */
    ResultSet<List<Tag>> addRelationWithBlog(List<Tag> tags, Long blogId);

    /**
     * 删除文章关联关系
     * @param blogId
     * @return
     */
    ResultSet<Tag> deleteRelationWithBlog(Long blogId);
}
