package com.zlz.blog.common.response;

import lombok.Data;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020/1/13 11:38
 */
@Data
public class FileUploadResponse {

    /**
     * 状态码
     */
    private Integer success;

    /**
     * 消息
     */
    private String message;

    /**
     * 路径
     */
    private String url;

    /**
     * iframe的id
     */
    private String guid;


}
