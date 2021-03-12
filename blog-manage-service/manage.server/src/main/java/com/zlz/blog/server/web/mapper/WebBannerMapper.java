package com.zlz.blog.server.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zlz.blog.common.entity.web.WebBanner;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2021-03-10 15:02
 * @description bannner的mapper
 */
@Mapper
public interface WebBannerMapper extends BaseMapper<WebBanner> {
}
