package com.zlz.blog.common.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * created by zlz on 2020/10/22 17:25
 **/
@Data
@TableName("sys_role")
public class SysRole implements Serializable {

    private static final long serialVersionUID = -7936335674844588148L;
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
     * 角色名
     */
    private String role;

    /**
     * 角色名（English）
     */
    private String ename;

    /**
     * 描述
     */
    private String description;

    /**
     * 角色拥有的权限
     */
    @TableField(exist = false)
    private List<SysPermission> sysPermissions;
}
