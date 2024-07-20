package com.codingpartner.codingpartnerbackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codingpartner.codingpartnerbackend.annotation.AuthCheck;
import com.codingpartner.codingpartnerbackend.common.BaseResponse;
import com.codingpartner.codingpartnerbackend.common.ResultUtils;
import com.codingpartner.codingpartnerbackend.constant.UserConstant;
import com.codingpartner.codingpartnerbackend.model.dto.user.UserQueryRequest;
import com.codingpartner.codingpartnerbackend.model.entity.User;
import com.codingpartner.codingpartnerbackend.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


import static com.codingpartner.codingpartnerbackend.constant.UserConstant.*;

@RestController
@RequestMapping("/admin")
@Api(tags = "管理员相关接口")
@Slf4j
public class AdminController {
    @Resource
    private UserService userService;

    /**
     * 封禁用户
     *
     * @param userId 用户id
     * @return USER_BAN_SUCCESS
     */
    @PutMapping("/ban")
    @ApiOperation("封禁用户")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<String> banUserById(Long userId) {
        userService.banUserById(userId);
        return ResultUtils.success(USER_BAN_SUCCESS);
    }

    /**
     * 解封用户
     *
     * @param userId 用户id
     * @return USER_BAN_SUCCESS
     */
    @PutMapping("/unban")
    @ApiOperation("解封用户")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<String> unbanUserById(Long userId) {
        userService.unbanUserById(userId);
        return ResultUtils.success(USER_UNBAN_SUCCESS);
    }

    /**
     * 根据id获取用户信息
     *
     * @param userId
     * @return
     */
    @GetMapping("/get")
    @ApiOperation("获取用户信息")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<User> getUserById(Long userId) {
        User user = userService.getUserById(userId);
        return ResultUtils.success(user);
    }

    /**
     * 获取用户列表
     *
     * @param userQueryRequest
     * @return
     */
    @PostMapping("/list")
    @ApiOperation("获取用户列表")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<User>> getUserList(@RequestBody UserQueryRequest userQueryRequest) {
        Page<User> userList = userService.getUserList(userQueryRequest);
        return ResultUtils.success(userList);
    }
}
