package com.zlz.blog.server.module.service.impl;

import com.zlz.blog.common.entity.user.LoginUser;
import com.zlz.blog.common.entity.module.ModuleMenu;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.common.util.SqlResultUtil;
import com.zlz.blog.common.util.TokenUtil;
import com.zlz.blog.server.module.mapper.ModuleMenuMapper;
import com.zlz.blog.server.module.service.ModuleMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 模块菜单
 * created by zlz on 2020/12/22 19:10
 **/
@Service
public class ModuleMenuServiceImpl implements ModuleMenuService {

    @Resource
    private ModuleMenuMapper moduleMenuMapper;

    @Override
    public ResultSet<ModuleMenu> addModuleMenu(ModuleMenu moduleMenu, HttpServletRequest request) {

        LoginUser loginUser = TokenUtil.getLoginUser(request);
        if(null == moduleMenu || null == loginUser){
            return ResultSet.error("新增参数错误");
        }

        //补全创建信息
        Date now = new Date();
        moduleMenu.setCreator(loginUser.getId());
        moduleMenu.setCreatedTime(now);
        moduleMenu.setLastModifier(loginUser.getId());
        moduleMenu.setLastModifiedTime(now);

        int insert = moduleMenuMapper.insert(moduleMenu);
        return SqlResultUtil.isOneRow(insert);
    }
}
