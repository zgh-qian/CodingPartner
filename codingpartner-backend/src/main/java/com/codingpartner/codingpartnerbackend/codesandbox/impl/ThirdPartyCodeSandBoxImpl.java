package com.codingpartner.codingpartnerbackend.codesandbox.impl;

import com.codingpartner.codingpartnerbackend.codesandbox.CodeSandBox;
import com.codingpartner.codingpartnerbackend.model.dto.judge.codesandbox.ExectionCodeResponse;
import com.codingpartner.codingpartnerbackend.model.dto.judge.codesandbox.ExecutionCodeRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * 第三方代码沙盒实现类
 */
@Slf4j
public class ThirdPartyCodeSandBoxImpl implements CodeSandBox {
    @Override
    public ExectionCodeResponse execute(ExecutionCodeRequest executionCodeRequest) {
        log.info("ThirdPartyCodeSandBoxImpl execute:{}", executionCodeRequest);
        return null;
    }
}
