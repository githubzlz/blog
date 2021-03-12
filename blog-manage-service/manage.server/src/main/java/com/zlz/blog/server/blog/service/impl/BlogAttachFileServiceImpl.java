package com.zlz.blog.server.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zlz.blog.common.entity.blog.BlogAttachFile;
import com.zlz.blog.common.exception.BlogException;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.common.util.WebUtil;
import com.zlz.blog.server.blog.mapper.BlogAttachFileMapper;
import com.zlz.blog.server.blog.service.BlogAttachFileService;
import com.zlz.fastdfs.util.FastdfsUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author peeterZ
 * @version 2.0 CreateTime:2020-12-17 20:07
 * @description
 */
@Service
public class BlogAttachFileServiceImpl implements BlogAttachFileService {

    /**
     * 文件标准大小
     */
    @Value("${self.standard.size}")
    private String imgSize;

    @Resource
    private FastdfsUtil fastdfsUtil;
    @Resource
    private BlogAttachFileMapper fileMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultSet<String> uploadImage(MultipartFile file) throws IOException, NoSuchAlgorithmException {

        // 计算文件md5
        MessageDigest md5I = MessageDigest.getInstance("MD5");
        md5I.update(file.getBytes());
        BigInteger bi = new BigInteger(1, md5I.digest());
        String md5 = bi.toString(16);
        String fileName = file.getOriginalFilename();

        // 查询文件是否已经上传过
        QueryWrapper<BlogAttachFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5", md5);
        BlogAttachFile blogAttachFile1 = fileMapper.selectOne(queryWrapper);
        // 若是上传过直接返回上传成功
        if (null != blogAttachFile1) {
            return ResultSet.success("上传成功", blogAttachFile1.getFileUrl());
        }

        // 文件质量处理
        float size = file.getSize();
        if(size == 0){
            throw new BlogException("文件上传失败，请重试");
        }
        float quality = Long.parseLong(imgSize) * 1024 / size;
        if (quality > 1) {
            quality = 1;
        }

        // 获取处理之后的文件流转为字节数组，将文件上传到文件服务器
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Thumbnails.of(file.getInputStream()).size(800, 600).outputQuality(quality).toOutputStream(out);
        byte[] bytes = out.toByteArray();
        String path = WebUtil.uploadFile(bytes, fileName, fastdfsUtil);
        if (StringUtils.isEmpty(path)) {
            throw new BlogException("文件上传失败");
        }

        // 将文件信息保存到数据库
        BlogAttachFile blogAttachFile = new BlogAttachFile();
        blogAttachFile.setFileName(fileName);
        assert fileName != null;
        String[] split = fileName.split("\\.");
        if (split.length > 1) {
            blogAttachFile.setFileType(split[split.length - 1]);
        }
        blogAttachFile.setFileUrl(path);
        blogAttachFile.setMd5(md5);
        blogAttachFile.setFileSize(String.valueOf(bytes.length));
        fileMapper.insert(blogAttachFile);
        return StringUtils.isEmpty(path) ? ResultSet.error("上传失败") : ResultSet.success("上传成功", path);
    }

    @Override
    public void downloadFile(String path, HttpServletResponse response) {
        WebUtil.downloadFile(path, response, fastdfsUtil);
    }

}

