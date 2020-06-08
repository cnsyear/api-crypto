package com.cnsyear.apicrypto.util;

import cn.hutool.crypto.SecureUtil;

/**
 * @Description DESede是由DES对称加密算法改进后的一种对称加密算法。
 * 使用 168 位的密钥对资料进行三次加密的一种机制；它通常（但非始终）提供极其强大的安全性。
 * 如果三个 56 位的子元素都相同，则三重 DES 向后兼容 DES。
 * @Author jie.zhao
 * @Date 2020/6/8 21:49
 */
public class DesedeUtil {

    /**
     * 加密
     *
     * @param key
     * @param content
     * @return
     */
    public static String encryptHex(String key, String content) {
        String encryptHex = SecureUtil.desede(key.getBytes()).encryptHex(content);
        return encryptHex;
    }

    /**
     * 解密
     *
     * @param key
     * @param encryptHex
     * @return
     */
    public static String decryptStr(String key, String encryptHex) {
        String decryptStr = SecureUtil.desede(key.getBytes()).decryptStr(encryptHex);
        return decryptStr;
    }

    public static void main(String[] args) {
        //24位
        String key = "kCXQjT6gM6O2LZtFJdiloTHz";
        String value = "zhaojie";

        String encryptStr = DesedeUtil.encryptHex(key,value);
        System.out.println(encryptStr);
        String decryptStr = DesedeUtil.decryptStr(key,encryptStr);
        System.out.println(decryptStr);

    }

}

