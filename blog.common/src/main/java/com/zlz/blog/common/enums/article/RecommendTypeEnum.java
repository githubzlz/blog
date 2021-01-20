package com.zlz.blog.common.enums.article;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-08-11 14:51
 * @description 推荐方式
 */
public enum RecommendTypeEnum {

    /**
     * 侧边栏推荐
     */
    SIDE(0, "侧边栏推荐"),

    /**
     * 主页推荐
     */
    HOMEPAGE(1, "主页推荐");

    private int code;
    private String name;

    RecommendTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static RecommendTypeEnum getEnumByCode(Integer code) {
        if (null == code) {
            return null;
        }
        for (RecommendTypeEnum recommendtypeenum : RecommendTypeEnum.values()) {
            if (recommendtypeenum.getCode() == code) {
                return recommendtypeenum;
            }
        }
        return null;
    }
}
