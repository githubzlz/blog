package com.zlz.blog.common.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zlz.blog.common.response.PageInfo;

/**
 * 分页实体类,与mybatis-plus分页信息IPage转换
 *
 * @author zhulinzhong
 * @version 1.0 CreateTime:2019/10/21 10:35
 */
public class PageUtil {

    /**
     * 将用PageInfo接收到的数据传递给IPage
     *
     * @param pageInfo 分页数据
     * @return IPage
     */
    public static <T> IPage<T> getIPage(PageInfo<T> pageInfo) {
        return new Page<>(pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    /**
     * 将IPage的信息给PageInfo返回
     *
     * @param iPage    iPage
     * @param pageInfo pageInfo
     * @return pageInfo
     */
    public static <T> PageInfo<T> setPageInfo(IPage<T> iPage, PageInfo<T> pageInfo) {

        PageInfo<T> newPageInfo = new PageInfo<>();
        newPageInfo.setPageSize(iPage.getSize());
        newPageInfo.setPageNum(iPage.getCurrent());
        newPageInfo.setTotalSize(iPage.getTotal());
        newPageInfo.setTotalPageNum(iPage.getPages());
        if (iPage.getRecords() != null) {
            newPageInfo.setList(iPage.getRecords());
        }
        return newPageInfo;
    }

}
