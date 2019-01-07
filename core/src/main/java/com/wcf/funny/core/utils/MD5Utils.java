package com.wcf.funny.core.utils;

import lombok.extern.log4j.Log4j2;

import java.security.MessageDigest;

/**
 * @author wangcanfeng
 * @time 2019/1/6
 * @function md5加密工具
 **/
@Log4j2
public class MD5Utils {
    /**
     * 密码加盐
     */
    private static final String SALT = "wcf";

    /**
     *@note 原密码加盐再加密
     *@author WCF
     *@time 2018/6/10 20:46
     *@since v1.0
     * @param password
     *@return java.lang.String
     **/
    public static String encode(String password) {
        password = password + SALT;
        return processEncode(password);
    }


    /**
     *@note 判断页面传输过来的密码是否有效
     *@author WCF
     *@time 2018/6/10 20:48
     *@since v1.0
     * @param DBPassword 页面的密码
     * @param authPassword 传入的密码
     *@return boolean
     **/
    public static boolean passwordValidation(String DBPassword, String authPassword){
        boolean flag = false;
        if(DBPassword.equals(authPassword)){
            flag = true;
        }
        return flag;
    }

    /**
     *@note 通过算法对密码进行实际加密操作
     *@author WCF
     *@time 2018/6/10 20:49
     *@since v1.0
     * @param password
     *@return java.lang.String
     **/
    private static String processEncode(String password) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            log.info("MD5 ："+e.toString());
        }
        byte[] md5Bytes = md5.digest(password.getBytes());
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            String hex = Integer.toHexString(0xff & md5Bytes[i]);
            if (hex.length()==1) {
                hexValue.append("0");
            }
            hexValue.append(hex);
        }
        return hexValue.toString();
    }
}
