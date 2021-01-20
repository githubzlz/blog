package com.zlz.blog.common.entity.web;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author peeterZ
 * @version 2.0 CreateTime:2020-12-12 14:14
 * @description 网站的轮播图
 */
@Data
@TableName("web_banner")
public class WebBanner implements Serializable {
    private static final long serialVersionUID = 2373651491407018461L;
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 描述
     */
    private String title;

    /**
     * 二级描述
     */
    private String subTitle;

    /**
     * 图片路径
     */
    private String imgUrl;

    /**
     * 0:no 1:yes
     */
    private Byte isShow;

    private Long creator;

    private Date createdTime;

    private Long lastModifier;

    private Date lastModifiedTime;

}