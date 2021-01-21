package com.zlz.blog.common.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * created by zlz on 2020/10/22 17:26
 **/
@Data
@TableName("sys_permission")
public class SysPermission {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 父级id
     */
    private Long parentId;

    /**
     * 权限名
     */
    private String permission;

    /**
     * 权限名（English）
     */
    private String ename;

    /**
     * 路径（资源）
     * 此处的资源为url，给url添加权限，就能实现访问的权限控制
     */
    private String url;

    /**
     * 描述
     */
    private String description;
}
