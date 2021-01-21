package com.zlz.blog.server.user.service;

import com.zlz.blog.common.entity.user.LoginUser;
import com.zlz.blog.common.response.ResultSet;

/**
 * created by zlz on 2021/1/5 20:00
 **/
public interface UserManageService {

    /**
     * 获取用户列表
     * @param loginUser
     * @return
     */
    ResultSet loginUserList(LoginUser loginUser);
}
