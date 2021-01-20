package com.zlz.blog.common.util;

import com.zlz.blog.common.exception.BlogException;
import com.zlz.blog.common.response.ResultSet;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-05-26 11:06
 * @description 对执行sql返回结果的处理工具类
 */
public class SqlResultUtil {

    /**
     * sql执行影响的结果为一行返回成功的消息，否则返回失败的消息
     *
     * @param row row
     * @return ResultSet
     */
    public static ResultSet isOneRow(int row) {
        if (row == 1) {
            return ResultSet.success();
        }
        throw new BlogException("操作失败");
    }

    /**
     * sql执行影响的结果为一行返回成功的消息，否则返回失败的消息
     *
     * @param row
     * @param successMsg
     * @param failMsg
     * @return
     */
    public static ResultSet isOneRow(int row, String successMsg, String failMsg) {
        if (row == 1) {
            return ResultSet.success(successMsg);
        }
        return ResultSet.error(failMsg);
    }
}
