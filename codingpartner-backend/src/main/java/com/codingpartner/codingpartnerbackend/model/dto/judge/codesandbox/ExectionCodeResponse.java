package com.codingpartner.codingpartnerbackend.model.dto.judge.codesandbox;

import com.codingpartner.codingpartnerbackend.model.entity.JudgeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 代码沙箱返回结果
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExectionCodeResponse {

    /**
     * 一组输出用例结果
     */
    private List<String> outputList;

    /**
     * 执行信息
     */
    private String message;
    /**
     * 执行状态
     */
    private Integer status;

    /**
     * 判题信息
     */
    private JudgeInfo judgeInfo;

    /**
     * 出错的用例输入
     */
    private String errorInput;
}
