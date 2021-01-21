package com.zlz.blog.common.template;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * created by zlz on 2021/1/4 19:21
 **/
@Data
public class EmailRedisTemplate implements Serializable {
    private static final long serialVersionUID = 8566117502988808434L;

    private String email;

    private Long userId;

    private String checkCode;

    private Date date;
}
