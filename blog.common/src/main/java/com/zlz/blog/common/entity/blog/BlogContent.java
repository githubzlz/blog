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
 * @description 博客文章主体内容
 */
@Data
@TableName("blog_content")
public class BlogContent implements Serializable {
    private static final long serialVersionUID = 822449018432064570L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 文章id
     */
    private Long blogId;

    /**
     * html
     */
    private String contentHtml;

    /**
     * md
     */
    private String contentMd;

    /**
     * html的大小
     */
    private String htmlSize;

    /**
     * md的大小
     */
    private String mdSize;

    private Long creator;

    private Date createdTime;

    private Long lastModifier;

    private Date lastModifiedTime;

}