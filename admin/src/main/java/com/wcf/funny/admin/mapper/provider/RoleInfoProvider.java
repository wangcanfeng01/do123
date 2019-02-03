package com.wcf.funny.admin.mapper.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.ObjectUtils;

/**
 * @author wangcanfeng
 * @time 2019/2/3
 * @function 角色查询辅助sql生成器
 **/
public class RoleInfoProvider {

    /**
     * 功能描述：  查询简单的角色信息的sql
     *@author wangcanfeng
     *@time 2019/2/3 14:06
     *@since v1.0
     * @param ids
     *@return java.lang.String
     **/
    public String simpleRoleByIdsSQL(@Param("ids") String ids) {
        String sql = new SQL() {{
            SELECT("id, role_name as roleName, role_type as roleType");
            FROM("info_role");
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
