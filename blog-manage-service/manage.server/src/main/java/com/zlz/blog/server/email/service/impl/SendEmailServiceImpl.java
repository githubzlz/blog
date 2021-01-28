package com.zlz.blog.server.email.service.impl;

import com.zlz.blog.common.entity.oauth.LoginUser;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.common.template.EmailRedisTemplate;
import com.zlz.blog.common.util.CheckCodeUtil;
import com.zlz.blog.server.email.service.SendEmailService;
import com.zlz.blog.server.oauth.service.LoginUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-11-20 16:06
 * @description
 */
@Service
@Slf4j
public class SendEmailServiceImpl implements SendEmailService {

    /**
     * Spring Boot 提供了一个发送邮件的简单抽象，使用的是下面这个接口，这里直接注入即可使用
     */
    @Resource
    private JavaMailSender mailSender;

    @Autowired
    private RedisTemplate<Object, EmailRedisTemplate> emailRedisTemplate;

    @Resource
    private LoginUserService loginUserService;

    /**
     * 配置文件中我的邮箱
     */
    private String from = "zlztsb.com";

    /**
     * 配置文件中获取验证码间隔时间
     */
    private Integer outTime = 60;

    /**
     * 配置文件中验证码失效时间
     */
    private Integer validityTime = 120;

    @Override
    public ResultSet<String> sendLoginMail(HttpServletRequest request, LoginUser user) {

        if(null == user || StringUtils.isEmpty(user.getEmail())){
            return ResultSet.error("邮箱地址不能为空");
        }

        String title = "EMAIL_CHECK_CODE";

        // 检查缓存是否存在改邮箱的记录
        Date now = new Date();
        EmailRedisTemplate template = emailRedisTemplate.opsForValue().get(title+user.getEmail());
        if(null != template){
            long time = now.getTime() - template.getDate().getTime();
            int remainingTime = outTime - (int)time/1000;
            if(remainingTime > 0){
                return ResultSet.error("请在" + remainingTime + "秒后重试");
            }
        }

        // 通过邮箱获取登陆人信息
        LoginUser byEmail = loginUserService.findByEmail(user.getEmail());
        if(null == byEmail){
            return ResultSet.error("当前邮箱未注册，请先注册用户");
        }

        // 设置缓存保存的信息
        String code = CheckCodeUtil.geneEmailCheckCode();
        template = new EmailRedisTemplate();
        template.setEmail(byEmail.getEmail());
        template.setUserId(byEmail.getId());
        template.setCheckCode(code);
        template.setDate(now);

        //放入缓存
        this.emailRedisTemplate.opsForValue().set(title + byEmail.getEmail(), template, validityTime, TimeUnit.SECONDS);

        sendSimpleMail(byEmail.getEmail(),
                "ZLZ博客后台登录验证码",
                "您的验证码为："
                        + code
                        + "。\n验证码在"
                        + validityTime/60
                        + "分钟内有效。\n不要将您的验证码告诉任何人，谨防盗号。");
        return ResultSet.success("邮件已发送，请查看邮箱，若没有收到邮件，请在2分钟后重试。");
    }

    /**
     * 简单文本邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        //创建SimpleMailMessage对象
        SimpleMailMessage message = new SimpleMailMessage();
        //邮件发送人
        message.setFrom(from);
        //邮件接收人
        message.setTo(to);
        //邮件主题
        message.setSubject(subject);
        //邮件内容
        message.setText(content);
        //发送邮件
        mailSender.send(message);
    }

    /**
     * html邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    @Override
    public void sendHtmlMail(String to, String subject, String content) {
        //获取MimeMessage对象
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(message, true);
            //邮件发送人
            messageHelper.setFrom(from);
            //邮件接收人
            messageHelper.setTo(subject);
            //邮件主题
            message.setSubject(subject);
            //邮件内容，html格式
            messageHelper.setText(content, true);
            //发送
            mailSender.send(message);
            //日志信息
            log.info("邮件已经发送。");
        } catch (MessagingException e) {
            log.error("发送邮件时发生异常！", e);
        }
    }

    /**
     * 带附件的邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @param filePath 附件
     */
    @Override
    public void sendAttachmentsMail(String to, String subject, String content, String filePath) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);
            mailSender.send(message);
            //日志信息
            log.info("邮件已经发送。");
        } catch (MessagingException e) {
            log.error("发送邮件时发生异常！", e);
        }

    }
}
