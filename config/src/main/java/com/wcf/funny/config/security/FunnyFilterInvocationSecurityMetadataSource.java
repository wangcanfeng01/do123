package com.wcf.funny.config.security;

import com.wcf.funny.admin.entity.UserRole;
import com.wcf.funny.admin.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangcanfeng
 * @description 加载角色权限列表，有部分页面是权限角色才能访问的
 * @Date Created in 17:29-2018/12/27
 */
@Service
@Order(999)
public class FunnyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private UserRoleService roleService;

    /**
     * 存储权限信息的map
     */
    private static ConcurrentHashMap<String, HashSet<String>> authMap = new ConcurrentHashMap<>();

    /**
     * 功能描述：  初始化权限的map
     *
     * @param
     * @return java.lang.Boolean
     * @author wangcanfeng
     * @time 2019/2/12 16:20
     * @since v1.0
     **/
    @Bean
    public Boolean setAuthMap() {
        List<UserRole> roles = roleService.getRoleList();
        roles.forEach(userRole -> {
            //重组角色与路径的关系，路径作为键值，角色类型的列表作为值
            userRole.getMenuInfos().forEach(simpleMenuInfo -> addAuth(simpleMenuInfo.getMenuPath(), userRole.getRoleType()));
        });
        return true;
    }

    /**
     * 功能描述：增加菜单路径对应的角色
     *
     * @param path
     * @param roleType
     * @author wangcanfeng
     * @time 2019/3/9 23:21
     * @since v1.0
     **/
    public void addAuth(String path, String roleType) {
        if (!authMap.containsKey(path)) {
            HashSet<String> roleSet = new HashSet<>();
            roleSet.add(roleType);
            authMap.put(path, roleSet);
        } else {
            authMap.get(path).add(roleType);
        }
    }

    /**
     * 功能描述：增加菜单路径对应所需的权限
     *
     * @param path
     * @param roleType
     * @author wangcanfeng
     * @time 2019/3/9 23:21
     * @since v1.0
     **/
    public void removeAuth(String path, String roleType) {
        authMap.get(path).remove(roleType);
    }


    /**
     * 功能描述：  根据拦截到的信息获取路径对应所需的角色列表
     *
     * @param obj
     * @return java.util.Collection<org.springframework.security.access.ConfigAttribute>
     * @author wangcanfeng
     * @time 2019/2/12 16:20
     * @since v1.0
     **/
    @Override
    public Collection<ConfigAttribute> getAttributes(Object obj) throws IllegalArgumentException {
        Collection<ConfigAttribute> collection = new ArrayList<>();
        FilterInvocation filterInvocation = (FilterInvocation) obj;
        //从数据库中加载拦截路径，然后创建对象
        String url = filterInvocation.getRequestUrl();
        HashSet<String> roles = authMap.get(url);
        if (!ObjectUtils.isEmpty(roles)) {
            roles.forEach(role -> {
                ConfigAttribute configAttribute = new SecurityConfig(role);
                collection.add(configAttribute);
            });
        }
        return collection;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return new ArrayList<>();
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}