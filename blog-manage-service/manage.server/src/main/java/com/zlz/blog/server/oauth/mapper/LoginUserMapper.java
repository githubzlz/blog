package com.zlz.blog.server.oauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zlz.blog.common.entity.oauth.LoginUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-11-18 11:53
 * @description
 */
@Mapper
public interface LoginUserMapper extends BaseMapper<LoginUser> {
}
