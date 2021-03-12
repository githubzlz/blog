package com.zlz.blog.server.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zlz.blog.common.entity.web.WebBanner;
import com.zlz.blog.common.enums.web.BannerShowEnum;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.common.util.SqlResultUtil;
import com.zlz.blog.server.web.mapper.WebBannerMapper;
import com.zlz.blog.server.web.service.WebBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author peeterZ
 * @version 2.0 CreateTime:2021-03-10 15:04
 */
@Service
public class WebBannerServiceImpl implements WebBannerService {

    private WebBannerMapper webBannerMapper;

    @Autowired
    public void setWebBannerMapper(WebBannerMapper webBannerMapper){
        this.webBannerMapper = webBannerMapper;
    }

    @Override
    public ResultSet<List<WebBanner>> getWebBanners(Integer numbers, Integer type) {

        // 默认一张
        numbers = Optional.ofNullable(numbers).orElse(1);
        // 默认轮播图banner
        type = Optional.ofNullable(type).orElse(0);
        QueryWrapper<WebBanner> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_show", BannerShowEnum.SHOW.getCode())
                .eq("type", type)
                .orderByAsc("priority")
                .last("limit " + numbers);
        return ResultSet.success("查询成功", webBannerMapper.selectList(queryWrapper));
    }

    @Override
    public ResultSet<List<WebBanner>> getWebBanners() {
        return ResultSet.success("查询成功", webBannerMapper.selectList(null));
    }

    @Override
    public ResultSet<String> insertBanner(WebBanner webBanner) {
        // 默认轮播图banner
        webBanner.setType(Optional.ofNullable(webBanner.getType()).orElse(1));
        int insert = webBannerMapper.insert(webBanner);
        return SqlResultUtil.isOneRow(insert, "新增banner成功", "新增banner失败");
    }

    @Override
    public ResultSet<String> updateBanner(WebBanner webBanner) {
        return null;
    }

}
