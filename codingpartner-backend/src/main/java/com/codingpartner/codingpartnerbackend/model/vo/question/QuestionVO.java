package com.codingpartner.codingpartnerbackend.model.vo.question;

import cn.hutool.json.JSONUtil;
import com.codingpartner.codingpartnerbackend.model.entity.JudgeConfig;
import com.codingpartner.codingpartnerbackend.model.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionVO {
    /**
     * id
     */
    private Long id;

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
     * 提交次数
     */
    private Integer submissionCount;

    /**
     * 通过次数
     */
    private Integer acceptedCount;

    /**
     * 点赞数
     */
    private Integer thumbCount;

    /**
     * 收藏数
     */
    private Integer favourCount;

    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 包装类转对象
     *
     * @param questionVO questionVO对象
     * @return Question对象
     */
    public static Question voToObj(QuestionVO questionVO) {
        if (questionVO == null) {
            return null;
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionVO, question);
        List<String> tagsList = questionVO.getTags();
        if (tagsList != null) {
            question.setTags(JSONUtil.toJsonStr(tagsList));
        }
        List<String> languageList = questionVO.getLanguage();
        if (languageList != null) {
            question.setLanguage(JSONUtil.toJsonStr(languageList));
        }
        JudgeConfig judgeConfig = questionVO.getJudgeConfig();
        if (judgeConfig != null) {
            question.setJudgeConfig(JSONUtil.toJsonStr(judgeConfig));
        }
        return question;
    }

    /**
     * 对象转包装类
     *
     * @param question Question对象
     * @return QuestionVO对象
     */
    public static QuestionVO objToVo(Question question) {
        if (question == null) {
            return null;
        }
        QuestionVO questionVO = new QuestionVO();
        BeanUtils.copyProperties(question, questionVO);
        questionVO.setTags(JSONUtil.toList(question.getTags(), String.class));
        questionVO.setLanguage(JSONUtil.toList(question.getLanguage(), String.class));
        questionVO.setJudgeConfig(JSONUtil.toBean(question.getJudgeConfig(), JudgeConfig.class));
        return questionVO;
    }
}
