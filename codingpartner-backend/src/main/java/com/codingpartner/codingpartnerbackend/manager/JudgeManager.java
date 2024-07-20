package com.codingpartner.codingpartnerbackend.manager;

import com.codingpartner.codingpartnerbackend.model.dto.judge.JudgeContext;
import com.codingpartner.codingpartnerbackend.model.entity.JudgeInfo;
import com.codingpartner.codingpartnerbackend.model.enums.CodeLanguageEnum;
import com.codingpartner.codingpartnerbackend.strategy.JudgeStrategy;
import com.codingpartner.codingpartnerbackend.strategy.impl.DefaultJudgeStrategyImpl;
import com.codingpartner.codingpartnerbackend.strategy.impl.JavaJudgeStrategyImpl;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class JudgeManager {

    /**
     * 执行判题
     *
     * @param judgeContext 上下文
     * @return 判题结果
     */
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        String language = judgeContext.getQuestionSubmit().getLanguage();
        JudgeStrategy judgeStrategy = null;
        if (language.equals(CodeLanguageEnum.JAVA.getText())) {
            judgeStrategy = new JavaJudgeStrategyImpl();
        } else {
            judgeStrategy = new DefaultJudgeStrategyImpl();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
