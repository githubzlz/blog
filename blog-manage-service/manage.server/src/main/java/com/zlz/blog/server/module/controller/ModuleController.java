package com.zlz.blog.server.module.controller;

import com.zlz.blog.common.entity.module.Module;
import com.zlz.blog.common.response.PageInfo;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.server.module.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * created by zlz on 2020/12/21 9:56
 **/
@RestController
@RequestMapping("/module")
public class ModuleController {

    private ModuleService moduleService;
    @Autowired
    public void setModuleService(ModuleService moduleService){
        this.moduleService = moduleService;
    }

    /**
     * 分页查询列表
     * @param module
     * @return
     */
    @PostMapping("/list")
    public ResultSet<PageInfo<Module>> getPageList(@RequestBody Module module){
        return moduleService.getPageList(module);
    }

    /**
     * 新增
     * @param module
     * @param request
     * @return
     */
    @PostMapping("/create")
    public ResultSet<Module> createModule(@RequestBody Module module, HttpServletRequest request){
        return moduleService.createModule(request, module);
    }
}
