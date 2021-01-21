package com.zlz.blog.common.response;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.zlz.blog.common.entity.common.ExcludeItem;
import lombok.Data;

import java.util.List;

/**
 * 封装了分页数据
 *
 * @author zhulinzhong
 * @version 1.0 CreateTime:2019/10/21 10:26
 */
@Data
public class PageInfo<T> {

    /**
     * 当前页码
     */
    private Long pageNum;

    /**
     * 每页的数据数量
     */
    private Long pageSize;

    /**
     * 数据总量
     */
    private Long totalSize;

    /**
     * 按当前分页数量分页，共有的页数
     */
    private Long totalPageNum;

    /**
     * 排序字段
     */
    private List<OrderItem> orders;

    /**
     * 筛选字段
     */
    private List<ExcludeItem> exclude;

    /**
     * 封装查询到的信息
     */
    private List<T> list;

}
