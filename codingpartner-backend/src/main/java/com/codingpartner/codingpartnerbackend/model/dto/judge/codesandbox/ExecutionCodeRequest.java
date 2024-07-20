package com.codingpartner.codingpartnerbackend.model.dto.judge.codesandbox;

import com.codingpartner.codingpartnerbackend.model.entity.JudgeConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 代码沙箱执行请求
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecutionCodeRequest {

    /**
     * 一组输入数据用例
     */
    private List<String> inputList;

    /**
     * 代码
     */
    private String code;

    /**
     * 编程语言
     */
    private String language;

    /**
     * 题目配置
     */
    private JudgeConfig judgeConfig;
}
