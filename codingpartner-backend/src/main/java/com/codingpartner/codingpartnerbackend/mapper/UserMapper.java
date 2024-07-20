package com.codingpartner.codingpartnerbackend.mapper;

import com.codingpartner.codingpartnerbackend.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zgh
 * @description 针对表【user(用户表)】的数据库操作Mapper
 * @createDate 2024-04-04 08:26:51
 * @Entity com.codingpartner.codingpartnerbackend.model.entity.User
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




