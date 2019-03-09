package com.wcf.funny.admin.constant;

import com.wcf.funny.admin.exception.UserException;
import com.wcf.funny.admin.exception.errorcode.UserErrorCode;
import com.wcf.funny.core.constant.InfoEnum;

/**
 * @author wangcanfeng
 * @time 2019/3/9
 * @function 用户状态
 **/
public enum UserStatus implements InfoEnum {
    DISABLE("user_disable", 0),
    ENABLE("user_enable", 1),;

    UserStatus(String code, Integer status) {
        this.code = code;
        this.status = status;
    }

    private String code;

    private Integer status;

    public Integer getInfo() {
        return status;
    }

    public String getCode() {
        return code;
    }

    /**
     * 功能描述：  将整型的int数字转成枚举值
     *
     * @param status
     * @author wangcanfeng
     * @time 2019/3/9 12:59
     * @since v1.0
     **/
    public static UserStatus valueOfInteger(Integer status) {
        UserStatus userStatus;
        switch (status) {
            case 0: {
                userStatus = DISABLE;
                break;
            }
            case 1: {
                userStatus = ENABLE;
                break;
            }
            default: {
                throw new UserException(UserErrorCode.USER_STATUS_ERROR);
            }
        }
        return userStatus;
    }
}
