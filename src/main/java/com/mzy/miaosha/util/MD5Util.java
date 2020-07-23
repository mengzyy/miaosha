package com.mzy.miaosha.util;


import org.apache.commons.codec.digest.DigestUtils;

/**
 * MD5工具包
 */
public class MD5Util {
    /*用户表单固定的salt值*/
    private static final String salt = "1a2b3c4d";


    public static String md5(String str) {
        return DigestUtils.md5Hex(str);
    }

    /**
     * md5(用户表单的密码+固定salt)
     *
     * @param inputPass 表单的传参
     * @return 网络中传输的md5值
     */
    public static String inputPassToFormPass(String inputPass) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        System.out.println(str);
        return md5(str);
    }

    /**
     * 入库的md5值
     *
             * @param formPass 表单md5后的值
     * @param salt     随机salt值：需要入库
     * @return 入库md5
     */
    public static String formPassToDBPass(String formPass, String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    /**
     * 模拟全过程
     *
     * @param inputPass 用户pwd
     * @param saltDB    随机salt
     * @return 入库md5
     */
    public static String inputPassToDbPass(String inputPass, String saltDB) {
        String formPass = inputPassToFormPass(inputPass);
        return formPassToDBPass(formPass, saltDB);
    }


}
