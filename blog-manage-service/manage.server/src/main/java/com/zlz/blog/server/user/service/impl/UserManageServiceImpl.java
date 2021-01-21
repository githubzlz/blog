package com.zlz.blog.server.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zlz.blog.common.entity.user.LoginUser;
import com.zlz.blog.common.response.PageInfo;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.common.util.PageUtil;
import com.zlz.blog.server.user.mapper.LoginUserMapper;
import com.zlz.blog.server.user.service.UserManageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * created by zlz on 2021/1/5 20:00
 **/
@Service
public class UserManageServiceImpl implements UserManageService {

    @Resource
    private LoginUserMapper userMapper;

    @Override
    public ResultSet loginUserList(LoginUser loginUser) {
        Optional<LoginUser> user = Optional.of(loginUser);
        PageInfo<LoginUser> pageInfo = user.get().getPageInfo();

        QueryWrapper<LoginUser> queryWrapper = new QueryWrapper<>();
        if(null != user.get().getUsername()){
            queryWrapper.like("username",user.get().getUsername());
        }
        if(null != user.get().getEmail()){
            queryWrapper.like("email",user.get().getEmail());
        }
        if(null != user.get().getPhone()){
            queryWrapper.like("phone",user.get().getPhone());
        }
        if(null != user.get().getState()){
            queryWrapper.eq("state",user.get().getState());
        }
        if(null != user.get().getLocked()){
            queryWrapper.eq("locked",user.get().getLocked());
        }

        IPage<LoginUser> loginUserIPage = userMapper.selectPage(PageUtil.getIPage(pageInfo), queryWrapper);

        return ResultSet.success("查询用户列表成功", PageUtil.setPageInfo(loginUserIPage, pageInfo));
    }
}
