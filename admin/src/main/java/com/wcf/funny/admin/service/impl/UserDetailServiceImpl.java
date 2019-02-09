package com.wcf.funny.admin.service.impl;

import com.wcf.funny.admin.constant.UserConstant;
import com.wcf.funny.admin.entity.UserInfo;
import com.wcf.funny.admin.service.UserInfoService;
import com.wcf.funny.admin.service.UserRoleService;
import com.wcf.funny.admin.vo.UserDetailsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service("funnyDetailService")
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 查询用户根据用户名称获取用户信息
     *
     * @param s
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDetailsVo userDetails = new UserDetailsVo();
        UserInfo info = userInfoService.getByUsername(s);
        if (ObjectUtils.isEmpty(info)) {
            return null;
        }
        // 读取权限信息
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (!ObjectUtils.isEmpty(info.getRoleInfos())) {
            info.getRoleInfos().forEach(simpleRoleInfo -> {
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(simpleRoleInfo.getRoleType());
                authorities.add(simpleGrantedAuthority);
            });
        }
        // 能够查询出来的都是未禁用状态，所以直接设置就行
        userDetails.setEnabled(true).setFace(info.getFacePath()).setUsername(info.getUsername())
                .setRegisterTime(info.getRegisterTime()).setPassword(info.getPassword())
                .setIntroduce(info.getIntroduce()).setAuthorities(authorities);
        return userDetails;
    }
}
