package com.zlz.blog.common.entity.blog;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author peeterZ
 * @version 2.0 CreateTime:2020-12-12 14:14
 * @description 博客文章统计信息
 */
@Data
@TableName("blog_statistics")
public class BlogStatistics implements Serializable {
    private static final long serialVersionUID = -6504023031770327405L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    private Long blogId;

    /**
     * 星星数量
     */
    private Long stars;

    /**
     * 点赞数量
     */
    private Long goods;

    /**
     * 评论数量
     */
    private Long comments;

    /**
     * 阅读数量
     */
    private Long readings;

    /**
     * 收藏数量
     */
    private Long collect;

    private Long creator;

    private Date createdTime;

    private Long lastModifier;

    private Date lastModifiedTime;

}