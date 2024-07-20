package com.codingpartner.codingpartnerbackend.model.vo.QuestionSubmit;

import cn.hutool.json.JSONUtil;
import com.codingpartner.codingpartnerbackend.model.entity.JudgeInfo;
import com.codingpartner.codingpartnerbackend.model.entity.QuestionSubmit;
import com.codingpartner.codingpartnerbackend.model.vo.question.QuestionVO;
import com.codingpartner.codingpartnerbackend.model.vo.user.UserVO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class QuestionSubmitVO {
    /**
     * 编程语言
     */
    private String language;

    /**
     * 代码
     */
    private String code;

    /**
     * 判题信息(json对象)
     */
    private JudgeInfo judgeInfo;

    /**
     * 判题状态：0-待判题，1-判题中，2-判题成功，3-判题失败
     */
    private Integer judgeStatus;

    /**
     * 是否通过：0-未通过，1-通过
     */
    private Integer isPass;

    /**
     * 提交用户信息
     */
    private UserVO userVO;

    /**
     * 题目信息
     */
    private QuestionVO questionVO;

    /**
     * 包装类转对象
     *
     * @param questionSubmitVO QuestionSubmitVO对象
     * @return QuestionSubmit对象
     */
    public static QuestionSubmit voToObj(QuestionSubmitVO questionSubmitVO) {
        if (questionSubmitVO == null) {
            return null;
        }
        QuestionSubmit questionSubmit = new QuestionSubmit();
        BeanUtils.copyProperties(questionSubmitVO, questionSubmit);
        JudgeInfo judgeInfo = questionSubmitVO.getJudgeInfo();
        if (judgeInfo != null) {
            questionSubmit.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        }
        return questionSubmit;
    }

    /**
     * 对象转包装类
     *
     * @param questionSubmit QuestionSubmit对象
     * @return QuestionSubmitVO对象
     */
    public static QuestionSubmitVO objToVo(QuestionSubmit questionSubmit) {
        if (questionSubmit == null) {
            return null;
        }
        QuestionSubmitVO questionSubmitVO = new QuestionSubmitVO();
        BeanUtils.copyProperties(questionSubmit, questionSubmitVO);
        questionSubmitVO.setJudgeInfo(JSONUtil.toBean(questionSubmit.getJudgeInfo(), JudgeInfo.class));
        return questionSubmitVO;
    }
}
