package com.zlz.blog.common.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-01 16:48
 * @description 树形结构返回
 */
@Data
public class TreeNode<T> implements Serializable {

    private static final long serialVersionUID = -3850073929685266028L;

    /**
     * id
     */
    private Long id;

    /**
     * 父级id
     */
    private Long pId;

    /**
     * 名称
     */
    private String name;

    /**
     * 本级树的数据
     */
    private T data;

    /**
     * 子级数据
     */
    private List<TreeNode<T>> children;

}
