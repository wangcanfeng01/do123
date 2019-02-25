package com.wcf.funny.core.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangcanfeng
 * @time 2019/2/25
 * @function 存放图片类型和图片类型名称的键值
 **/
public class PictureTypeMap {
    private final static Map<Integer, String> typeMap = new HashMap<>();

    public static String getName(Integer type) {
        // 如果还没有赋初值，则调用赋值方法
        if(typeMap.size()==0){
            setTypeMap();
        }
        return typeMap.get(type);
    }

    private static void setTypeMap() {
        typeMap.put(0,"文章封面");
        typeMap.put(1,"专题封面");
        typeMap.put(2,"文章内容");
        typeMap.put(3,"人脸图片");
    }
}
