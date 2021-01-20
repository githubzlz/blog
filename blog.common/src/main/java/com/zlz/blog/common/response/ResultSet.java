package com.zlz.blog.common.response;

import com.zlz.blog.common.enums.ResultCodeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2019/10/21 9:59
 */
@Data
public class ResultSet<T> implements Serializable {

    private static final long serialVersionUID = -8110766448154376759L;
    /**
     * 返回的消息
     */
    private String message;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回的实体
     */
    private T entity;

    private ResultSet(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    private ResultSet(String message, Integer code, T entity) {
        this.message = message;
        this.code = code;
        this.entity = entity;
    }

    /**
     * 返回成功的消息
     *
     * @return ResultSet
     */
    public static ResultSet success() {
        return new ResultSet(ResultCodeEnum.SUCCESS.getMsg(), ResultCodeEnum.SUCCESS.getCode());
    }

    /**
     * 返回成功的消息
     *
     * @param msg msg
     * @return entity
     */
    public static ResultSet success(String msg) {
        return new ResultSet(msg, ResultCodeEnum.SUCCESS.getCode());
    }

    /**
     * 返回成功的消息
     *
     * @param entity entity
     * @return ResultSet
     */
    public static <T>  ResultSet success(T entity) {
        return new ResultSet<>(ResultCodeEnum.SUCCESS.getMsg(), ResultCodeEnum.SUCCESS.getCode(), entity);
    }

    /**
     * 返回成功的消息数据
     *
     * @return ResultSet
     */
    public static <T> ResultSet success(String msg, T entity) {
        return new ResultSet<>(msg, ResultCodeEnum.SUCCESS.getCode(), entity);
    }

    /**
     * 返回失败的消息
     *
     * @return ResultSet
     */
    public static ResultSet error() {
        return new ResultSet(ResultCodeEnum.ERROR.getMsg(), ResultCodeEnum.ERROR.getCode());
    }

    /**
     * 返回失败的消息
     *
     * @return ResultSet
     */
    public static ResultSet error(String msg) {
        return new ResultSet(msg, ResultCodeEnum.ERROR.getCode());
    }

    /**
     * 返回失败的消息数据
     *
     * @param entity entity
     * @return ResultSet
     */
    public static <T> ResultSet error(T entity) {
        return new ResultSet<>(ResultCodeEnum.ERROR.getMsg(), ResultCodeEnum.ERROR.getCode(), entity);
    }

    /**
     * 返回失败的消息数据
     *
     * @param msg    msg
     * @param entity entity
     * @return ResultSet
     */
    public static <T> ResultSet error(String msg, T entity) {
        return new ResultSet<>(msg, ResultCodeEnum.ERROR.getCode(), entity);
    }

    /**
     * 由于输入参数问题返回的消息
     *
     * @return ResultSet
     */
    public static ResultSet inputError() {
        return new ResultSet(ResultCodeEnum.INPUT_ERROR.getMsg(), ResultCodeEnum.INPUT_ERROR.getCode());
    }

    /**
     * 由于输入参数问题返回的消息
     *
     * @param msg msg
     * @return ResultSet
     */
    public static ResultSet inputError(String msg) {
        return new ResultSet(msg, ResultCodeEnum.INPUT_ERROR.getCode());
    }

    /**
     * 由于输入参数问题返回的消息
     *
     * @return ResultSet
     */
    public static <T> ResultSet inputError(T entity) {
        return new ResultSet<>(ResultCodeEnum.INPUT_ERROR.getMsg(), ResultCodeEnum.INPUT_ERROR.getCode(), entity);
    }

    /**
     * 由于输入参数问题返回的消息
     *
     * @return ResultSet
     */
    public static <T> ResultSet inputError(String msg, T entity) {
        return new ResultSet<>(msg, ResultCodeEnum.INPUT_ERROR.getCode(), entity);
    }

    /**
     * 由于查询结果问题返回的消息
     *
     * @return ResultSet
     */
    public static ResultSet outError() {
        return new ResultSet(ResultCodeEnum.OUTPUT_ERROR.getMsg(), ResultCodeEnum.OUTPUT_ERROR.getCode());
    }

    /**
     * 由于查询结果问题返回的消息
     *
     * @return ResultSet
     */
    public static ResultSet outError(String msg) {
        return new ResultSet(msg, ResultCodeEnum.OUTPUT_ERROR.getCode());
    }

    /**
     * 由于查询结果问题返回的消息
     *
     * @return ResultSet
     */
    public static <T> ResultSet outError(T entity) {
        return new ResultSet<>(ResultCodeEnum.OUTPUT_ERROR.getMsg(), ResultCodeEnum.OUTPUT_ERROR.getCode(), entity);
    }

    /**
     * 由于查询结果问题返回的消息
     *
     * @return ResultSet
     */
    public static <T> ResultSet outError(String msg, T entity) {
        return new ResultSet<>(msg, ResultCodeEnum.OUTPUT_ERROR.getCode(), entity);
    }

    /**
     * 检查返回的是否是成功的消息
     *
     * @param resultSet resultSet
     * @return boolean
     */
    public static boolean isSuccess(ResultSet resultSet) {
        return null != resultSet && ResultCodeEnum.SUCCESS.getCode().equals(resultSet.getCode());
    }

    /**
     * 登陆验证失败
     * @return
     */
    public static ResultSet loginError(String msg) {
        return new ResultSet<>(msg, ResultCodeEnum.LOGIN_ERROR.getCode());
    }

    /**
     * 鉴权失败,权限不足
     * @return
     */
    public static ResultSet unauthorizedError(String msg) {
        return new ResultSet<>(msg, ResultCodeEnum.UNAUTHORIZED_ERROR.getCode());
    }

    @Override
    public String toString() {
        return "ResultSet{" +
                "message='" + message + '\'' +
                ", code=" + code +
                ", entity=" + entity +
                '}';
    }
}
