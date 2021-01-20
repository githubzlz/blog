package com.zlz.blog.common.exception;

/**
 * 本系统某些操作可能需要抛出的异常
 * @author zhulinzhong
 * @version 1.0 CreateTime:2019/12/20 17:59
 */
public class BlogException extends RuntimeException {
    public BlogException() {
    }

    public BlogException(String message) {
        super(message);
    }

    public BlogException(String message, Throwable cause) {
        super(message, cause);
    }

    public BlogException(Throwable cause) {
        super(cause);
    }

    public BlogException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
