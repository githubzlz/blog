package com.zlz.blog.common.entity.module;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author peeterZ
 * @version 2.0 CreateTime:2020-12-12 14:14
 * @description 目录与博客的关联
 */
@Data
@TableName("module_menu_blog")
public class ModuleMenuBlog implements Serializable {
    private static final long serialVersionUID = -4883559095958469992L;
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    private Long moduleMenuId;

    private Long blogId;
}