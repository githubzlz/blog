package com.zlz.blog.oauth.server.controller;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.zlz.blog.common.response.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-06-07 09:20
 * @description 退出登陆
 */
@RestController
public class LogoutController {

    private KeyPair keyPair;
    @Autowired
    public void setKeyPair(KeyPair keyPair){
        this.keyPair = keyPair;
    }

    /**
     * 设置token失效
     * @param request
     * @return
     */
    @RequestMapping("/token/logout")
    public ResultSet logOut(HttpServletRequest request){
        try {
            //清空session
            request.getSession().invalidate();
            return ResultSet.success("退出登陆成功");
        }catch (Exception e){
            e.printStackTrace();
            return  ResultSet.error("退出登陆失败");
        }
    }

    /**
     * 获取认证token合法性的公钥
     * @return
     */
    @GetMapping("/getPublicKey")
    public Map<String, Object> getPublicKey() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }

}
