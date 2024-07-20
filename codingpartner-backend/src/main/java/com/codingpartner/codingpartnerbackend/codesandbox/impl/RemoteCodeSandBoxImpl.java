package com.codingpartner.codingpartnerbackend.codesandbox.impl;

import com.codingpartner.codingpartnerbackend.codesandbox.CodeSandBox;
import com.codingpartner.codingpartnerbackend.model.dto.judge.codesandbox.ExectionCodeResponse;
import com.codingpartner.codingpartnerbackend.model.dto.judge.codesandbox.ExecutionCodeRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * 远程代码沙盒实现类
 */
@Slf4j
public class RemoteCodeSandBoxImpl implements CodeSandBox {
    @Override
    public ExectionCodeResponse execute(ExecutionCodeRequest executionCodeRequest) {
        log.info("RemoteCodeSandBoxImpl execute:{}", executionCodeRequest);
        return null;
    }
}
