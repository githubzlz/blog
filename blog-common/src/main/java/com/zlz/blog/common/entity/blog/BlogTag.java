package com.zlz.blog.common.entity.blog;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author peeterZ
 * @version 2.0 CreateTime:2020-12-12 14:14
 * @description 博客文章标签关联实体
 */
@Data
@TableName("blog_tag")
public class BlogTag implements Serializable {
    private static final long serialVersionUID = -1077782982593687362L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    private Long blogId;

    private Long tagId;

}