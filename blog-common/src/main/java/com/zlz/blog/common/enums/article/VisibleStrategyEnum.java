package com.zlz.blog.common.enums.article;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-06-16 15:56
 * @description 可见策略 0 所有人 1 粉丝 2 付费
 */
public enum VisibleStrategyEnum {

    /**
     * 所有人
     */
    EVERYONE(0, "所有人"),

    /**
     * 粉丝
     */
    FANS(1, "粉丝"),

    /**
     * 付费
     */
    PAIED(2, "付费");
    private int code;
    private String name;


    VisibleStrategyEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static VisibleStrategyEnum getEnumByCode(Integer code) {
        if (null == code) {
            return null;
        }
        for (VisibleStrategyEnum visibleStrategyEnum : VisibleStrategyEnum.values()) {
            if (visibleStrategyEnum.getCode() == code) {
                return visibleStrategyEnum;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "VisibleStrategyEnum{" +
                "code=" + code +
                ", name='" + name + '\'' +
                '}';
    }
}
