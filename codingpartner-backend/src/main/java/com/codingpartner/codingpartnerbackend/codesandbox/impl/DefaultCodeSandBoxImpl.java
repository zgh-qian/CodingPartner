package com.codingpartner.codingpartnerbackend.codesandbox.impl;

import com.codingpartner.codingpartnerbackend.codesandbox.CodeSandBox;
import com.codingpartner.codingpartnerbackend.codesandbox.impl.java.JavaDockerSandBoxTemplateImpl;
import com.codingpartner.codingpartnerbackend.codesandbox.impl.java.JavaNativeSandBoxTemplateImpl;
import com.codingpartner.codingpartnerbackend.model.dto.judge.codesandbox.ExectionCodeResponse;
import com.codingpartner.codingpartnerbackend.model.dto.judge.codesandbox.ExecutionCodeRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * 默认代码沙盒实现类
 */
@Slf4j
public class DefaultCodeSandBoxImpl implements CodeSandBox {

    /**
     * 执行
     *
     * @param executionCodeRequest 请求参数
     * @return ExectionCodeResponse
     */
    @Override
    public ExectionCodeResponse execute(ExecutionCodeRequest executionCodeRequest) {
        //log.info("DefaultCodeSandBoxImpl execute:{}", executionCodeRequest);
        ExectionCodeResponse exectionCodeResponse = null;
        // native
        exectionCodeResponse = new JavaNativeSandBoxTemplateImpl().execute(executionCodeRequest);
        // docker
        //exectionCodeResponse = new JavaDockerSandBoxTemplateImpl().execute(executionCodeRequest);
        return exectionCodeResponse;
    }
}
