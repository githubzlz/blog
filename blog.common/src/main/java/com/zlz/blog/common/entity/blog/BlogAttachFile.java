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
 * @description 博客文章附件
 */
@Data
@TableName("blog_attach_file")
public class BlogAttachFile implements Serializable {
    private static final long serialVersionUID = 2765703555693290018L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 博客文章的id
     */
    private Long blogId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件大小
     */
    private String fileSize;

    /**
     * 文件保存路径
     */
    private String fileUrl;

    /**
     * 文件md5值
     */
    private String md5;

    private Long creator;

    private Date createdTime;

    private Long lastModifier;

    private Date lastModifiedTime;

}