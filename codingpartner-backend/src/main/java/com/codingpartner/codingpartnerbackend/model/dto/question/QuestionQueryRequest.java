package com.codingpartner.codingpartnerbackend.model.dto.question;

import com.codingpartner.codingpartnerbackend.common.PageRequest;
import lombok.Data;

import java.util.List;

@Data
public class QuestionQueryRequest extends PageRequest {
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
     * 提交排序
     */
    private String submissionOrder;

    /**
     * 通过排序
     */
    private String acceptedOrder;

    /**
     * 点赞排序
     */
    private String thumbOrder;

    /**
     * 收藏排序
     */
    private String favourOrder;

    /**
     * 评论排序
     */
    private String commentOrder;
}
