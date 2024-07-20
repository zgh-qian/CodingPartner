package com.codingpartner.codingpartnerbackend.model.dto.questionSubmit;

import com.codingpartner.codingpartnerbackend.common.PageRequest;
import com.codingpartner.codingpartnerbackend.model.entity.JudgeInfo;
import lombok.Data;

@Data
public class QuestionSubmitQueryRequest extends PageRequest {
    /**
     * id
     */
    private Long id;
    /**
     * 编程语言
     */
    private String language;

    /**
     * 执行结果，比如：AC，WA，TLE，MLE，RE
     */
    private String result;

    /**
     * 判题状态：0-待判题，1-判题中，2-判题成功，3-判题失败
     */
    private Integer judgeStatus;

    /**
     * 是否通过：0-未通过，1-通过
     */
    private Integer isPass;
}
