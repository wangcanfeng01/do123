package com.wcf.funny.admin.mapper.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.ObjectUtils;

/**
 * @author wangcanfeng
 * @time 2019/1/31
 * @function 菜单信息的sql生成工具类
 **/
public class MenuInfoProvider {

    public String simpleMenuByIdsSQL(@Param("ids") String ids) {
        String sql = new SQL() {{
            SELECT("id, menu_name as menuName, path as menuPath");
            FROM("info_menu");
            if (!ObjectUtils.isEmpty(ids)) {
                //查询到指定id对应的菜单以及不需要权限的菜单
                WHERE("id in (" + ids + ") OR need_auth=0");
            } else {
                //设置一个根本不存在id，让他查不到
                WHERE("id in (-1) OR need_auth=0");
            }
        }}.toString();
        return sql;
    }

    public String simpleMenuWithAuthByIdsSQL(@Param("ids") String ids) {
        String sql = new SQL() {{
            SELECT("id, menu_name as menuName, path as menuPath");
            FROM("info_menu");
            if (!ObjectUtils.isEmpty(ids)) {
                //查询到指定id对应的菜单
                WHERE("id in (" + ids + ")");
            } else {
                //设置一个根本不存在id，让他查不到
                WHERE("id in (-1)");
            }
        }}.toString();
        return sql;
    }


    public String selectMenuMapSQL(@Param("ids") String ids, @Param("level") Integer level) {
        String sql = new SQL() {{
            SELECT("menu_name as name, menu_code as code");
            FROM("info_menu");
            //查询到指定id对应的菜单
            WHERE("id in (" + ids + ") and menu_level=#{level} or (need_auth=0 and menu_level=#{level})");
        }}.toString();
        return sql;
    }

}
