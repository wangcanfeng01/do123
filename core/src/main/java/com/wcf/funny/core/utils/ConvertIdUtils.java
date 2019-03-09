package com.wcf.funny.core.utils;

import com.wcf.funny.core.constant.CoreConstant;
import com.wcf.funny.core.entity.CodeAndName;
import com.wcf.funny.core.entity.NameAndType;
import org.springframework.util.ObjectUtils;

import java.util.*;

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
     *
     * @param list
     * @return java.lang.String
     * @author wangcanfeng
     * @time 2019/2/3 15:09
     * @since v1.0
     **/
    public static String getString(List<Integer> list) {
        if (ObjectUtils.isEmpty(list)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        list.forEach(role -> sb.append(role).append(","));
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * 功能描述：  将关键字的列表转成用逗号隔开的字符串
     *
     * @param list
     * @return java.lang.String
     * @author wangcanfeng
     * @time 2019/2/3 15:09
     * @since v1.0
     **/
    public static String getKeywordString(List<String> list) {
        if (ObjectUtils.isEmpty(list)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        list.forEach(keyword -> sb.append(keyword).append(","));
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * 功能描述：  将中间用逗号隔开的字符串转成列表形式
     *
     * @param
     * @author wangcanfeng
     * @time 2019/2/23 16:55
     * @since v1.0
     **/
    public static List<String> getStringList(String info) {
        List<String> infoList = new ArrayList<>();
        if (!ObjectUtils.isEmpty(info)) {
            String[] array = info.split(",");
            Arrays.asList(array).forEach(obj -> infoList.add(obj));
        }
        return infoList;
    }

    /**
     * 功能描述: 将ids串列表，合并去重后转成单个ids串
     *
     * @param idsList
     * @return:java.lang.String
     * @since: v1.0
     * @Author:wangcanfeng
     * @Date: 2019/3/2 15:49
     */
    public static String getUniqMenuIds(List<String> idsList) {
        StringBuilder sb = new StringBuilder();
        // 对菜单id做去重
        HashSet<Integer> menuIds = new HashSet<>();
        idsList.forEach(menusString -> {
            getList(menusString).forEach(menuId -> menuIds.add(menuId));
        });
        menuIds.forEach(id -> sb.append(id).append(","));
        if (sb.length() > 0) {
            //去掉最后一个逗号
            sb.deleteCharAt(sb.length() - 1);
        } else {
            sb.append(CoreConstant.NULL_MENU_ID);
        }
        return sb.toString();
    }

    /**
     * 功能描述：  list转换成map
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/2 22:04
     * @since v1.0
     **/
    public static Map<String, String> convertListToMap(List<CodeAndName> list) {
        Map<String, String> menuMap = new HashMap<>();
        list.forEach(codeAndName -> menuMap.put(codeAndName.getCode(), codeAndName.getName()));
        return menuMap;
    }

    /**
     * 功能描述： 将标签转成编号和名称的列表
     *
     * @param tags
     * @author wangcanfeng
     * @time 2019/3/9 18:13
     * @since v1.0
     **/
    public static List<NameAndType> convertTagsToList(String tags) {
        if (ObjectUtils.isEmpty(tags)) {
            return Collections.emptyList();
        }
        String[] arr = tags.split(";");
        List<NameAndType> list = new ArrayList<>();
        for (String tag : arr) {
            NameAndType nameAndType = new NameAndType();
            String[] info = tag.split(":");
            nameAndType.setName(info[0]);
            nameAndType.setType(info[1]);
            list.add(nameAndType);
        }
        return list;
    }

    /**
     * 功能描述： 将tag列表转成字符串
     *@author wangcanfeng
     *@time 2019/3/9 19:58
     *@since v1.0
     * @param list
     **/
    public static String convertListToTags(List<NameAndType> list) {
        if(ObjectUtils.isEmpty(list)){
            return "";
        }
        StringBuilder sb=new StringBuilder();
        list.forEach(nameAndType -> {
            sb.append(nameAndType.getName()).append(":").append(nameAndType.getType()).append(";");
        });
        // 去除最后一个分号
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
