package com.zlz.blog.oauth.server.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zlz.blog.client.BlogManageClient;
import com.zlz.blog.common.entity.user.LoginUser;
import com.zlz.blog.common.exception.BlogException;
import com.zlz.blog.common.token.UserVo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.management.relation.Role;
import java.security.Permission;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2019/10/12 16:37
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private BlogManageClient blogManageClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        username = Optional.of(username).orElseThrow(() -> new BlogException("用户名不能为空"));
        LoginUser loginUser = blogManageClient.getAuthenticationInfo(username);

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        loginUser = Optional.ofNullable(loginUser).orElseThrow(() -> new BlogException("用户信息为空"));
        loginUser.getSysRoles().forEach(role -> {
            role.getSysPermissions().forEach(permission ->{
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getEname());
                grantedAuthorities.add(grantedAuthority);
            });
        });
        return new UserVo(loginUser.getUsername(), loginUser.getPassword(), grantedAuthorities, loginUser.getId());
    }
}
