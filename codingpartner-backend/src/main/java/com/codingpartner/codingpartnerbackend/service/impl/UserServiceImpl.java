package com.codingpartner.codingpartnerbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codingpartner.codingpartnerbackend.common.ErrorCode;
import com.codingpartner.codingpartnerbackend.constant.CommonConstant;
import com.codingpartner.codingpartnerbackend.constant.UserConstant;
import com.codingpartner.codingpartnerbackend.exception.BusinessException;
import com.codingpartner.codingpartnerbackend.model.dto.user.UserLoginRequest;
import com.codingpartner.codingpartnerbackend.model.dto.user.UserQueryRequest;
import com.codingpartner.codingpartnerbackend.model.dto.user.UserRegisterRequest;
import com.codingpartner.codingpartnerbackend.model.dto.user.UserUpdateRequest;
import com.codingpartner.codingpartnerbackend.model.entity.User;
import com.codingpartner.codingpartnerbackend.model.vo.user.UserVO;
import com.codingpartner.codingpartnerbackend.service.UserService;
import com.codingpartner.codingpartnerbackend.mapper.UserMapper;
import com.codingpartner.codingpartnerbackend.utils.Md5Util;
import com.codingpartner.codingpartnerbackend.utils.SqlUtil;
import com.codingpartner.codingpartnerbackend.utils.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;

import static com.codingpartner.codingpartnerbackend.constant.CommonConstant.EXIST_PARAM_NULL;
import static com.codingpartner.codingpartnerbackend.constant.UserConstant.*;

