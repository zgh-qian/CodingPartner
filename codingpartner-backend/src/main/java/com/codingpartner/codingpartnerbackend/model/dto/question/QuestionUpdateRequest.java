package com.codingpartner.codingpartnerbackend.model.dto.question;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.codingpartner.codingpartnerbackend.model.entity.JudgeCase;
import com.codingpartner.codingpartnerbackend.model.entity.JudgeConfig;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class QuestionUpdateRequest {
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
     * 判题用例(json数组)
     */
    private List<JudgeCase> judgeCase;

    /**
     * 答案
     */
    private String answer;

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
     * 用户id
     */
    private Long userId;
}