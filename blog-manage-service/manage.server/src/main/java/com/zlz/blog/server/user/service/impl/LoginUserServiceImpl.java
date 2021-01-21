package com.zlz.blog.server.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zlz.blog.common.entity.user.LoginUser;
import com.zlz.blog.common.entity.user.SysRole;
import com.zlz.blog.common.enums.LoginTypeEnum;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.common.template.EmailRedisTemplate;
import com.zlz.blog.common.util.EncryptionUtil;
import com.zlz.blog.common.util.SqlResultUtil;
import com.zlz.blog.server.user.mapper.AuthenticationMapper;
import com.zlz.blog.server.user.mapper.LoginUserMapper;
import com.zlz.blog.server.user.service.LoginUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-11-18 11:54
 * @description
 */
@Service
public class LoginUserServiceImpl implements LoginUserService {

    @Resource
    private LoginUserMapper userMapper;
    @Resource
    private AuthenticationMapper authenticationMapper;

    @Autowired
    private RedisTemplate<Object, EmailRedisTemplate> emailRedisTemplate;


    @Override
    public ResultSet<LoginUser> registerUser(LoginUser loginUser, String type) {
        Optional<LoginUser> loginUserOpt = Optional.of(loginUser);
        LoginTypeEnum loginTypeEnum = LoginTypeEnum.getLoginTypeEnum(type);

        switch (loginTypeEnum){
            case PASSWORD:
                String username = loginUserOpt.get().getUsername();
                QueryWrapper<LoginUser> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("username", username);
                int count = userMapper.selectCount(queryWrapper);
                if(count != 0){
                    return ResultSet.error("用户名已经存在,请尝试新的用户名");
                }
                String salt = UUID.randomUUID().toString();
                String password = EncryptionUtil.md5Encrypt(loginUserOpt.get().getPassword(), salt);
                LoginUser user = new LoginUser();
                user.setSalt(salt);
                user.setUsername(username);
                user.setPassword(password);
                int insert = userMapper.insert(user);
                return SqlResultUtil.isOneRow(insert);
            case EMAIL_VERIFICATION_CODE:
                break;
            case PHONE_VERIFICATION_CODE:
                break;
            default:
                return ResultSet.error("注册失败");
        }
        return ResultSet.error("注册失败");
    }

    @Override
    public LoginUser findByEmail(String email) {
        Optional<String> oName = Optional.of(email);

        // 查询用户
        QueryWrapper<LoginUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", oName.get())
                .select("id", "email", "phone", "username");
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public LoginUser findByUsername(String name) {
        Optional<String> oName = Optional.of(name);

        // 查询用户
        QueryWrapper<LoginUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", oName.get())
                .select("id", "email", "phone", "username", "salt", "password");
        LoginUser userData = userMapper.selectOne(queryWrapper);

        if(null != userData){
            // 获权限信息
            List<SysRole> roles = authenticationMapper.getAuthenticationInfo(userData.getId());
            userData.setSysRoles(roles);
        }
        return userData;
    }

    private LoginUser setAuthenticationInfo(LoginUser userData){
        // 获权限信息
        List<SysRole> roles = authenticationMapper.getAuthenticationInfo(userData.getId());
        LoginUser authenticationInfo = new LoginUser();
        authenticationInfo.setSysRoles(roles);
        authenticationInfo.setUsername(userData.getUsername());
        authenticationInfo.setEmail(userData.getEmail());
        return authenticationInfo;
    }
}
