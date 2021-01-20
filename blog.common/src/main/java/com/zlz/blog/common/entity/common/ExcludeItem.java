package com.zlz.blog.common.entity.common;

import lombok.Data;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-05-29 16:09
 * @description 筛选条件
 */
@Data
public class ExcludeItem {

    /**
     * 字段
     */
    private String column;

    /**
     * 条件
     */
    private String value;
}
