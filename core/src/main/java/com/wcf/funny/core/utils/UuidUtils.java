package com.wcf.funny.core.utils;

import java.util.UUID;

/**
 * @author WCF
 * @time 2018/5/22
 * @why 生成uuid
 **/
public class UuidUtils {
    /**
     * 大小写字母
     */
    public static String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    /**
     * @param
     * @return java.lang.String
     * @note 获取8位的uuid
     * @author WCF
     * @time 2018/6/10 20:54
     * @since v1.0
     **/
    public static String generateShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();
    }

    /**
     *@note 生成32位
     *@author WCF
     *@time 2018/6/24 11:13
     *@since v1.0
     * @param
     *@return java.lang.String
     **/
    public static String generateUuid() {
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        return uuid;
    }
}
