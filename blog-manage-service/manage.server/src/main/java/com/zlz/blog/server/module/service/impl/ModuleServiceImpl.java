package com.zlz.blog.server.module.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zlz.blog.common.entity.user.LoginUser;
import com.zlz.blog.common.entity.module.Module;
import com.zlz.blog.common.response.PageInfo;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.common.util.PageUtil;
import com.zlz.blog.common.util.SqlResultUtil;
import com.zlz.blog.common.util.TokenUtil;
import com.zlz.blog.server.module.mapper.ModuleMapper;
import com.zlz.blog.server.module.service.ModuleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * created by zlz on 2020/12/21 9:59
 **/
@Service
public class ModuleServiceImpl implements ModuleService {

    @Resource
    private ModuleMapper moduleMapper;

    @Override
    public ResultSet<PageInfo<Module>> getPageList(Module module) {

        if(null == module || null == module.getPageInfo()){
            return ResultSet.error("缺少查询参数");
        }

        PageInfo<Module> pageInfo = module.getPageInfo();
        IPage<Module> moduleIPage = moduleMapper.selectPage(PageUtil.getIPage(pageInfo), module);

        return ResultSet.success("分页查询成功", PageUtil.setPageInfo(moduleIPage, pageInfo));
    }

    @Override
    public ResultSet<Module> createModule(HttpServletRequest request, Module module) {

        LoginUser loginUser = TokenUtil.getLoginUser(request);

        if(null == module || null == loginUser){
            return ResultSet.error("缺少新增参数");
        }

        //补全创建信息
        Date now = new Date();
        module.setCreator(loginUser.getId());
        module.setCreatedTime(now);
        module.setLastModifier(loginUser.getId());
        module.setLastModifiedTime(now);

        //插入数据
        int insert = moduleMapper.insert(module);
        return SqlResultUtil.isOneRow(insert);
    }
}
