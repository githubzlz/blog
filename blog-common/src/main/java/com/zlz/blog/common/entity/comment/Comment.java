package com.zlz.blog.common.entity.comment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author peeterZ
 * @version 2.0 CreateTime:2020-12-12 14:14
 * @description 评论
 */
@Data
@TableName("comment")
public class Comment implements Serializable {
    private static final long serialVersionUID = -387195575820874947L;
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    private Long blogId;

    /**
     * 父id，若是没有上级则为0
     */
    private Long parentId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 点赞的数量
     */
    private Long likes;

    /**
     * 层级 0：盖楼层 1：回复层
     */
    private Byte level;

    /**
     * 创建人，默认为评论所属人
     */
    private String creator;

    private Date createdTime;

    private String lastModifier;

    private Date lastModifiedTime;

}