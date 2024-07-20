package com.codingpartner.codingpartnerbackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codingpartner.codingpartnerbackend.annotation.AuthCheck;
import com.codingpartner.codingpartnerbackend.annotation.AuthQuestion;
import com.codingpartner.codingpartnerbackend.common.BaseResponse;
import com.codingpartner.codingpartnerbackend.common.ResultUtils;
import com.codingpartner.codingpartnerbackend.model.dto.question.QuestionAddRequest;
import com.codingpartner.codingpartnerbackend.model.dto.question.QuestionQueryRequest;
import com.codingpartner.codingpartnerbackend.model.dto.question.QuestionUpdateRequest;
import com.codingpartner.codingpartnerbackend.model.entity.Question;
import com.codingpartner.codingpartnerbackend.model.vo.question.QuestionVO;
import com.codingpartner.codingpartnerbackend.service.QuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


import static com.codingpartner.codingpartnerbackend.constant.QuestionConstant.*;

@RestController
@RequestMapping("/question")
@Api(tags = "题目相关接口")
@Slf4j
public class QuestionController {

    @Resource
    private QuestionService questionService;

    /**
     * 创建题目
     *
     * @param questionAddRequest 题目创建请求
     * @return QUESTION_CREATE_SUCCESS
     */
    @PostMapping("/add")
    @ApiOperation("创建题目")
    public BaseResponse<String> addQuestion(@RequestBody QuestionAddRequest questionAddRequest) {
        questionService.addQuestion(questionAddRequest);
        return ResultUtils.success(QUESTION_CREATE_SUCCESS);
    }

    /**
     * 删除题目
     *
     * @param ids 题目ids
     * @return QUESTION_DELETE_SUCCESS
     */
    @DeleteMapping("/delete")
    @ApiOperation("删除题目")
    @AuthQuestion(param = "ids")
    public BaseResponse<String> deleteQuestion(@RequestParam("ids") Long[] ids) {
        questionService.deleteQuestion(ids);
        return ResultUtils.success(QUESTION_DELETE_SUCCESS);
    }

    /**
     * 更新题目
     *
     * @param questionUpdateRequest 题目更新请求
     * @return QUESTION_UPDATE_SUCCESS
     */
    @PutMapping("/update")
    @ApiOperation("更新题目")
    @AuthQuestion(param = "question")
    public BaseResponse<String> updateQuestion(@RequestBody QuestionUpdateRequest questionUpdateRequest) {
        questionService.updateQuestion(questionUpdateRequest);
        return ResultUtils.success(QUESTION_UPDATE_SUCCESS);
    }

    /**
     * 获取题目详情
     *
     * @param id 题目id
     * @return 题目
     */
    @GetMapping("/get")
    @ApiOperation("获取题目详情")
    @AuthQuestion
    public BaseResponse<Question> getQuestionById(@RequestParam("id") Long id) {
        Question question = questionService.getQuestionById(id);
        return ResultUtils.success(question);
    }

    /**
     * 获取脱敏题目
     *
     * @param id 题目id
     * @return 脱敏题目
     */
    @GetMapping("/get/vo")
    @ApiOperation("获取脱敏题目")
    public BaseResponse<QuestionVO> getQuestionByIdVO(@RequestParam("id") Long id) {
        QuestionVO questionVO = questionService.getQuestionByIdVO(id);
        return ResultUtils.success(questionVO);
    }

    /**
     * 获取题目列表
     *
     * @param questionQueryRequest 题目查询请求
     * @return 题目列表
     */
    @PostMapping("/list")
    @ApiOperation("获取题目详情列表")
    @AuthCheck
    public BaseResponse<Page<Question>> getQuestionList(@RequestBody QuestionQueryRequest questionQueryRequest) {
        Page<Question> questionPage = questionService.getQuestionList(questionQueryRequest);
        return ResultUtils.success(questionPage);
    }

    /**
     * 获取脱敏题目列表
     *
     * @param questionQueryRequest 题目查询请求
     * @return 脱敏题目列表
     */
    @PostMapping("/list/vo")
    @ApiOperation("获取脱敏题目列表")
    public BaseResponse<Page<QuestionVO>> getQuestionVOList(@RequestBody QuestionQueryRequest questionQueryRequest) {
        Page<QuestionVO> questionVOPage = questionService.getQuestionVOList(questionQueryRequest);
        return ResultUtils.success(questionVOPage);
    }

    // todo 题目相关接口，比如：获取题目数量，不同类型数量等
}
