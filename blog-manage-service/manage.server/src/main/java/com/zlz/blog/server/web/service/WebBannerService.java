package com.zlz.blog.server.web.service;

import com.zlz.blog.common.entity.web.WebBanner;
import com.zlz.blog.common.response.ResultSet;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2021-03-10 15:04
 * @description banner service层
 */
public interface WebBannerService {

    /**
     * 获取前端展示的banner
     * @param numbers
     * @param type
     * @return
     */
    ResultSet<List<WebBanner>> getWebBanners(Integer numbers, Integer type);

    /**
     * 获取所有的banner
     * @return
     */
    ResultSet<List<WebBanner>> getWebBanners();

    /**
     * 更改banner
     * @param webBanner
     * @return
     */
    ResultSet<String> updateBanner(WebBanner webBanner);

    /**
     * 新增banner
     * @param webBanner
     * @return
     */
    ResultSet<String> insertBanner(WebBanner webBanner);
}
