package com.zlz.blog.common.util;

import com.zlz.blog.common.exception.BlogException;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 加密，解密工具类（AES）
 *
 * @author zhulinzhong
 * @version 1.0 CreateTime:2019/12/7 14:00
 */
@Slf4j
public class EncryptionUtil {

    private static final Integer TWO = 2;
    /**
     * 加密方法
     * @param sSrc 明文
     * @param sKey 密钥
     * @return 加密后的16进制字符串
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws UnsupportedEncodingException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     */
    public static String encrypt(String sSrc, String sKey) throws Exception {

        //非空检查
        if (sSrc == null || sKey == null) {
            throw new Exception("加密失败,数据或密码不能为空");
        }

        //密钥生成器初始化
        KeyGenerator kGen = KeyGenerator.getInstance("AES");
        kGen.init(128, new SecureRandom(sKey.getBytes()));
        SecretKeySpec sKeySpec = new SecretKeySpec(kGen.generateKey().getEncoded(), "AES");

        //"AES算法/分组密码模式/补码方式"
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));

        //转化为16进制返回
        return bytesToHex(encrypted);
    }

    /**
     * 解密方法
     * @param sSrc 密文
     * @param sKey 密钥
     * @throws Exception
     * @return
     */
    public static String decrypt(String sSrc, String sKey) throws Exception {

        // 判断Key是否正确
        if (sKey == null || sSrc == null) {
            throw new Exception("解密失败,数据或密码不能为空");
        }
        try {
            //获取密钥，用于Cipher的初始化
            KeyGenerator kGen = KeyGenerator.getInstance("AES");
            kGen.init(128, new SecureRandom(sKey.getBytes()));
            SecretKey secretKey = kGen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec sKeySpec = new SecretKeySpec(enCodeFormat, "AES");

            //初始化Cipher
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, sKeySpec);

            //转换为字节数组
            byte[] encrypted1 = hexToByteArray(sSrc);
            byte[] original = cipher.doFinal(encrypted1);

            return new String(original, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("解密失败");
            return null;
        }
    }

    /**
     * 使用md5，和盐值进行加密
     * @param password
     * @param salt
     * @return
     */
    public static String md5Encrypt(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(salt.getBytes());
            md.update(password.getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();
            return bytesToHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new BlogException("加密失败");
        }
    }

    /**
     * 将字节数组转为16进制字符串
     * @param bytes
     * @return
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if(hex.length() < TWO){
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 16进制转化为字节
     * @param inHex
     * @return
     */
    private static byte hexToByte(String inHex){
        return (byte)Integer.parseInt(inHex,16);
    }

    /**
     * 16进制字符串转换为字节数组
     * @param inHex
     * @return
     */
    private static byte[] hexToByteArray(String inHex){
        int hexLen = inHex.length();
        byte[] result;
        if (hexLen % TWO == 1){
            //奇数
            hexLen++;
            result = new byte[(hexLen/2)];
            inHex="0"+inHex;
        }else {
            //偶数
            result = new byte[(hexLen/2)];
        }
        int j=0;
        for (int i = 0; i < hexLen; i+=TWO){
            result[j]=hexToByte(inHex.substring(i,i+2));
            j++;
        }
        return result;
    }

}
