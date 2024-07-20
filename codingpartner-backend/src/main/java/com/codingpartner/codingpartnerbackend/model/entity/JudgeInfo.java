package com.codingpartner.codingpartnerbackend.model.entity;

import lombok.Data;

/**
 * 判题信息
 */
@Data
public class JudgeInfo {
    /**
     * 程序执行信息
     */
    private String message;

    /**
     * 消耗内存(KB)
     */
    private Long memory;

    /**
     * 消耗时间(ms)
     */
    private Long time;

    /**
     * 出错的用例输入
     */
    private String errorInput;
}
