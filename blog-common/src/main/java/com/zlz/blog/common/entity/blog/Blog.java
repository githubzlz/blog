package com.zlz.blog.common.entity.blog;

import com.baomidou.mybatisplus.annotation.*;
import com.zlz.blog.common.entity.comment.Comment;
import com.zlz.blog.common.response.PageInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author peeterZ
 * @version 2.0 CreateTime:2020-12-12 14:14
 * @description 博客文章
 */
@Data
@TableName("blog")
public class Blog implements Serializable {
    private static final long serialVersionUID = 6223378491653952167L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 创建人
     */
    private Long userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 作者
     */
    private String author;

    /**
     * 出处 0 原创 1 转载 2 翻译
     */
    private Integer provenance;

    /**
     * 可见策略 0 所有人 1 粉丝 2 付费
     */
    private Integer visibleStrategy;

    /**
     * 文章图片路径
     */
    private String imgSrc;

    /**
     * 0 不允许 1 允许
     */
    private Integer isShow;

    /**
     * 0 未删除 1 删除
     */
    @TableLogic
    private Integer isDeleted;

    private Long creator;

    private Date createdTime;

    private Long lastModifier;

    private Date lastModifiedTime;

    /**
     * 文章的内容
     */
    @TableField(exist = false)
    private BlogContent blogContent;

    /**
     * 文章的统计信息
     */
    @TableField(exist = false)
    private BlogStatistics blogStatistics;

    /**
     * 文章的附件
     */
    @TableField(exist = false)
    private List<BlogAttachFile> attachFiles;

    /**
     * 文章的标签
     */
    @TableField(exist = false)
    private List<BlogTag> blogTag;

    /**
     * 文章的标签
     */
    @TableField(exist = false)
    private List<String> tags;

    /**
     * 文章的评论
     */
    @TableField(exist = false)
    private List<Comment> comments;

    /**
     * 分页信息
     */
    @TableField(exist = false)
    private PageInfo<Blog> pageInfo;

    /**
     * 种类绑定关系
     */
    @TableField(exist = false)
    private Long categorBlogId;
}