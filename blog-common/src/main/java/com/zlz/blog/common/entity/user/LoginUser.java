package com.zlz.blog.common.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zlz.blog.common.entity.common.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2019/12/20 18:15
 */
@TableName("sys_user")
@Data
public class LoginUser extends BaseEntity<LoginUser> {

    private static final long serialVersionUID = -749363217569096813L;
    /**
     * id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐值
     */
    private String salt;

    /**
     * email
     */
    private String email;

    /**
     * 电话号
     */
    private String phone;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 状态 0 ：正常 1：停用 2：注销
     */
    private Integer state;

    /**
     * 是否锁定 0：正常 1：锁定
     */
    private Integer locked;

    /**
     * 用户角色
     */
    @TableField(exist = false)
    private List<SysRole> sysRoles;

    /**
     * 邮箱验证码
     */
    @TableField(exist = false)
    private String checkCode;
}
