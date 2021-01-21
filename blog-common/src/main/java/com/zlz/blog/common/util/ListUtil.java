package com.zlz.blog.common.util;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 将集合元素组装成为字符串
 *
 * @author zhulinzhong
 * @version 1.0 CreateTime:2019/12/21 14:44
 */
public class ListUtil {

    private static final String SPLIT = ",";

    /**
     * 将数组转化成为“ ,”(英文)分割的字符串
     *
     * @param list
     * @return
     */
    public static String toString(List<String> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (String str : list) {
            builder.append(str);
            builder.append(SPLIT);
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    /**
     * 将由“ ,”(英文)分割的字符串转化成数组
     *
     * @param str
     * @return
     */
    public static List<String> toList(String str) {
        if (StringUtils.isEmpty(str)) {
            return new ArrayList<>();
        }
        return Arrays.asList(str.split(SPLIT));
    }

}
