package com.zlz.blog.common.entity.module;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zlz.blog.common.entity.common.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * @author peeterZ
 * @version 2.0 CreateTime:2020-12-12 14:14
 * @description 模块目录
 */
@Data
@TableName("module_menu")
public class ModuleMenu extends BaseEntity<ModuleMenu> {
    private static final long serialVersionUID = 9025708445064044104L;
    /**
     * 目录id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 模块id
     */
    private Long moduleId;

    /**
     * 目录名
     */
    private String name;

    /**
     * 博客文章
     */
    @TableField(exist = false)
    private List<ModuleMenuBlog> blogList;
}