package com.codingpartner.codingpartnerbackend.controller;

import com.codingpartner.codingpartnerbackend.common.BaseResponse;
import com.codingpartner.codingpartnerbackend.common.ResultUtils;
import com.codingpartner.codingpartnerbackend.model.dto.user.UserLoginRequest;
import com.codingpartner.codingpartnerbackend.model.dto.user.UserRegisterRequest;
import com.codingpartner.codingpartnerbackend.model.dto.user.UserUpdateRequest;
import com.codingpartner.codingpartnerbackend.model.vo.user.UserVO;
import com.codingpartner.codingpartnerbackend.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.codingpartner.codingpartnerbackend.constant.UserConstant.*;

@RestController
@RequestMapping("/user")
@Api(tags = "用户相关接口")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册
     *
     * @param userRegisterRequest 用户注册请求
     * @return USER_REGISTER_SUCCESS
     */
    @PostMapping("/register")
    @ApiOperation("用户注册")
    public BaseResponse<String> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        // log.info("用户注册请求参数：{}", userRegisterRequest);
        userService.userRegister(userRegisterRequest);
        return ResultUtils.success(USER_REGISTER_SUCCESS);
    }

    /**
     * 用户登录
     *
     * @param userLoginRequest 用户登录请求
     * @param request          请求
     * @return loginUserVO
     */
    @PostMapping("/login")
    @ApiOperation("用户登录")
    public BaseResponse<UserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        UserVO loginUserVO = userService.userLogin(userLoginRequest, request);
        return ResultUtils.success(loginUserVO);
    }

    /**
     * 用户登出
     *
     * @param request 请求
     * @return USER_LOUGOUT_SUCCESS
     */
    @PostMapping("/logout")
    @ApiOperation("用户登出")
    public BaseResponse<String> userLogout(HttpServletRequest request) {
        userService.userLogout(request);
        return ResultUtils.success(USER_LOUGOUT_SUCCESS);
    }

    /**
     * 用户注销
     *
     * @param request 请求
     * @return USER_DELETE_SUCCESS
     */
    @DeleteMapping("/delete")
    @ApiOperation("用户注销")
    public BaseResponse<String> userDelete(HttpServletRequest request) {
        userService.userDelete(request);
        return ResultUtils.success(USER_DELETE_SUCCESS);
    }

    /**
     * 更新用户
     *
     * @param userUpdateRequest 用户更新请求
     * @param request           请求
     * @return USER_UPDATE_SUCCESS
     */
    @PutMapping("/update")
    @ApiOperation("更新用户")
    public BaseResponse<String> userUpdate(@RequestBody UserUpdateRequest userUpdateRequest, HttpServletRequest request) {
        userService.userUpdate(userUpdateRequest, request);
        return ResultUtils.success(USER_UPDATE_SUCCESS);
    }

    /**
     * 获取其他用户
     *
     * @param username 用户名
     * @param request  请求
     * @return 其他用户信息
     */
    @GetMapping("/get")
    @ApiOperation("获取其他用户")
    public BaseResponse<UserVO> getUserVO(@RequestParam(value = "username") String username, HttpServletRequest request) {
        UserVO userVO = userService.getUserVO(username, request);
        return ResultUtils.success(userVO);
    }

    /**
     * 获取当前登录用户
     *
     * @param request 请求
     * @return loginUserVO
     */
    @GetMapping("/get/login")
    @ApiOperation("获取当前用户")
    public BaseResponse<UserVO> getLoginUserVO(HttpServletRequest request) {
        UserVO userVO = userService.getLoginUserVO(request);
        return ResultUtils.success(userVO);
    }
}
