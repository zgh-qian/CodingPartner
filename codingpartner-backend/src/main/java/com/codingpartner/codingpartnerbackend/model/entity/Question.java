package com.codingpartner.codingpartnerbackend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 题目表
 *
 * @TableName question
 */
@TableName(value = "question")
@Data
public class Question implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
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
    private String tags;

    /**
     * 支持的编程语言(json数组)
     */
    private String language;

    /**
     * 内容
     */
    private String content;

    /**
     * 判题配置(json对象)
     */
    private String judgeConfig;

    /**
     * 判题用例(json数组)
     */
    private String judgeCase;

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

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}