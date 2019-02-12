package com.wcf.funny.config.security;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.wcf.funny.admin.entity.UserRole;
import com.wcf.funny.admin.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;
<<<<<<< HEAD
=======
import org.springframework.util.ObjectUtils;
>>>>>>> dbe92b545ca4db17c938429bc525792603f0e757

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangcanfeng
<<<<<<< HEAD
 * @description 加载角色权限列表，有部分页面是权限角色才能访问的
 * @Date Created in 17:29-2018/12/27
 */
@Service
public class FunnyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

=======
 * @description 从数据库中加载权限信息
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

    @Bean
    public Boolean setAuthMap() {
        List<UserRole> roles = roleService.getRoleList();
        roles.forEach(userRole -> {
            //重组角色与路径的关系，路径作为键值，角色类型的列表作为值
            userRole.getMenuInfos().forEach(simpleMenuInfo -> {
                if (!authMap.containsKey(simpleMenuInfo.getMenuPath())) {
                    HashSet<String> roleSet = new HashSet<>();
                    roleSet.add(userRole.getRoleType());
                    authMap.put(simpleMenuInfo.getMenuPath(), roleSet);
                } else {
                    authMap.get(simpleMenuInfo.getMenuPath()).add(userRole.getRoleType());
                }
            });
        });
        return true;
    }

>>>>>>> dbe92b545ca4db17c938429bc525792603f0e757

    @Override
    public Collection<ConfigAttribute> getAttributes(Object obj) throws IllegalArgumentException {
        Collection<ConfigAttribute> collection = new ArrayList<>();
        FilterInvocation filterInvocation = (FilterInvocation) obj;
        //从数据库中加载拦截路径，然后创建对象
        String url = filterInvocation.getRequestUrl();
        HashSet<String> roles = authMap.get(url);
        if (!ObjectUtils.isEmpty(roles)) {
            roles.forEach(role->{
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