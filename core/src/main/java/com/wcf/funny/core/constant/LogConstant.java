package com.wcf.funny.core.constant;

/**
 * @author wangcanfeng
 * @time 2019/3/4
 * @function 日志常量
 **/
public class LogConstant {

    /**
     * 操作类型
     */
    public final static String ACTION_TYPE_PREFIX="log.action.type";
    public static class ActionType {
        //增加
        public final static String ADD="add";
        //删除
        public final static String DELETE="delete";
        //更新
        public final static String UPDATE="update";
        //搜索
        public final static String SEARCH="search";
        //登录
        public final static String LOGIN="login";
        //登出
        public final static String LOGOUT="logout";
        //注册
        public final static String REGISTER="register";
    }

    /**
     * 操作结果
     */
    public final static String ACTION_RESULT_PREFIX="log.action.result";
    public static class ActionResult{
        //成功
        public final static String SUCCESS = "success";
        //失败
        public final static String FAILED = "failed";
    }

    /**
     * 操作对象
     */
    public final static String ACTION_OBJECT_PREFIX="log.action.object";
    public static class ActionObject{
        // 用户
        public final static String USER="user";
    }

    /**
     * 操作信息
     */
    public final static String ACTION_INFO_PREFIX="log.action.info";
    public static class ActionInfo {
        //重置用户密码
        public final static String RESET_USER_PASSWORD="reset_user_password";
        //修改用户角色
        public final static String UPDATE_USER_ROLE="update_user_role";
        //修改用户状态信息
        public final static String CHANGE_USER_STATUS="change_user_status";
    }

    /**
     * 操作详情
     */
    public static class ActionDetails{

    }
}
