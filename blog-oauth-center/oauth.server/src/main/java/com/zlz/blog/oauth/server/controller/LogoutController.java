package com.zlz.blog.oauth.server.controller;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
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

    private ConsumerTokenServices consumerTokenServices;
    @Autowired
    public void setConsumerTokenServices(ConsumerTokenServices consumerTokenServices){
        this.consumerTokenServices = consumerTokenServices;
    }

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
    public String logOut(HttpServletRequest request){
        try {
            System.out.println("111");
            String host = request.getHeader("Host");
            String authorization = request.getHeader("Authorization");
            if(authorization == null){
                return "error";
            }
            String[] s = authorization.split(" ");
            if(s.length != 2){
                return "error";
            }
            String token = s[1];
            System.out.println(token);
            boolean b = consumerTokenServices.revokeToken(token);
            if(!b){
                return "error";
            }

            //清空session
            request.getSession().invalidate();
            return "true";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }


    @GetMapping("/getPublicKey")
    public Map<String, Object> getPublicKey() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }

}
