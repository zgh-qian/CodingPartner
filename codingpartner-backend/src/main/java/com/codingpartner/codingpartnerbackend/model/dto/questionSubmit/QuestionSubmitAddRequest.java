package com.codingpartner.codingpartnerbackend.model.dto.questionSubmit;

import lombok.Data;


@Data
public class QuestionSubmitAddRequest {

    /**
     * 题目id
     */
    private Long questionId;

    /**
     * 编程语言
     */
    private String language;

    /**
     * 代码
     */
    private String code;
}
