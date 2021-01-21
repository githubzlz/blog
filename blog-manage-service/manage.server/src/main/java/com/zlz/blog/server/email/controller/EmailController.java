package com.zlz.blog.server.email.controller;

import com.ramostear.captcha.HappyCaptcha;
import com.ramostear.captcha.support.CaptchaType;
import com.zlz.blog.common.entity.user.LoginUser;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.server.email.service.SendEmailService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-11-20 16:18
 * @description
 */
@RestController
public class EmailController {

    @Resource
    private SendEmailService sendEmailService;


    /**
     * 邮箱验证码登录
     * @param request
     * @param user
     * @return
     */
    @PostMapping("/sendemail")
    public ResultSet sendEmail(HttpServletRequest request, @RequestBody LoginUser user){
        return sendEmailService.sendLoginMail(request, user);
    }

    /**
     * 生成验证码
     * @param request
     * @param response
     */
    @GetMapping("/getCheckCode/{random}")
    public void getCheckCode(HttpServletRequest request, HttpServletResponse response){
        HappyCaptcha.require(request,response).width(90).height(42)
                .type(CaptchaType.NUMBER)
                .build().finish();
    }

    /**
     * 验证验证码
     * @param request
     * @param code
     * @return
     */
    @GetMapping("/checkCode/check/{code}")
    public ResultSet checkCheckCode(HttpServletRequest request, @PathVariable("code") String code){
        boolean verification = HappyCaptcha.verification(request, code, true);
        if(verification){
            return ResultSet.success("验证通过");
        }
        return ResultSet.error("验证码错误");
    }
}
