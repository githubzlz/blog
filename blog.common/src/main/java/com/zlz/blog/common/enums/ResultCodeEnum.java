package com.zlz.blog.common.enums;

/**
 * 返回状态枚举类
 */
public enum ResultCodeEnum {

    /**
     * 成功
     */
    SUCCESS(1, "成功"),

    /**
     * 失败
     */
    ERROR(-1, "失败,请联系管理员"),

    /**
     * 输入参数错误
     */
    INPUT_ERROR(-2, "参数异常，请输入正确的参数"),

    /**
     * 查询异常
     */
    OUTPUT_ERROR(-3, "服务器异常，请重新尝试"),

    /**
     * 登陆失败
     */
    LOGIN_ERROR(401, "登陆失败,用户名或密码错误"),

    /**
     * 鉴权失败
     */
    UNAUTHORIZED_ERROR(403, "权限不足,请切换账号");

    /**
     * 代码
     */
    private Integer code;

    /**
     * 消息
     */
    private String msg;

    ResultCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
