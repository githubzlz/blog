package com.zlz.blog.common.util;

import com.zlz.blog.common.enums.LoginTypeEnum;
import org.springframework.util.StringUtils;

import java.util.Random;

/**
 * 验证码工具类
 * created by zlz on 2021/1/4 19:48
 **/
public class CheckCodeUtil {

    private static Random random = new Random();

    /**
     * 生成邮箱验证码
     * @return
     */
    public static String geneEmailCheckCode(){
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<6;i++){
            builder.append(random.nextInt(10));
        }
        return builder.toString();
    }

    /**
     * 获取密码拼接串（EMAIL）
     * @param password
     * @return
     */
    public static String geneEmailPassWord(String password){
        return LoginTypeEnum.EMAIL_VERIFICATION_CODE.getCode() + ":" + password;
    }

    /**
     * 获取真实密码
     * @param password
     * @return
     */
    public static String getRealPassWord(String password){
        String[] arr = password.split(":");
        if(arr.length != 2){
            return null;
        }
        return arr[1];
    }

    /**
     * 获取登录类型
     * @param password
     * @return
     */
    public static LoginTypeEnum getLoginType(String password){
        String[] arr = password.split(":");
        if(arr.length != 2){
            return null;
        }
        if(StringUtils.isEmpty(arr[0])){
            return null;
        }
        return LoginTypeEnum.getLoginTypeEnum(arr[0]);
    }

    /**
     * 获取密码拼接串（PASSWORD）
     * @param password
     * @return
     */
    public static String genePassWord(String password){
        return LoginTypeEnum.PASSWORD.getCode() + ":" + password;
    }
}
