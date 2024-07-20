package com.codingpartner.codingpartnerbackend.codesandbox.impl.java;

import com.codingpartner.codingpartnerbackend.model.dto.judge.codesandbox.ExectionCodeResponse;
import com.codingpartner.codingpartnerbackend.model.dto.judge.codesandbox.ExecutionCodeRequest;

/**
 * Java原生代码沙箱实现，直接调用父类JavaSandBoxTemplate的execute方法
 */
public class JavaNativeSandBoxTemplateImpl extends JavaSandBoxTemplate {
    @Override
    public ExectionCodeResponse execute(ExecutionCodeRequest executionCodeRequest) {
        return super.execute(executionCodeRequest);
    }
}
