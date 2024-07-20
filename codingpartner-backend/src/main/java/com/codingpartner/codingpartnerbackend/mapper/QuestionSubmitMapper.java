package com.codingpartner.codingpartnerbackend.mapper;

import com.codingpartner.codingpartnerbackend.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author zgh
* @description 针对表【question_submit(题目提交表)】的数据库操作Mapper
* @createDate 2024-04-05 09:54:56
* @Entity com.codingpartner.codingpartnerbackend.model.entity.QuestionSubmit
*/
@Mapper
public interface QuestionSubmitMapper extends BaseMapper<QuestionSubmit> {

}




