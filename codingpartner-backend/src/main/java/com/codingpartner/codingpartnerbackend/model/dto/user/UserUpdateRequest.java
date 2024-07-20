package com.codingpartner.codingpartnerbackend.model.dto.user;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserUpdateRequest {
    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别：0-未知，1-男，2-女
     */
    private Integer gender;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 地址
     */
    private String address;

    /**
     * 学历
     */
    private String education;

    /**
     * 公司
     */
    private String company;

    /**
     * 职位
     */
    private String position;

    /**
     * 个人标签(json数组)
     */
    private List<String> tags;

    /**
     * 个人网站(json数组)
     */
    private List<String> website;

    /**
     * 个人简介
     */
    private String introduction;

    /**
     * 是否私密：0-公开，1-私密
     */
    private Integer isPrivate;
}
