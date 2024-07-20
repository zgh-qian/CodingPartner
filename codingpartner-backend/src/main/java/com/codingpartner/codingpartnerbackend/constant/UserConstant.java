package com.codingpartner.codingpartnerbackend.constant;

/**
 * 用户常量
 */
public interface UserConstant {

    /**
     * 用户登录态键
     */
    String USER_LOGIN_STATE = "codingpartner_user_login";

    /**
     * 密钥 salt
     */
    String SALT = "codingpartnerbackend";

    //  region 权限

    /**
     * 默认角色
     */
    String DEFAULT_ROLE = "user";

    /**
     * 管理员角色
     */
    String ADMIN_ROLE = "admin";

    /**
     * 被封号
     */
    String BAN_ROLE = "ban";

    // endregion

    String USER_REGISTER_FAILED = "注册用户失败";
    String USER_REGISTER_SUCCESS = "注册成功";
    String USER_LOUGOUT_SUCCESS = "登出成功";
    String USER_UPDATE_SUCCESS = "修改成功";
    String USER_UPDATE_FAILED = "修改失败";
    String USER_DELETE_SUCCESS = "注销成功";
    String USER_DELETE_FAILED = "注销失败";
    String USER_BAN_SUCCESS = "封禁成功";
    String USER_BAN_FAILED = "封禁失败";
    String USER_UNBAN_SUCCESS = "解封成功";
    String USER_UNBAN_FAILED = "解封失败";
    String USER_USERNAME_INVALID = "账号长度不合法，账号长度为4-20个字符";
    String USER_PASSWORD_WRONG = "密码错误";
    String USER_PASSWORD_INVALID = "密码长度不合法，密码长度为6-20个字符";
    String USER_REPEAT_PASSWORD_INVALID = "两次输入的密码不一致";
    String USER_EMAIL_INVALID = "邮箱不合法，请输入正确的邮箱格式";
    String USER_NICKNAME_INVALID = "昵称长度不合法，昵称长度为1-20个字符";
    String USER_AVATAR_INVALID = "头像地址长度不合法";
    String USER_PHONE_INVALID = "手机号长度不合法";
    String USER_ADDRESS_INVALID = "地址长度不合法";
    String USER_EDUCATION_EXPERIENCE_INVALID = "教育经历长度不合法";
    String USER_COMPANY_INVALID = "公司长度不合法";
    String USER_POSITION_INVALID = "职位长度不合法";
    String USER_TAGS_INVALID = "标签长度不合法";
    String USER_WEBSITE_INVALID = "个人网站长度不合法";
    String USER_INTRODUCTION_INVALID = "个人简介长度不合法";
    String USER_USERNAME_EXIST = "账号已存在";
    String USER_USERNAME_NOT_EXIST = "账号不存在";
    String USER_NOT_LOGIN = "未登录";
    String USER_BAN_ADMIN_ERROR = "封禁管理员错误";


}
