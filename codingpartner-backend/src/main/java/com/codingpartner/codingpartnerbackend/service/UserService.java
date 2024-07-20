package com.codingpartner.codingpartnerbackend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codingpartner.codingpartnerbackend.model.dto.user.UserLoginRequest;
import com.codingpartner.codingpartnerbackend.model.dto.user.UserQueryRequest;
import com.codingpartner.codingpartnerbackend.model.dto.user.UserRegisterRequest;
import com.codingpartner.codingpartnerbackend.model.dto.user.UserUpdateRequest;
import com.codingpartner.codingpartnerbackend.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.codingpartner.codingpartnerbackend.model.vo.user.UserVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zgh
 * @description 针对表【user(用户表)】的数据库操作Service
 * @createDate 2024-04-04 08:26:51
 */
public interface UserService extends IService<User> {
    /**
     * 用户注册
     *
     * @param userRegisterRequest 用户注册请求
     */
    void userRegister(UserRegisterRequest userRegisterRequest);

    /**
     * 用户登录
     *
     * @param userLoginRequest 用户登录请求
     * @param request          请求
     * @return userVO
     */
    UserVO userLogin(UserLoginRequest userLoginRequest, HttpServletRequest request);

    /**
     * 用户登出
     *
     * @param request 请求
     */
    void userLogout(HttpServletRequest request);

    /**
     * 用户注销
     *
     * @param request 请求
     */
    void userDelete(HttpServletRequest request);

    /**
     * 更新用户
     *
     * @param userUpdateRequest 用户更新请求
     * @param request           请求
     */
    void userUpdate(UserUpdateRequest userUpdateRequest, HttpServletRequest request);

    /**
     * 获取脱敏后的用户信息
     *
     * @param username 用户名
     * @param request  请求
     * @return 用户信息
     */
    UserVO getUserVO(String username, HttpServletRequest request);

    /**
     * 获取脱敏后的登录用户信息
     *
     * @param request 请求
     * @return 登录用户信息
     */
    UserVO getLoginUserVO(HttpServletRequest request);

    /**
     * 封禁用户
     *
     * @param userId 用户id
     */
    void banUserById(Long userId);

    /**
     * 解封用户
     *
     * @param userId 用户id
     */
    void unbanUserById(Long userId);

    /**
     * 根据用户id获取用户信息
     *
     * @param userId 用户id
     * @return 用户信息
     */
    User getUserById(Long userId);

    /**
     * 获取用户列表
     *
     * @param userQueryRequest 用户查询请求
     * @return 用户列表
     */
    Page<User> getUserList(UserQueryRequest userQueryRequest);
}
