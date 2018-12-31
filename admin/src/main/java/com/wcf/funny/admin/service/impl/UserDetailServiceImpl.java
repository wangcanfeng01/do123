package com.wcf.funny.admin.service.impl;

import com.wcf.funny.admin.constant.UserConstant;
import com.wcf.funny.admin.entity.UserInfo;
import com.wcf.funny.admin.service.UserInfoService;
import com.wcf.funny.admin.vo.UserDetailsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;


@Service
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
//        String facePath = UserConstant.FACE_PATH_PREFIX + new Random().nextInt(10) + ".jpg";
        //获取头像图片路径
        String facePath = UserConstant.FACE_PATH_PREFIX + info.getFaceName() + ".jpg";
        userDetails.setEnabled(true).setFace(facePath).setUsername(info.getUsername())
                .setRegisterTime(info.getRegisterTime()).setPassword(info.getPassword())
                .setIntroduce(userDetails.getIntroduce());
        return userDetails;
    }
}
