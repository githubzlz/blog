package com.zlz.blog.server.module.controller;

import com.zlz.blog.common.entity.module.ModuleMenu;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.server.module.service.ModuleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * created by zlz on 2020/12/22 19:12
 **/
@RestController
@RequestMapping("/module/menu")
public class ModuleMenuController {

    private ModuleMenuService moduleMenuService;

    @Autowired
    public void setModuleMenuService(ModuleMenuService moduleMenuService){
        this.moduleMenuService = moduleMenuService;
    }

    @PostMapping("/add")
    public ResultSet addModuleMenu(@RequestBody ModuleMenu moduleMenu, HttpServletRequest request){
        return moduleMenuService.addModuleMenu(moduleMenu, request);
    }
}
