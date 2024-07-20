package com.codingpartner.codingpartnerbackend.model.dto.judge;

import com.codingpartner.codingpartnerbackend.model.entity.JudgeCase;
import com.codingpartner.codingpartnerbackend.model.entity.JudgeInfo;
import com.codingpartner.codingpartnerbackend.model.entity.Question;
import com.codingpartner.codingpartnerbackend.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * JudgeContext: 上下文，用于定义JudgeStrategy传递的参数
 */
@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private Question question;

    private List<JudgeCase> judgeCaseList;

    private QuestionSubmit questionSubmit;
}
