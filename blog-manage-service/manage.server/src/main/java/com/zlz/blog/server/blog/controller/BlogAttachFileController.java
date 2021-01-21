package com.zlz.blog.server.blog.controller;

import com.zlz.blog.common.entity.blog.BlogAttachFile;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.server.blog.service.BlogAttachFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * created by zlz on 2020/12/18 16:00
 **/
@RestController
@RequestMapping("/file")
public class BlogAttachFileController {

    private BlogAttachFileService blogAttachFileService;

    @Autowired
    public void setBlogAttachFileService(BlogAttachFileService blogAttachFileService){
        this.blogAttachFileService = blogAttachFileService;
    }

    /**
     * 上传图片，返回图片地址到前端
     *
     * @param file 文件
     * @throws IOException IOException
     */
    @PostMapping("/image/upload")
    public ResultSet<BlogAttachFile> upload(MultipartFile file) throws IOException, NoSuchAlgorithmException {
        return blogAttachFileService.uploadImage(file);
    }

    /**
     * 下载文件
     *
     * @param path     文件路径
     * @param response response
     */
    @PostMapping("/download")
    public void download(String path, HttpServletResponse response) {
        blogAttachFileService.downloadFile(path, response);
    }
}
