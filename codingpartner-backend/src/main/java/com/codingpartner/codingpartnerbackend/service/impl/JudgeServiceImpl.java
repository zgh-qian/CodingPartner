package com.codingpartner.codingpartnerbackend.service.impl;

import cn.hutool.json.JSONUtil;
import com.codingpartner.codingpartnerbackend.codesandbox.CodeSandBox;
import com.codingpartner.codingpartnerbackend.codesandbox.factory.SingletonCodeSandBoxFactory;
import com.codingpartner.codingpartnerbackend.codesandbox.factory.StaticCodeSandBoxFactory;
import com.codingpartner.codingpartnerbackend.common.ErrorCode;
import com.codingpartner.codingpartnerbackend.config.CodeSandBoxConfig;
import com.codingpartner.codingpartnerbackend.exception.BusinessException;
import com.codingpartner.codingpartnerbackend.manager.JudgeManager;
import com.codingpartner.codingpartnerbackend.model.dto.judge.JudgeContext;
import com.codingpartner.codingpartnerbackend.model.dto.judge.codesandbox.ExectionCodeResponse;
import com.codingpartner.codingpartnerbackend.model.dto.judge.codesandbox.ExecutionCodeRequest;
import com.codingpartner.codingpartnerbackend.model.entity.*;
import com.codingpartner.codingpartnerbackend.model.enums.JudgeInfoMessageEnum;
import com.codingpartner.codingpartnerbackend.model.enums.QuestionSubmitStatusEnum;
import com.codingpartner.codingpartnerbackend.service.JudgeService;
import com.codingpartner.codingpartnerbackend.service.QuestionService;
import com.codingpartner.codingpartnerbackend.service.QuestionSubmitService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 判题服务实现类
 */
@Service
public class JudgeServiceImpl implements JudgeService {
    @Resource
    private CodeSandBoxConfig codeSandBoxConfig;

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private JudgeManager judgeManager;

    /**
     * 判题服务
     *
     * @param questionSubmitId 问题提交ID
     * @return 判题结果
     */
    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        // 1. 获取提交记录和题目
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交记录不存在");
        }
        Question question = questionService.getById(questionSubmit.getQuestionId());
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        // 2. 如果不为等待状态
        if (!questionSubmit.getJudgeStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中");
        }
        // 3. 更新题目提交为运行中
        QuestionSubmit questionSubmitUpdate1 = new QuestionSubmit();
        questionSubmitUpdate1.setId(questionSubmitId);
        questionSubmitUpdate1.setJudgeStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionSubmitService.updateById(questionSubmitUpdate1);
        if (!update) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目状态更新失败");
        }
        // 4. 调用代码沙箱，获取判题结果
        CodeSandBox codeSandBox = null;
        if (codeSandBoxConfig.getFactory().equals("static")) {
            // 静态工厂模式
            codeSandBox = StaticCodeSandBoxFactory.newInstanceByStaticFactoryProxy(codeSandBoxConfig.getType());
        } else if (codeSandBoxConfig.getFactory().equals("singleton")) {
            // 单例工厂模式
            codeSandBox = SingletonCodeSandBoxFactory.getInstance().newInstanceBySingletonFactory(codeSandBoxConfig.getType());
        } else {
            // 默认单例工厂模式
            codeSandBox = SingletonCodeSandBoxFactory.getInstance().newInstanceBySingletonFactory(codeSandBoxConfig.getType());
        }
        // 获取编程语言
        String language = questionSubmit.getLanguage();
        // 获取代码
        String code = questionSubmit.getCode();
        // 获取判题配置
        JudgeConfig judgeConfig = JSONUtil.toBean(question.getJudgeConfig(), JudgeConfig.class);
        // 获取判题用例
        List<JudgeCase> judgeCaseList = JSONUtil.toList(question.getJudgeCase(), JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        // 调用代码沙箱
        ExecutionCodeRequest executionCodeRequest = ExecutionCodeRequest
                .builder()
                .language(language)
                .code(code)
                .judgeConfig(judgeConfig)
                .inputList(inputList)
                .build();
        ExectionCodeResponse exectionCodeResponse = codeSandBox.execute(executionCodeRequest);
        if (exectionCodeResponse == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "代码沙箱执行失败");
        }
        List<String> outputList = exectionCodeResponse.getOutputList();
        // 5.根据代码沙箱的执行结果，设置题目提交的判题结果
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(exectionCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setQuestion(question);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestionSubmit(questionSubmit);
        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);
        // 6.更新题目提交的判题结果
        QuestionSubmit questionSubmitUpdate2 = new QuestionSubmit();
        questionSubmitUpdate2.setId(questionSubmitId);
        questionSubmitUpdate2.setJudgeStatus(QuestionSubmitStatusEnum.SUCCESS.getValue());
        questionSubmitUpdate2.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        if (judgeInfo.getMessage().equals(JudgeInfoMessageEnum.ACCEPTED.getValue())) {
            questionSubmitUpdate2.setIsPass(1);
        }
        boolean update2 = questionSubmitService.updateById(questionSubmitUpdate2);
        if (!update2) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目提交状态更新失败");
        }
        // 7. 返回题目提交结果
        return questionSubmitService.getById(questionSubmitId);
    }
}
