package com.wcf.funny.admin.vo;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author wangcanfeng
 * @time 2018/12/29
 * @function 用户详细信息，配合security
 **/
public class UserDetailsVo implements UserDetails {
    /**
     * 用户角色列表
     */
    private  Collection<? extends GrantedAuthority> authorities;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户名
     */
    private String username;

    /**
     * 头像路径
     */
    private String face;

    /**
     * 注册时间
     */
    private String registerTime;

    /**
     * 签名档
     */
    private String introduce;

    private Boolean accountNonExpired;

    private Boolean accountNonLocked;

    private Boolean credentialsNonExpired;

    /**
     * 是否禁用
     */
    private Boolean isEnabled;

    private UserDetailsVo setAuthorities(Collection<? extends GrantedAuthority> authorities){
        this.authorities=authorities;
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public UserDetailsVo setPassword(String password){
        this.password=password;
        return this;
    }

    @Override
    public String getPassword() {
        return password;
    }


    public UserDetailsVo setUsername(String username){
        this.username=username;
        return this;
    }

    @Override
    public String getUsername() {
        return username;
    }


    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public UserDetailsVo setEnabled(Boolean enabled){
        this.isEnabled=enabled;
        return this;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public String getFace() {
        return face;
    }

    public UserDetailsVo setFace(String face) {
        this.face = face;
        return this;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public UserDetailsVo setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
        return this;
    }

    public String getIntroduce() {
        return introduce;
    }

    public UserDetailsVo setIntroduce(String introduce) {
        this.introduce = introduce;
        return this;
    }
}
