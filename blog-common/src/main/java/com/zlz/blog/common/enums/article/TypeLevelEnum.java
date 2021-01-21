package com.zlz.blog.common.enums.article;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-08 08:37
 * @description 文章类型层级枚举类
 */
public enum TypeLevelEnum {

    /**
     * 一级分类
     */
    ONE(1, "一级分类"),

    /**
     * 二级分类
     */
    TWO(2, "二级分类");
    private int code;
    private String name;


    TypeLevelEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static TypeLevelEnum getEnumByCode(Integer code) {
        if (null == code) {
            return null;
        }
        for (TypeLevelEnum typeLevelEnum : TypeLevelEnum.values()) {
            if (typeLevelEnum.getCode() == code) {
                return typeLevelEnum;
            }
        }
        return null;
    }
}
