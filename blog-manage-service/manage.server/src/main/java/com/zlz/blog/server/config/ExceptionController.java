package com.zlz.blog.server.config;

import com.zlz.blog.common.response.ResultSet;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-06-06 09:17
 * @description 全局统一异常处理
 */
@ControllerAdvice
@RestController
@Log4j2
public class ExceptionController {

    @ExceptionHandler(value = Exception.class)
    public ResultSet exceptionHandler(Exception e) {
        log.error("发生异常:", e);
        return ResultSet.error(e.getMessage());
    }
}
