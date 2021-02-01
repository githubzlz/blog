package com.zlz.blog.common.entity.category;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zlz.blog.common.entity.blog.Blog;
import com.zlz.blog.common.entity.common.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * @author peeterZ
 * @version 2.0 CreateTime:2020-12-12 14:14
 * @description 文章种类
 */
@Data
@TableName("module")
public class Category extends BaseEntity<Category> {
    private static final long serialVersionUID = -3086115441709363100L;
    /**
     * 模块id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 模块标题
     */
    private String title;

    /**
     * 模块介绍
     */
    private String introduction;

    /**
     * 模块图片路径
     */
    private String imageUrl;

    /**
     * 0 不发布 1发布
     */
    private Integer isPublish;

    /**
     * 0 未删除 1 删除
     */
    private Integer isDeleted;

    /**
     * 种类对应的文章
     */
    @TableField(exist = false)
    private List<Blog> blogs;
}