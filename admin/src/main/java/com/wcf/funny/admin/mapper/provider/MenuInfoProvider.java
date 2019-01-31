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
            StringBuilder sb=new StringBuilder();
            if(!ObjectUtils.isEmpty(ids)){
                WHERE("id in ("+ids+")");
            }else {
                //设置一个根本不存在id，让他查不到
                WHERE("id in (-1)");
            }
        }}.toString();
        return sql;
    }
}
