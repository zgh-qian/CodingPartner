package com.codingpartner.codingpartnerbackend.model.dto.user;

import lombok.Data;

@Data
public class UserLoginRequest {
    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 确认密码
     */
    private String rePassword;
}
