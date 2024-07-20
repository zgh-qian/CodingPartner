package com.codingpartner.codingpartnerbackend.strategy.impl;

import cn.hutool.json.JSONUtil;
import com.codingpartner.codingpartnerbackend.model.dto.judge.JudgeContext;
import com.codingpartner.codingpartnerbackend.model.entity.*;
import com.codingpartner.codingpartnerbackend.model.enums.JudgeInfoMessageEnum;
import com.codingpartner.codingpartnerbackend.strategy.JudgeStrategy;

import java.util.List;
import java.util.Optional;

/**
 * java判题策略实现类
 */
public class JavaJudgeStrategyImpl implements JudgeStrategy {
    /**
     * java判题策略实现类
     *
     * @param judgeContext 判题上下文
     * @return 判题结果
     */
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        // 根据上下文获取数据
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        Question question = judgeContext.getQuestion();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        // 获取判题信息
        Long memory = Optional.ofNullable(judgeInfo.getMemory()).orElse(0L); // 内存，如果为空，则默认为0
        Long time = Optional.ofNullable(judgeInfo.getTime()).orElse(0L); // 时间，如果为空，则默认为0
        String errorInput = judgeInfo.getErrorInput();
        // 初始化判题信息
        JudgeInfo judgeInfoResponse = new JudgeInfo();
        judgeInfoResponse.setMessage(JudgeInfoMessageEnum.ACCEPTED.getValue());
        judgeInfoResponse.setMemory(memory);
        judgeInfoResponse.setTime(time);
        judgeInfoResponse.setErrorInput(errorInput);
        // 如果输出用例和输入列表长度不一致，说明判题失败
        if (outputList.size() != inputList.size()) {
            judgeInfoResponse.setMessage(JudgeInfoMessageEnum.WRONG_ANSWER.getValue());
            return judgeInfoResponse;
        }
        // 遍历输出列表和输入列表，如果不一致，说明判题失败
        for (int i = 0; i < judgeCaseList.size(); i++) {
            JudgeCase judgeCase = judgeCaseList.get(i);
            if (!judgeCase.getOutput().equals(outputList.get(i))) {
                judgeInfoResponse.setMessage(JudgeInfoMessageEnum.WRONG_ANSWER.getValue());
                judgeInfoResponse.setErrorInput(judgeCase.getInput());
                return judgeInfoResponse;
            }
        }
        // 判断题目限制
        JudgeConfig judgeConfig = JSONUtil.toBean(question.getJudgeConfig(), JudgeConfig.class);
        Long timeLimit = judgeConfig.getTimeLimit();
        Long memoryLimit = judgeConfig.getMemoryLimit();
        // 判断时间限制
        if (time > timeLimit) {
            judgeInfoResponse.setMessage(JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED.getValue());
        }
        // 判断内存限制
        if (memory > memoryLimit) {
            judgeInfoResponse.setMessage(JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED.getValue());
        }
        // 判题通过
        return judgeInfoResponse;
    }
}
