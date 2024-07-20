package com.codingpartner.codingpartnerbackend.utils;

import org.springframework.util.DigestUtils;

import static com.codingpartner.codingpartnerbackend.constant.UserConstant.SALT;

/**
 * 加密工具类
 */
public class Md5Util {
    public static String getPassword(String password) {
        String passwordWithSalt = password + SALT;
        return DigestUtils.md5DigestAsHex(passwordWithSalt.getBytes());
    }
}
