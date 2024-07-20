package com.codingpartner.codingpartnerbackend.strategy;

import com.codingpartner.codingpartnerbackend.model.dto.judge.JudgeContext;
import com.codingpartner.codingpartnerbackend.model.entity.JudgeInfo;

/**
 * 判题策略接口
 */
public interface JudgeStrategy {

    /**
     * 执行判题
     *
     * @param judgeContext 判题上下文
     * @return 判题结果
     */
    JudgeInfo doJudge(JudgeContext judgeContext);
}
