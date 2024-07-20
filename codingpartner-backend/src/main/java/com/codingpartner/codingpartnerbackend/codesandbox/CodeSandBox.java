package com.codingpartner.codingpartnerbackend.codesandbox;

import com.codingpartner.codingpartnerbackend.model.dto.judge.codesandbox.ExectionCodeResponse;
import com.codingpartner.codingpartnerbackend.model.dto.judge.codesandbox.ExecutionCodeRequest;

/**
 * 代码沙箱接口
 */
public interface CodeSandBox {

    /**
     * 执行代码沙箱
     *
     * @param executionCodeRequest 请求参数
     * @return 执行结果
     */
    ExectionCodeResponse execute(ExecutionCodeRequest executionCodeRequest);
}
