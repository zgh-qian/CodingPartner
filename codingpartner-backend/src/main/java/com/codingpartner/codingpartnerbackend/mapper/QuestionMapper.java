package com.codingpartner.codingpartnerbackend.mapper;

import com.codingpartner.codingpartnerbackend.model.entity.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zgh
 * @description 针对表【question(题目表)】的数据库操作Mapper
 * @createDate 2024-04-04 22:54:38
 * @Entity com.codingpartner.codingpartnerbackend.model.entity.Question
 */
@Mapper
public interface QuestionMapper extends BaseMapper<Question> {

}




