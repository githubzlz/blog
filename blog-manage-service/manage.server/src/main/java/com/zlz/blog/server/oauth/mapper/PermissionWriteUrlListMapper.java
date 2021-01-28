package com.zlz.blog.server.oauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zlz.blog.common.entity.oauth.BlogWriteUrlList;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2021-01-27 11:20
 * @description 路由白名单
 */
@Mapper
public interface PermissionWriteUrlListMapper extends BaseMapper<BlogWriteUrlList> {
}
