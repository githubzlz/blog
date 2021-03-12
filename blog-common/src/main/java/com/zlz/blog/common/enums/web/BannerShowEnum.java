package com.zlz.blog.common.enums.web;

import com.zlz.blog.common.exception.BlogException;

/**
 * bannen是否展示
 * @author 12101
 */

public enum BannerShowEnum {

    /**
     * 展示
     */
    SHOW(1, "展示"),

    /**
     * 隐藏
     */
    HIDDEN(0, "隐藏");

    private Integer code;
    private String name;

    BannerShowEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    /**
     * 通过code获取枚举
     * @param code
     * @return
     */
    public static BannerShowEnum getBannerShowEnum(String code){
        if(null != code){
            for(BannerShowEnum typeEnum : BannerShowEnum.values()){
                if(typeEnum.getCode().equals(code)){
                    return typeEnum;
                }
            }
        }
        throw new BlogException("获取枚举异常，错误的code");
    }
}
