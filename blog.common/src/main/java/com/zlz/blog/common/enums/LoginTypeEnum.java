package com.zlz.blog.common.enums;

import com.zlz.blog.common.exception.BlogException;

public enum LoginTypeEnum {

    /**
     * 账号密码登录
     */
    PASSWORD("PASSWORD", "账号密码登录"),

    /**
     * 邮箱验证码登录
     */
    EMAIL_VERIFICATION_CODE("EMAIL_VERIFICATION_CODE", "邮箱验证码登录"),

    /**
     * 手机验证码登录
     */
    PHONE_VERIFICATION_CODE("PHONE_VERIFICATION_CODE", "手机验证码登录");

    private String code;
    private String name;

    LoginTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
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
    public static LoginTypeEnum getLoginTypeEnum(String code){
        if(null != code){
            for(LoginTypeEnum typeEnum : LoginTypeEnum.values()){
                if(typeEnum.getCode().equals(code)){
                    return typeEnum;
                }
            }
        }
        throw new BlogException("获取枚举异常，错误的code");
    }
}
