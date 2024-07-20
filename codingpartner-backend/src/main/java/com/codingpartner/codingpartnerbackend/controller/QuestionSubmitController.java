package com.codingpartner.codingpartnerbackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codingpartner.codingpartnerbackend.annotation.AuthCheck;
import com.codingpartner.codingpartnerbackend.common.BaseResponse;
import com.codingpartner.codingpartnerbackend.common.ResultUtils;
import com.codingpartner.codingpartnerbackend.constant.UserConstant;
import com.codingpartner.codingpartnerbackend.model.dto.questionSubmit.QuestionSubmitAddRequest;
import com.codingpartner.codingpartnerbackend.model.dto.questionSubmit.QuestionSubmitQueryRequest;
import com.codingpartner.codingpartnerbackend.model.vo.QuestionSubmit.QuestionSubmitVO;
import com.codingpartner.codingpartnerbackend.model.entity.QuestionSubmit;
import com.codingpartner.codingpartnerbackend.service.QuestionSubmitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.codingpartner.codingpartnerbackend.constant.QuestionSubmitConstant.*;

@RestController
@RequestMapping("/submit/question")
@Api(tags = "题目提交相关接口")
@Slf4j
public class QuestionSubmitController {
    @Resource
    private QuestionSubmitService questionSubmitService;

    /**
     * 添加题目提交记录
     *
     * @param questioSubmitAddRequest 题目提交记录添加请求
     * @return questionSubmitId
     */
    @PostMapping("/add")
    @ApiOperation("添加题目提交记录")
    public BaseResponse<Long> addQuestionSubmit(@RequestBody QuestionSubmitAddRequest questioSubmitAddRequest) {
        Long questionSubmitId=questionSubmitService.addQuestionSubmit(questioSubmitAddRequest);
        return ResultUtils.success(questionSubmitId);
    }

    /**
     * 删除题目提交记录
     *
     * @param ids 题目提交记录id
     * @return QUESTION_SUBMIT_DELETED_SUCCESS
     */
    @DeleteMapping("/delete")
    @ApiOperation("删除题目提交记录")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<String> deleteQuestionSubmit(@RequestParam(value = "ids") Long[] ids) {
        questionSubmitService.deleteQuestionSubmit(ids);
        return ResultUtils.success(QUESTION_SUBMIT_DELETED_SUCCESS);
    }

    /**
     * 获取脱敏题目提交记录
     *
     * @param questionSubmitQueryRequest 题目提交记录查询请求
     * @return 题目提交记录
     */
    @PostMapping("/get/vo")
    @ApiOperation("获取本人脱敏题目提交记录")
    public BaseResponse<QuestionSubmitVO> getQuestionSubmitVOById(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest) {
        QuestionSubmitVO questionSubmitVO = questionSubmitService.getQuestionSubmitVOById(questionSubmitQueryRequest.getId());
        return ResultUtils.success(questionSubmitVO);
    }

    /**
     * 获取脱敏题目提交记录列表
     *
     * @param questionSubmitQueryRequest 题目提交记录查询请求
     * @return 题目提交记录列表
     */
    @PostMapping("/get/list/vo")
    @ApiOperation("获取本人脱敏题目提交记录列表")
    public BaseResponse<Page<QuestionSubmitVO>> getAllQuestionSubmitVO(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest) {
        Page<QuestionSubmitVO> questionSubmitVOList = questionSubmitService.getAllQuestionSubmitVO(questionSubmitQueryRequest);
        return ResultUtils.success(questionSubmitVOList);
    }

    /**
     * 获取题目提交记录
     *
     * @param id 题目提交记录id
     * @return 题目提交记录
     */
    @PostMapping("/get")
    @ApiOperation("管理员获取题目提交记录")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<QuestionSubmit> getQuestionSubmitById(@RequestParam(value = "id") Long id) {
        QuestionSubmit questionSubmit = questionSubmitService.getQuestionSubmitById(id);
        return ResultUtils.success(questionSubmit);
    }

    /**
     * 获取题目提交记录列表
     *
     * @param questionSubmitQueryRequest 题目提交记录查询请求
     * @return 题目提交记录列表
     */
    @PostMapping("/get/list")
    @ApiOperation("管理员获取题目提交记录列表")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<QuestionSubmit>> getAllQuestionSubmit(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest) {
        Page<QuestionSubmit> questionSubmitVOList = questionSubmitService.getAllQuestionSubmit(questionSubmitQueryRequest);
        return ResultUtils.success(questionSubmitVOList);
    }
}
