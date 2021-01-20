package com.zlz.blog.common.enums.article;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-08 09:44
 * @description 操作类型
 */
public enum OperateTypeEnum {

    /**
     * 管理列表
     */
    MANAGEMENT(0, "管理列表"),

    /**
     * 使用列表
     */
    USE(1, "使用列表");
    private int code;
    private String name;


    OperateTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static OperateTypeEnum getEnumByCode(Integer code) {
        if (null == code) {
            return null;
        }
        for (OperateTypeEnum enum1 : OperateTypeEnum.values()) {
            if (enum1.getCode() == code) {
                return enum1;
            }
        }
        return null;
    }
}
