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

    @Resource
    private FastdfsUtil fastdfsUtil;
    @Resource
    private BlogAttachFileMapper fileMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultSet<BlogAttachFile> uploadImage(MultipartFile file) throws IOException, NoSuchAlgorithmException {

        MessageDigest md5I = MessageDigest.getInstance("MD5");
        md5I.update(file.getBytes());
        BigInteger bi = new BigInteger(1, md5I.digest());
        String md5 = bi.toString(16);
        String fileName = file.getOriginalFilename();

        QueryWrapper<BlogAttachFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5", md5);
        BlogAttachFile BlogAttachFile1 = fileMapper.selectOne(queryWrapper);

        if (null != BlogAttachFile1) {
            return ResultSet.success("上传成功", BlogAttachFile1.getFileUrl());
        }

        int base = 200 * 1024;
        float quality = base / file.getSize();
        if (quality > 1) {
            quality = 1;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Thumbnails.of(file.getInputStream()).size(800, 600).outputQuality(quality).toOutputStream(out);
        byte[] bytes = out.toByteArray();

        String path = WebUtil.uploadFile(bytes, fileName, fastdfsUtil);
        if (StringUtils.isEmpty(path)) {
            throw new BlogException("文件上传失败");
        }
        BlogAttachFile BlogAttachFile = new BlogAttachFile();
        BlogAttachFile.setFileName(fileName);
        assert fileName != null;
        String[] split = fileName.split("\\.");
        if (split.length > 1) {
            BlogAttachFile.setFileType(split[split.length - 1]);
        }
        BlogAttachFile.setFileUrl(path);
        BlogAttachFile.setMd5(md5);
        BlogAttachFile.setFileSize(String.valueOf(bytes.length));
        fileMapper.insert(BlogAttachFile);
        return StringUtils.isEmpty(path) ? ResultSet.error("上传失败") : ResultSet.success("上传成功", path);
    }

    @Override
    public void downloadFile(String path, HttpServletResponse response) {
        WebUtil.downloadFile(path, response, fastdfsUtil);
    }

}

