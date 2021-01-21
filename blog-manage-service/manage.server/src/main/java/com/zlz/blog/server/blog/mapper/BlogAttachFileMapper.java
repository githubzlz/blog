package com.zlz.blog.server.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zlz.blog.common.entity.blog.BlogAttachFile;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-12-12 11:44
 * @description 博客附件
 */
@Mapper
public interface BlogAttachFileMapper extends BaseMapper<BlogAttachFile> {
}
