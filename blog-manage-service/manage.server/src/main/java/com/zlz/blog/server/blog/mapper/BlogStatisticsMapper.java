package com.zlz.blog.server.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zlz.blog.common.entity.blog.BlogStatistics;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-12-12 11:50
 * @description 博客统计信息
 */
@Mapper
public interface BlogStatisticsMapper extends BaseMapper<BlogStatistics> {
}
