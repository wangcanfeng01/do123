package com.wcf.funny.admin.constant;

import com.wcf.funny.admin.exception.UserException;
import com.wcf.funny.admin.exception.errorcode.UserErrorCode;
import com.wcf.funny.core.constant.InfoEnum;

/**
 * @author wangcanfeng
 * @time 2019/3/9
 * @function 用户类型
 **/
public enum UserType implements InfoEnum{
    USER("user_code","user"),
    PROGRAMMER("programmer_code","programmer");

    UserType(String code,String type) {
        this.code=code;
        this.type = type;
    }

    private String type;

    private String code;

    public String getInfo() {
        return type;
    }

    public String getCode(){
        return code;
    }

    /**
     * 功能描述：  将字符串信息转成枚举类型
     *
     * @param type
     * @author wangcanfeng
     * @time 2019/3/9 12:59
     * @since v1.0
     **/
    public static UserType valueOfString(String type) {
        UserType userType;
        switch (type) {
            case "user": {
                userType = USER;
                break;
            }
            case "programmer": {
                userType = PROGRAMMER;
                break;
            }
            default: {
                throw new UserException(UserErrorCode.USER_TYPE_ERROR);
            }
        }
        return userType;
    }
}
