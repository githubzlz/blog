package com.zlz.blog.common.vos.blog;

import com.zlz.blog.common.entity.blog.Blog;
import lombok.Data;

import java.util.List;

/**
 * blog vo类
 *
 * @author peeterZ
 * @version 2.0 CreateTime:2021-02-04 14:28
 */
@Data
public class BlogVO extends Blog {

    /**
     * 查询文章的集合
     */
    private List<Long> categoryIds;
}
