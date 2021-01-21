package com.zlz.blog.common.util;

import com.zlz.blog.common.entity.user.LoginUser;
import com.zlz.blog.common.exception.BlogException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-05-19 11:14
 * @description token解析
 */
@Slf4j
public class TokenUtil {

    /**
     * 获取用户信息
     *
     * @param request
     * @return
     */
    public static LoginUser getLoginUser(HttpServletRequest request) {
        String token = "XXXXXXXXXXXXXXX";
        return getLoginUser(token);
    }

    /**
     * 获取用户信息
     *
     * @param token
     * @return
     */
    public static LoginUser getLoginUser(String token) {

        try {
            if (StringUtils.isEmpty(token)) {
                throw new BlogException("未获取到当前登陆用户");
            }

            //设置用户信息实体
            LoginUser loginUser = new LoginUser();
            loginUser.setUsername("zlztest");
            loginUser.setId(10000L);
            return loginUser;
        } catch (Exception e) {
            log.error("Exception[{}]", e);
            throw new BlogException("未获取到当前登陆用户");
        }
    }
}
