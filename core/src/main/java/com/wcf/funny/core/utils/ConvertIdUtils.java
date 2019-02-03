package com.wcf.funny.core.utils;

import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wangcanfeng
 * @time 2019/2/3
 * @function 类型转换工具
 **/
public class ConvertIdUtils {
    /**
     * 功能描述：  将id的字符串改写成list
     *
     * @param info
     * @return java.util.List<java.lang.Integer>
     * @author wangcanfeng
     * @time 2019/1/31 22:48
     * @since v1.0
     **/
    public static List<Integer> getList(String info) {
        List<Integer> infoList = new ArrayList<>();
        if (!ObjectUtils.isEmpty(info)) {
            String[] array = info.split(",");
            Arrays.asList(array).forEach(obj -> infoList.add(Integer.valueOf(obj)));
        }
        return infoList;
    }

    /**
     * 功能描述：  将id的列表转成用逗号隔开的字符串
     *@author wangcanfeng
     *@time 2019/2/3 15:09
     *@since v1.0
     * @param list
     *@return java.lang.String
     **/
    public static String getString(List<Integer> list){
        if (ObjectUtils.isEmpty(list)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        list.forEach(role -> sb.append(role).append(","));
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
