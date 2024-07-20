package com.codingpartner.codingpartnerbackend.model.dto.judge.codesandbox;

import lombok.Data;

/**
 * 进程执行信息
 */
@Data
public class ExecuteMessage {
    /**
     * 错误码
     */
    private Integer exitCode;

    /**
     * 正常的进程输出信息
     */
    private String message;

    /**
     * 错误的进程输出信息
     */
    private String errorMessage;

    /**
     * 执行时间
     */
    private Long time;

    /**
     * 内存消耗
     */
    private Long memory;
}
