package com.zlz.blog.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.impl.JWTParser;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zlz.blog.common.entity.oauth.LoginUser;
import com.zlz.blog.common.exception.BlogException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

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
     * @return
     */
    public static LoginUser getLoginUser() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = request.getHeader("Authorization");
        return getLoginUser(token);
    }

    /**
     * 获取用户信息
     *
     * @param request
     * @return
     */
    public static LoginUser getLoginUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
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
            assert token != null;
            token = token.substring(7);
            DecodedJWT decoded = JWT.decode(token);
            Map<String, Claim> claims = decoded.getClaims();
            String name = claims.get("user_name").asString();
            Long userId = claims.get("user_id").asLong();
            //设置用户信息实体
            LoginUser loginUser = new LoginUser();
            loginUser.setUsername(name);
            loginUser.setId(userId);
            return loginUser;
        } catch (Exception e) {
            log.error("Exception：", e);
            return null;
        }
    }
}
