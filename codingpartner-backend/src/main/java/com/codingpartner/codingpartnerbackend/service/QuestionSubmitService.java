package com.codingpartner.codingpartnerbackend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codingpartner.codingpartnerbackend.model.dto.questionSubmit.QuestionSubmitAddRequest;
import com.codingpartner.codingpartnerbackend.model.dto.questionSubmit.QuestionSubmitQueryRequest;
import com.codingpartner.codingpartnerbackend.model.vo.QuestionSubmit.QuestionSubmitVO;
import com.codingpartner.codingpartnerbackend.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author zgh
 * @description 针对表【question_submit(题目提交表)】的数据库操作Service
 * @createDate 2024-04-05 09:54:56
 */
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    /**
     * 添加题目提交记录
     *
     * @param questioSubmitAddRequest 题目提交请求参数
     */
    Long addQuestionSubmit(QuestionSubmitAddRequest questioSubmitAddRequest);

    /**
     * 删除题目提交记录
     *
     * @param ids 题目提交ids
     */
    void deleteQuestionSubmit(Long[] ids);

    /**
     * 根据id获取题目提交记录
     *
     * @param id 题目提交id
     * @return 题目提交记录
     */
    QuestionSubmitVO getQuestionSubmitVOById(Long id);

    /**
     * 分页查询题目提交记录
     *
     * @param questionSubmitQueryRequest 查询请求参数
     * @return 分页查询结果
     */
    Page<QuestionSubmitVO> getAllQuestionSubmitVO(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 根据id获取题目提交记录
     *
     * @param id 题目提交id
     * @return 题目提交记录
     */
    QuestionSubmit getQuestionSubmitById(Long id);

    /**
     * 分页查询题目提交记录
     *
     * @param questionSubmitQueryRequest 查询请求参数
     * @return 分页查询结果
     */
    Page<QuestionSubmit> getAllQuestionSubmit(QuestionSubmitQueryRequest questionSubmitQueryRequest);
}
