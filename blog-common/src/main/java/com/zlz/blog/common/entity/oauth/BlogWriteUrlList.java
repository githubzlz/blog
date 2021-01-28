package com.zlz.blog.common.entity.oauth;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author peeterZ
 * @version 2.0 CreateTime:2021-01-27 11:18
 * @description 路由白名单，无需权限即可访问
 */
@TableName("permession_write_list")
@Data
public class BlogWriteUrlList {

    /**
     * id
     */
    private Long id;

    /**
     * 路由地址
     */
    private String url;

    /**
     * 描述
     */
    private String description;
}
