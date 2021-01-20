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
 * @description 网站信息的统计
 */
@Data
@TableName("web_statistics")
public class WebStatistics implements Serializable {

    private static final long serialVersionUID = -5078187666989599646L;
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 时间
     */
    private Date date;

    /**
     * 今日浏览量
     */
    private Long pvToday;

    /**
     * 今日阅读量
     */
    private Long readingToday;

    /**
     * 今日新文章数量
     */
    private Long blogToday;

    /**
     * 今日新用户数量
     */
    private Long userToday;

    /**
     * 今日新留言数量
     */
    private Long messageToday;

    /**
     * 总浏览量
     */
    private Long pvTotal;

    /**
     * 总阅读量
     */
    private Long readingTotal;

    /**
     * 总文章数量
     */
    private Long blogTotal;

    /**
     * 总用户数量
     */
    private Long userTotal;

    /**
     * 总留言数量
     */
    private Long messageTotal;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 修改时间
     */
    private Date lastModifiedTime;

}