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
    public final static String ACTION_TYPE_PREFIX = "log.action.type.";

    public static class ActionType {
        //增加
        public final static String ADD = "add";
        //删除
        public final static String DELETE = "delete";
        //更新
        public final static String UPDATE = "update";
        //搜索
        public final static String SEARCH = "search";
        //登录
        public final static String LOGIN = "login";
        //登出
        public final static String LOGOUT = "logout";
        //注册
        public final static String REGISTER = "register";
        //保存
        public final static String SAVE = "save";
        // 点赞
        public final static String STAR="star";
        // 发布
        public final static String PUBLISH="publish";
        // 上传
        public final static String UPLOAD="upload";
        //收藏
        public final static String COLLECT="collect";
        //取消
        public final static String CANCEL="cancel";
    }

    /**
     * 操作结果
     */
    public final static String ACTION_RESULT_PREFIX = "log.action.result.";

    public static class ActionResult {
        //成功
        public final static String SUCCESS = "success";
        //失败
        public final static String FAILED = "failed";
    }

    /**
     * 操作对象
     */
    public final static String ACTION_OBJECT_PREFIX = "log.action.object.";

    public static class ActionObject {
        // 用户
        public final static String USER = "user";
        // 文章
        public final static String ARTICLE = "article";
        // 评论
        public final static String COMMENT = "comment";
        // 关键词
        public final static String KEYWORD = "keyword";
        // 专题
        public final static String CATEGORY = "category";
        // 版本
        public final static String VERSION = "version";
        // 菜单
        public final static String MENU="menu";
        // 角色
        public final static String ROLE="role";
        // 视频
        public final static String VIDEO="video";
    }

    /**
     * 操作信息
     */
    public final static String ACTION_INFO_PREFIX = "log.action.info.";

    public static class ActionInfo {
        //重置用户密码
        public final static String RESET_USER_PASSWORD = "reset_user_password";
        //修改用户角色
        public final static String UPDATE_USER_ROLE = "update_user_role";
        //修改用户状态信息
        public final static String CHANGE_USER_STATUS = "change_user_status";
        //修改用户类型信息
        public final static String CHANGE_USER_TYPE = "change_user_type";
        // 删除版本信息
        public final static String DELETE_VERSION_INFO="delete_version_info";
        // 编辑版本信息
        public final static String MODIFY_VERSION_INFO="modify_version_info";
        // 增加版本信息
        public final static String ADD_VERSION_INFO="add_version_info";
        // 新增菜单信息
        public final static String ADD_MENU_INFO="add_menu_info";
        // 编辑菜单信息
        public final static String MODIFY_MENU_INFO="modify_menu_info";
        // 编辑菜单权限
        public final static String MODIFY_MENU_AUTH="modify_menu_auth";
        // 编辑菜单类型
        public final static String MODIFY_MENU_TYPE="modify_menu_type";
        // 删除菜单信息
        public final static String DELETE_MENU_TYPE="delete_menu_type";
        // 添加一个角色
        public final static String ADD_ROLE="add_role";
        // 修改角色信息
        public final static String MODIFY_ROLE="modify_role";
        // 删除角色信息
        public final static String DELETE_ROLE="delete_role";
        // 给文章点赞
        public final static String STAR_ARTICLE="star_article";
        // 编辑文章
        public final static String MODIFY_ARTICLE="modify_article";
        // 上传文章图片
        public final static String UPLOAD_ARTICLE_PICTURE="upload_article_picture";
        // 删除文章内图片
        public final static String DELETE_ARTICLE_PICTURE="delete_article_picture";
        // 上传文章封面图片
        public final static String UPLOAD_ARTICLE_COVER="upload_article_cover";
        // 删除文章
        public final static String DELETE_ARTICLE="delete_article";
        // 删除评论信息
        public final static String DELETE_COMMENT="delete_comment";
        // 添加评论信息
        public final static String ADD_COMMENT="add_comment";
        // 删除专题信息
        public final static String DELETE_CATEGORY="delete_category";
        // 删除关键字信息
        public final static String DELETE_KEYWORD="delete_keyword";
        // 编辑专题信息
        public final static String MODIFY_CATEGORY="modify_category";
        // 添加专题信息
        public final static String ADD_CATEGORY="add_category";
        // 上传专题封面
        public final static String UPLOAD_CATEGORY_COVER="upload_category_cover";
        // 上传用户头像
        public final static String UPLOAD_USER_FACE="upload_user_face";
        // 修改用户基本信息
        public final static String UPDATE_USER_BASE="update_user_base";
        // 修改用户密码
        public final static String UPDATE_USER_PASSWORD="update_user_password";
        // 更新用户详情
        public final static String UPDATE_USER_DETAILS="update_user_details";
        // 上传个人简历
        public final static String UPLOAD_RESUME="upload_resume";
        // 上传思维导图
        public final static String UPLOAD_MIND="upload_mind";
        // 收藏视频
        public final static String COLLECT_VIDEO="collect_video";
        // 取消收藏视频
        public final static String CANCEL_COLLECT_VIDEO="cancel_collect_video";
    }

    /**
     * 操作详情
     */
    public static class ActionDetails {

    }
}
