package com.zlz.blog.server.oauth.service;

import com.zlz.blog.common.entity.oauth.BlogWriteUrlList;

import java.util.List;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2021-01-27 11:22
 * @description
 */
public interface WriteUrlListService {

    /**
     * 获取路由白名单
     * @return
     */
    List<BlogWriteUrlList> getWriteUrlList();
}
