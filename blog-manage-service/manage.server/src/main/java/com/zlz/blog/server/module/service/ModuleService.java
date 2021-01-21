package com.zlz.blog.server.module.service;

import com.zlz.blog.common.entity.module.Module;
import com.zlz.blog.common.response.PageInfo;
import com.zlz.blog.common.response.ResultSet;

import javax.servlet.http.HttpServletRequest;

/**
 * created by zlz on 2020/12/21 9:58
 **/
public interface ModuleService {

    /**
     * 分页查询，模糊查询
     * @param module
     * @return
     */
    ResultSet<PageInfo<Module>> getPageList(Module module);

    /**
     * 新增module
     * @param module
     * @return
     */
    ResultSet<Module> createModule(HttpServletRequest request, Module module);
}
