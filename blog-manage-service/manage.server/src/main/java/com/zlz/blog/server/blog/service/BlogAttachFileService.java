package com.zlz.blog.server.blog.service;

import com.zlz.blog.common.entity.blog.BlogAttachFile;
import com.zlz.blog.common.response.ResultSet;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-12-12 11:44
 * @description 博客附件
 */
public interface BlogAttachFileService {
    /**
     * 上传图片
     *
     * @param file 文件
     * @return 上传文件返回消息封装的对象
     * @throws IOException io异常
     */
    ResultSet<BlogAttachFile> uploadImage(MultipartFile file) throws IOException, NoSuchAlgorithmException;

    /**
     * 下载文件
     *
     * @param path     路径
     * @param response 响应
     */
    void downloadFile(String path, HttpServletResponse response);
}
