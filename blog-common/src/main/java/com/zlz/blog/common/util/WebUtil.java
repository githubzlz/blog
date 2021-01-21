package com.zlz.blog.common.util;

import com.zlz.blog.common.constants.CommonConstants;
import com.zlz.fastdfs.util.FastdfsUtil;
import lombok.extern.slf4j.Slf4j;
import org.csource.common.MyException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2019/12/20 18:12
 */
@Slf4j
public class WebUtil {

    /**
     * 文件上传方法
     *
     * @param bytes 上传的文件
     * @return 操作的消息
     */
    public static String uploadFile(byte[] bytes, String fileName, FastdfsUtil fastdfsUtil) {
        String filePath = "";
        if (bytes.length != 0) {
            try {
                log.info("文件大小[{}]", bytes.length);
                String url = fastdfsUtil.uploadFile(bytes, fileName);
                filePath = CommonConstants.FASTDFS_ADDRESS + "/" + url;
                log.info("文件上传成功，文件地址:" + url);
            } catch (IOException | MyException e ) {
                log.error("文件上传异常，上传失败", e);
                return filePath;
            }
        }
        return filePath;
    }



    /**
     * 下载文件的方法(将文件写到输出流)
     *
     * @param fileName fileName
     * @param data     数据的字节数组
     * @param response response
     */
    public static void downloadFile(String fileName, byte[] data, HttpServletResponse response) {

        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/x-msdownload");
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            response.setContentLength(data.length);
            OutputStream out = response.getOutputStream();
            out.write(data);
            out.close();
        } catch (IOException e) {
            log.error("文件下载异常，下载失败[{}]", e);
        }
    }

    /**
     * 下载文件的方法(将文件写到输出流)
     *
     * @param path path
     */
    public static void downloadFile(String path, HttpServletResponse response, FastdfsUtil fastdfsUtil) {
        try {
            byte[] bytes = fastdfsUtil.downLoadFile(path);
            response.getOutputStream().write(bytes);
        } catch (IOException | MyException e) {
            log.error("文件下载异常,下载失败,文件地址：" + path);
            e.printStackTrace();
        }
    }
}
