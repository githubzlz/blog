package com.zlz.blog.server.tag.controller;

import com.zlz.blog.common.entity.tag.Tag;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.server.tag.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 标签管理
 *
 * @author peeterZ
 * @version 2.0 CreateTime:2021-02-04 16:54
 */
@RestController
@RequestMapping("/tag")
public class TagController {

    private TagService tagService;

    @Autowired
    public void setTagService(TagService tagService){
        this.tagService = tagService;
    }

    /**
     * 查询标签列表
     * @param tag
     * @return
     */
    @PostMapping("/me/query/list")
    public ResultSet<List<Tag>> queryTagList(@RequestBody Tag tag){
        return tagService.queryTagList(tag);
    }

    /**
     * 创建新的标签
     * @param tags
     * @return
     */
    @PostMapping("/me/create")
    public ResultSet<List<Tag>> createTag(@RequestBody List<Tag> tags){
        return tagService.createTag(tags);
    }
}
