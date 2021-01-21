package com.zlz.blog.common.entity.common;

import com.baomidou.mybatisplus.annotation.TableField;
import com.zlz.blog.common.response.PageInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体类
 *
 * @author zhulinzhong
 * @version 1.0 CreateTime:2019/12/20 17:25
 */
@Data
public class BaseEntity<T> implements Serializable {

    private static final long serialVersionUID = 6953538375643325713L;

    private Long creator;

    private Date createdTime;

    private Long lastModifier;

    private Date lastModifiedTime;

    @TableField(exist = false)
    private PageInfo<T> pageInfo;

}
