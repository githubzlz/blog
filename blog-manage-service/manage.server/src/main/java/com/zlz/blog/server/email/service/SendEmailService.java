package com.zlz.blog.server.email.service;

import com.zlz.blog.common.entity.user.LoginUser;
import com.zlz.blog.common.response.ResultSet;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-11-20 16:05
 * @description 发送邮件
 */
public interface SendEmailService {

    /**
     * 发送简单邮件
     * @param request
     * @param user
     * @return
     */
    ResultSet<String> sendLoginMail(HttpServletRequest request, LoginUser user);

    /**
     * 发送文本邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    void sendSimpleMail(String to, String subject, String content);

    /**
     * 发送HTML邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    void sendHtmlMail(String to, String subject, String content);



    /**
     * 发送带附件的邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @param filePath 附件
     */
    void sendAttachmentsMail(String to, String subject, String content, String filePath);
}
