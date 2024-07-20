package com.codingpartner.codingpartnerbackend.model.vo.user;

import cn.hutool.json.JSONUtil;
import com.codingpartner.codingpartnerbackend.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 脱敏后的用户信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserVO {
    /**
     * 账号
     */
    private String username;

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
     * 角色
     */
    private String role;

    /**
     * 等级
     */
    private String level;

    /**
     * 提交次数
     */
    private Integer submissionCount;

    /**
     * 通过次数
     */
    private Integer acceptedCount;

    /**
     * 是否封禁：0-未封禁，1-封禁
     */
    private Integer isBanned;

    /**
     * 是否私密：0-公开，1-私密
     */
    private Integer isPrivate;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 包装类转对象
     *
     * @param userVO UserVO对象
     * @return User对象
     */
    public static User voToObj(UserVO userVO) {
        if (userVO == null) {
            return null;
        }
        User user = new User();
        BeanUtils.copyProperties(userVO, user);
        List<String> tagList = userVO.getTags();
        if (tagList != null) {
            user.setTags(JSONUtil.toJsonStr(tagList));
        }
        List<String> websiteList = userVO.getWebsite();
        if (websiteList != null) {
            user.setWebsite(JSONUtil.toJsonStr(websiteList));
        }
        return user;
    }

    /**
     * 对象转包装类
     *
     * @param user User对象
     * @return UserVO对象
     */
    public static UserVO objToVo(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        String tagList = user.getTags();
        if (tagList != null) {
            userVO.setTags(JSONUtil.toList(tagList, String.class));
        }
        String websiteList = user.getWebsite();
        if (websiteList != null) {
            userVO.setWebsite(JSONUtil.toList(websiteList, String.class));
        }
        return userVO;
    }
}
