package com.zlz.blog.server.oauth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zlz.blog.common.entity.oauth.BlogWriteUrlList;
import com.zlz.blog.server.oauth.mapper.PermissionWriteUrlListMapper;
import com.zlz.blog.server.oauth.service.WriteUrlListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author peeterZ
 * @version 2.0 CreateTime:2021-01-27 11:22
 * @description
 */
@Service
public class WriteUrlListServiceImpl implements WriteUrlListService {

    @Resource
    private PermissionWriteUrlListMapper writeUrlListMapper;

    @Override
    public List<BlogWriteUrlList> getWriteUrlList() {
        QueryWrapper<BlogWriteUrlList> queryWrapper = new QueryWrapper<>();
        return writeUrlListMapper.selectList(queryWrapper);
    }
}
