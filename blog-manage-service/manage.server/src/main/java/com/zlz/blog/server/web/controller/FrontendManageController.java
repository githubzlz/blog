package com.zlz.blog.server.web.controller;

import com.zlz.blog.common.entity.web.WebBanner;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.server.web.service.WebBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 前端管理
 *
 * @author peeterZ
 * @version 2.0 CreateTime:2021-03-10 14:59
 */
@RestController
@RequestMapping("/front")
public class FrontendManageController {

    private WebBannerService webBannerService;

    @Autowired
    public void setWebBannerService(WebBannerService webBannerService){
        this.webBannerService = webBannerService;
    }

    /**
     * 获取前端展示的banner
     * @param numbers
     * @param type
     * @return
     */
    @GetMapping("/show/banner/{numbers}/{type}")
    public ResultSet<List<WebBanner>> getWebBanners(@PathVariable("numbers") Integer numbers,
                                                    @PathVariable("type") Integer type){
        return webBannerService.getWebBanners(numbers, type);
    }

    /**
     * 管理端查询banner列表
     * @return
     */
    @PostMapping("/manage/select/banner")
    public ResultSet<List<WebBanner>> getWebBannersAll(){
        return webBannerService.getWebBanners();
    }

    /**
     * 管理端新增banner
     * @return
     */
    @PostMapping("/manage/insert/banner")
    public ResultSet<String> insertBanner(@RequestBody WebBanner webBanner){
        return webBannerService.insertBanner(webBanner);
    }

    /**
     * 管理端更改banner信息
     * @return
     */
    @PostMapping("/manage/update/banner")
    public ResultSet<String> updateBanner(@RequestBody WebBanner webBanner){
        return webBannerService.updateBanner(webBanner);
    }


}
