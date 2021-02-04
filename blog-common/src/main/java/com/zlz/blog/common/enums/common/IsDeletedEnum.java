package com.zlz.blog.common.enums.common;

/**
 * 是否删除
 *
 * @author peeterZ
 * @version 2.0 CreateTime:2021-02-04 11:08
 */
public enum IsDeletedEnum {
    /**
     * 已删除
     */
    DELETED_ENUM(1, "已删除"),
    /**
     * 未删除
     */
    IS_NOT_DELETED_ENUM(0, "未删除");

    private Integer code;
    private String name;

    private IsDeletedEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