/**
 * @author zgh
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2024-04-04 08:26:51
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Resource
    private UserMapper userMapper;


    /**
     * 注册用户
     *
     * @param userRegisterRequest 注册请求
     */
    @Override
    public void userRegister(UserRegisterRequest userRegisterRequest) {
        // 1. 准备参数
        String username = userRegisterRequest.getUsername();
        String password = userRegisterRequest.getPassword();
        String email = userRegisterRequest.getEmail();
        String nickname = userRegisterRequest.getNickname();
        String avatar = userRegisterRequest.getAvatar();
        Integer gender = userRegisterRequest.getGender();
        Date birthday = userRegisterRequest.getBirthday();
        String phone = userRegisterRequest.getPhone();
        String address = userRegisterRequest.getAddress();
        String education = userRegisterRequest.getEducation();
        String company = userRegisterRequest.getCompany();
        String position = userRegisterRequest.getPosition();
        String tags = userRegisterRequest.getTags().toString();
        String website = userRegisterRequest.getWebsite().toString();
        String introduction = userRegisterRequest.getIntroduction();
        // 2. 校验用户输入的数据
        // 校验用户名、密码、邮箱、昵称是否为空
        if (StringUtils.isAnyBlank(username, password, email, nickname)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, EXIST_PARAM_NULL);
        }
        // 校验用户名长度是否合法
        if (username.length() < 4 || username.length() > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, USER_USERNAME_INVALID);
        }
        // 校验密码长度是否合法
        if (password.length() < 6 || password.length() > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, USER_PASSWORD_INVALID);
        }
        // 校验邮箱格式是否合法
        if (!ValidatorUtil.isEmailValid(email)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, USER_EMAIL_INVALID);
        }
        // 校验昵称长度是否合法
        if (nickname.length() > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, USER_NICKNAME_INVALID);
        }
        // 校验头像长度是否合法
        if (avatar != null && avatar.length() > 100) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, USER_AVATAR_INVALID);
        }
        // 校验手机号长度是否合法
        if (phone != null && phone.length() > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, USER_PHONE_INVALID);
        }
        // 校验地址长度是否合法
        if (address != null && address.length() > 100) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, USER_ADDRESS_INVALID);
        }
        // 校验教育经历长度是否合法
        if (education != null && education.length() > 100) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, USER_EDUCATION_EXPERIENCE_INVALID);
        }
        // 校验公司长度是否合法
        if (company != null && company.length() > 100) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, USER_COMPANY_INVALID);
        }
        // 校验职位长度是否合法
        if (position != null && position.length() > 100) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, USER_POSITION_INVALID);
        }
        // 校验标签长度是否合法
        if (tags != null && tags.length() > 100) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, USER_TAGS_INVALID);
        }
        // 校验个人网站长度是否合法
        if (website != null && website.length() > 100) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, USER_WEBSITE_INVALID);
        }
        // 校验个人简介长度是否合法
        if (introduction != null && introduction.length() > 1000) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, USER_INTRODUCTION_INVALID);
        }
        // 3. 复制属性到User对象
        User user = new User();
        BeanUtils.copyProperties(userRegisterRequest, user);
        user.setRole(DEFAULT_ROLE);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        synchronized (username.intern()) {
            // 4. 检查账号是否存在
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            Long count = this.userMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, USER_USERNAME_EXIST);
            }
            // 5. 加密密码
            user.setPassword(Md5Util.getPassword(password));
            // 6. 保存到数据库
            boolean save = this.save(user);
            if (!save) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, USER_REGISTER_FAILED);
            }
        }
    }

    /**
     * 用户登录
     *
     * @param userLoginRequest 登录请求
     * @param request          请求
     * @return 登录成功的用户信息
     */
    @Override
    public UserVO userLogin(UserLoginRequest userLoginRequest, HttpServletRequest request) {
        // 1. 准备参数
        String username = userLoginRequest.getUsername();
        String password = userLoginRequest.getPassword();
        String rePassword = userLoginRequest.getRePassword();
        // 2. 校验用户输入的数据
        // 校验用户名、密码、确认密码是否为空
        if (StringUtils.isAnyBlank(username, password, rePassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, EXIST_PARAM_NULL);
        }
        // 校验用户名长度是否合法
        if (username.length() < 4 || username.length() > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, USER_USERNAME_INVALID);
        }
        // 校验密码长度是否合法
        if (password.length() < 6 || password.length() > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, USER_PASSWORD_INVALID);
        }
        // 校验确认密码是否一致
        if (!password.equals(rePassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, USER_REPEAT_PASSWORD_INVALID);
        }
        // 3. 查询用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = this.userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, USER_USERNAME_NOT_EXIST);
        }
        // 4. 验证密码
        String md5Password = Md5Util.getPassword(password);
        if (!md5Password.equals(user.getPassword())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, USER_PASSWORD_WRONG);
        }
        // 5. 设置session
        request.getSession().setAttribute(USER_LOGIN_STATE, user);
        // 6. 返回结果
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    /**
     * 用户登出
     *
     * @param request
     */
    @Override
    public void userLogout(HttpServletRequest request) {
        // 1. 如果session中没有登录信息，则直接返回
        if (request == null || request.getSession().getAttribute(USER_LOGIN_STATE) == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, USER_NOT_LOGIN);
        }
        // 2. 销毁session
        request.getSession().removeAttribute(USER_LOGIN_STATE);
    }

    /**
     * 更新用户信息
     *
     * @param userUpdateRequest 用户更新请求
     * @param request           请求
     */
    @Override
    public void userUpdate(UserUpdateRequest userUpdateRequest, HttpServletRequest request) {
        // 1. 准备参数
        if (userUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, EXIST_PARAM_NULL);
        }
        String username = userUpdateRequest.getUsername();
        String password = userUpdateRequest.getPassword();
        String email = userUpdateRequest.getEmail();
        String nickname = userUpdateRequest.getNickname();
        String avatar = userUpdateRequest.getAvatar();
        Integer gender = userUpdateRequest.getGender();
        Date birthday = userUpdateRequest.getBirthday();
        String phone = userUpdateRequest.getPhone();
        String address = userUpdateRequest.getAddress();
        String education = userUpdateRequest.getEducation();
        String company = userUpdateRequest.getCompany();
        String position = userUpdateRequest.getPosition();
        String tags = userUpdateRequest.getTags().toString();
        String website = userUpdateRequest.getWebsite().toString();
        String introduction = userUpdateRequest.getIntroduction();
        Integer isPrivate = userUpdateRequest.getIsPrivate();
        // 2. 校验用户输入的数据
        // 校验用户名长度是否合法
        if (username != null && !StringUtils.isBlank(username) && (username.length() < 4 || username.length() > 20)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, USER_USERNAME_INVALID);
        }
        // 校验密码长度是否合法
        if (password != null && !StringUtils.isBlank(password) && (password.length() < 6 || password.length() > 20)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, USER_PASSWORD_INVALID);
        }
        // 校验邮箱格式是否合法
        if (email != null && !StringUtils.isBlank(email) && !ValidatorUtil.isEmailValid(email)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, USER_EMAIL_INVALID);
        }
        // 校验昵称长度是否合法
        if (nickname != null && !StringUtils.isBlank(nickname) && nickname.length() > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, USER_NICKNAME_INVALID);
        }
        // 校验头像长度是否合法
        if (avatar != null && !StringUtils.isBlank(avatar) && avatar.length() > 100) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, USER_AVATAR_INVALID);
        }
        // 校验手机号长度是否合法
        if (phone != null && !StringUtils.isBlank(phone) && phone.length() > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, USER_PHONE_INVALID);
        }
        // 校验地址长度是否合法
        if (address != null && !StringUtils.isBlank(address) && address.length() > 100) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, USER_ADDRESS_INVALID);
        }
        // 校验教育经历长度是否合法
        if (education != null && !StringUtils.isBlank(education) && education.length() > 100) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, USER_EDUCATION_EXPERIENCE_INVALID);
        }
        // 校验公司长度是否合法
        if (company != null && !StringUtils.isBlank(company) && company.length() > 100) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, USER_COMPANY_INVALID);
        }
        // 校验职位长度是否合法
        if (position != null && !StringUtils.isBlank(position) && position.length() > 100) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, USER_POSITION_INVALID);
        }
        // 校验标签长度是否合法
        if (tags != null && !StringUtils.isBlank(tags) && tags.length() > 100) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, USER_TAGS_INVALID);
        }
        // 校验个人网站长度是否合法
        if (website != null && !StringUtils.isBlank(website) && website.length() > 100) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, USER_WEBSITE_INVALID);
        }
        // 校验个人简介长度是否合法
        if (introduction != null && !StringUtils.isBlank(introduction) && introduction.length() > 1000) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, USER_INTRODUCTION_INVALID);
        }
        // 3. 复制属性到User对象
        User user = new User();
        BeanUtils.copyProperties(userUpdateRequest, user);
        user.setUpdateTime(LocalDateTime.now());
        user.setPassword(Md5Util.getPassword(password));
        User currentUser = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        user.setId(currentUser.getId());
        // 4. 插入到数据库
        int update = userMapper.updateById(user);
        if (update == 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, USER_UPDATE_FAILED);
        }
        // 5. 更新session
        request.getSession().setAttribute(USER_LOGIN_STATE, user);
    }


    /**
     * 用户注销
     *
     * @param request 请求
     */
    @Override
    public void userDelete(HttpServletRequest request) {
        // 1. 准备参数
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        // 2. 校验用户是否登录
        if (userObj == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 3. 删除用户
        User currentUser = (User) userObj;
        int delete = userMapper.deleteById(currentUser);
        if (delete == 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, USER_DELETE_FAILED);
        }
    }

    /**
     * 获取脱敏后的其他用户信息
     *
     * @param username 用户名
     * @param request  请求
     * @return 用户信息
     */
    @Override
    public UserVO getUserVO(String username, HttpServletRequest request) {
        // 1. 准备参数
        if (StringUtils.isBlank(username)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, EXIST_PARAM_NULL);
        }
        // 2. 查询用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = this.userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, USER_USERNAME_NOT_EXIST);
        }
        // 3. 获取脱敏后的用户信息
        UserVO userVO = new UserVO();
        // 私密或被封禁的用户只能查看部分信息
        if (user.getIsPrivate() == 1 || user.getIsBanned() == 1) {
            userVO.setUsername(user.getUsername());
            userVO.setAvatar(user.getAvatar());
            userVO.setIsPrivate(user.getIsPrivate());
            userVO.setIsBanned(user.getIsBanned());
            return userVO;
        }
        // 正常公开用户可以查看全部信息
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    /**
     * 获取脱敏后的登录用户信息
     *
     * @param request 请求
     * @return
     */
    @Override
    public UserVO getLoginUserVO(HttpServletRequest request) {
        // 1. 准备参数
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        // 2. 校验用户是否登录
        if (userObj == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 3. 获取用户信息
        User currentUser = (User) userObj;
        User user = this.getById(currentUser.getId());
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        UserVO userVO = new UserVO();
        // 被封禁的用户只能查看部分信息
        if (user.getIsBanned() == 1) {
            userVO.setUsername(user.getUsername());
            userVO.setAvatar(user.getAvatar());
            userVO.setIsBanned(user.getIsBanned());
            return userVO;
        }
        // 正常用户可以查看全部信息
        BeanUtils.copyProperties(user, userVO);
        // 4. 返回结果
        return userVO;
    }

    /**
     * 封禁用户
     *
     * @param userId 用户id
     */
    @Override
    public void banUserById(Long userId) {
        User user = new User();
        user.setId(userId);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);
        User bannedUser = this.userMapper.selectOne(queryWrapper);
        if (bannedUser == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, USER_USERNAME_NOT_EXIST);
        }
        if (bannedUser.getRole().equals(ADMIN_ROLE)) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, USER_BAN_ADMIN_ERROR);
        }
        user.setIsBanned(1);
        user.setUpdateTime(LocalDateTime.now());
        int update = userMapper.updateById(user);
        if (update == 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, USER_BAN_FAILED);
        }
    }

    /**
     * 解封用户
     *
     * @param userId 用户id
     */
    @Override
    public void unbanUserById(Long userId) {
        User user = new User();
        user.setId(userId);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);
        User unbannedUser = this.userMapper.selectOne(queryWrapper);
        if (unbannedUser == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, USER_USERNAME_NOT_EXIST);
        }
        if (unbannedUser.getIsBanned() == 0) {
            return;
        }
        user.setIsBanned(0);
        user.setUpdateTime(LocalDateTime.now());
        int update = userMapper.updateById(user);
        if (update == 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, USER_UNBAN_FAILED);
        }
    }

    /**
     * 获取用户信息
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public User getUserById(Long userId) {
        if (userId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, EXIST_PARAM_NULL);
        }
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, USER_USERNAME_NOT_EXIST);
        }
        return user;
    }

    @Override
    public Page<User> getUserList(UserQueryRequest userQueryRequest) {
        Long id = userQueryRequest.getId();
        String username = userQueryRequest.getUsername();
        String role = userQueryRequest.getRole();
        String level = userQueryRequest.getLevel();
        Integer submissionCount = userQueryRequest.getSubmissionCount();
        Integer acceptedCount = userQueryRequest.getAcceptedCount();
        Integer isBanned = userQueryRequest.getIsBanned();
        LocalDateTime createTime = userQueryRequest.getCreateTime();
        LocalDateTime updateTime = userQueryRequest.getUpdateTime();

        long current = userQueryRequest.getCurrent();
        long pageSize = userQueryRequest.getPageSize();
        String sortField = userQueryRequest.getSortField();
        String sortOrder = userQueryRequest.getSortOrder();

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id != null, "id", id) // 用户id
                .eq(StringUtils.isNotBlank(role), "role", role) // 角色
                .eq(StringUtils.isNotBlank(level), "level", level) // 等级
                .like(StringUtils.isNotBlank(username), "username", username) // 模糊查询
                .orderBy(SqlUtil.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);// 排序
        Page<User> userPage = userMapper.selectPage(new Page<>(current, pageSize), queryWrapper);
        return userPage;
    }
}




