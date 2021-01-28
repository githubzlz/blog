package com.zlz.blog.server.oauth.service;

import com.zlz.blog.common.entity.oauth.LoginUser;
import com.zlz.blog.common.response.ResultSet;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-11-18 11:53
 * @description
 */
public interface LoginUserService {

    /**
     * 用户注册
     * @param loginUser
     * @param type
     * @return
     */
    ResultSet<LoginUser> registerUser(LoginUser loginUser, String type);

    LoginUser findByEmail(String email);

    LoginUser findByUsername(String name);

}
