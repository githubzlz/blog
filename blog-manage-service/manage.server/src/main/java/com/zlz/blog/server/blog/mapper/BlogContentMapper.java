package com.zlz.blog.server.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zlz.blog.common.entity.blog.BlogContent;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-12-12 11:50
 * @description 博客文章内容
 */
@Mapper
public interface BlogContentMapper extends BaseMapper<BlogContent> {
}
