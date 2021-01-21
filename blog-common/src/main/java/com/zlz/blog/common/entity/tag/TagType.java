package com.zlz.blog.common.entity.tag;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author peeterZ
 * @version 2.0 CreateTime:2020-12-12 14:14
 * @description 标签分类
 */
@Data
@TableName("tag_type")
public class TagType implements Serializable {
    private static final long serialVersionUID = -1003865481221641364L;
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 分类名称
     */
    private String typeName;

    /**
     * 0停用 1启用
     */
    private Boolean state;

    private Long creator;

    private Date createdTime;

    private Long lastModifier;

    private Date lastModifiedTime;

    /**
     * 标签
     */
    @TableField(exist = false)
    private List<Tag> tags;
}