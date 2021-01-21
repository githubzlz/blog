package com.zlz.blog.common.enums;

/**
 * 排序方式枚举类
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020/2/7 11:57
 */
public enum SortMethodEnum {

    /**
     * 排序方式：最后修改时间
     */
    LAST_MODIFIED_TIME(1, "last_modified_time"),

    /**
     * 排序方式：创建修改时间
     */
    CREATED_TIME(2, "created_time");

    /**
     * key值
     */
    private Integer key;

    /**
     * value值
     */
    private String value;

    SortMethodEnum(Integer key, String value){
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
