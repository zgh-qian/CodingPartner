package com.codingpartner.codingpartnerbackend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codingpartner.codingpartnerbackend.model.dto.question.QuestionAddRequest;
import com.codingpartner.codingpartnerbackend.model.dto.question.QuestionQueryRequest;
import com.codingpartner.codingpartnerbackend.model.dto.question.QuestionUpdateRequest;
import com.codingpartner.codingpartnerbackend.model.entity.Question;
import com.baomidou.mybatisplus.extension.service.IService;
import com.codingpartner.codingpartnerbackend.model.vo.question.QuestionVO;

/**
 * @author zgh
 * @description 针对表【question(题目表)】的数据库操作Service
 * @createDate 2024-04-04 22:54:38
 */
public interface QuestionService extends IService<Question> {
    /**
     * 添加题目
     *
     * @param questionAddRequest 题目添加请求
     */
    void addQuestion(QuestionAddRequest questionAddRequest);

    /**
     * 删除题目
     *
     * @param id 题目id
     */
    void deleteQuestion(Long[] id);

    /**
     * 更新题目
     *
     * @param questionUpdateRequest 题目更新请求
     */
    void updateQuestion(QuestionUpdateRequest questionUpdateRequest);

    /**
     * 根据id获取题目
     *
     * @param id 题目id
     * @return 题目
     */
    Question getQuestionById(Long id);

    /**
     * 根据id获取脱敏问题
     *
     * @param id 题目id
     * @return 脱敏问题
     */
    QuestionVO getQuestionByIdVO(Long id);

    /**
     * 分页查询题目列表
     *
     * @param questionQueryRequest 查询请求
     * @return 分页查询结果
     */
    Page<Question> getQuestionList(QuestionQueryRequest questionQueryRequest);

    /**
     * 分页查询脱敏题目列表
     *
     * @param questionQueryRequest 查询请求
     * @return 分页查询结果
     */
    Page<QuestionVO> getQuestionVOList(QuestionQueryRequest questionQueryRequest);
}
