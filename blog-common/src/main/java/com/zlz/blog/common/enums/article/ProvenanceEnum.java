package com.zlz.blog.common.enums.article;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-06-16 15:45
 * @description 文章出处
 */
public enum ProvenanceEnum {

    /**
     * 原创
     */
    ORIGINAL(0, "原创"),

    /**
     * 转载
     */
    TRANSPOND(1, "转载"),

    /**
     * 翻译
     */
    TRANSLATE(2, "翻译");

    private int code;
    private String name;


    ProvenanceEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static ProvenanceEnum getEnumByCode(Integer code) {
        if (null == code) {
            return null;
        }
        for (ProvenanceEnum provenanceEnum : ProvenanceEnum.values()) {
            if (provenanceEnum.getCode() == code) {
                return provenanceEnum;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "ProvenanceEnum{" +
                "code=" + code +
                ", name='" + name + '\'' +
                '}';
    }

}
