package com.zlz.blog.server.module.service;

import com.zlz.blog.common.entity.module.ModuleMenu;
import com.zlz.blog.common.response.ResultSet;

import javax.servlet.http.HttpServletRequest;

/**
 * created by zlz on 2020/12/22 19:10
 **/
public interface ModuleMenuService {

    /**
     * 创建模块的菜单
     * @param moduleMenu
     * @param request
     * @return
     */
    ResultSet<ModuleMenu> addModuleMenu(ModuleMenu moduleMenu, HttpServletRequest request);
}
