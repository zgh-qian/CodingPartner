package com.codingpartner.codingpartnerbackend.model.dto.question;

import com.codingpartner.codingpartnerbackend.model.entity.JudgeCase;
import com.codingpartner.codingpartnerbackend.model.entity.JudgeConfig;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class QuestionAddRequest {
    /**
     * 标题
     */
    private String title;

    /**
     * 难度
     */
    private String difficulty;

    /**
     * 标签(json数组)
     */
    private List<String> tags;

    /**
     * 支持的编程语言(json数组)
     */
    private List<String> language;

    /**
     * 内容
     */
    private String content;

    /**
     * 判题配置(json对象)
     */
    private JudgeConfig judgeConfig;

    /**
     * 判题用例(json数组)
     */
    private List<JudgeCase> judgeCase;

    /**
     * 答案
     */
    private String answer;
}
