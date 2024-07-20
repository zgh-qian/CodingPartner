package com.codingpartner.codingpartnerbackend.codesandbox.proxy;

import com.codingpartner.codingpartnerbackend.codesandbox.CodeSandBox;
import com.codingpartner.codingpartnerbackend.model.dto.judge.codesandbox.ExectionCodeResponse;
import com.codingpartner.codingpartnerbackend.model.dto.judge.codesandbox.ExecutionCodeRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * 代码沙箱代理类，增强代码沙箱类
 */
@Slf4j
public class CodeSandBoxProxy implements CodeSandBox {
    private final CodeSandBox codeSandBox;


    public CodeSandBoxProxy(CodeSandBox codeSandBox) {
        this.codeSandBox = codeSandBox;
    }

    @Override
    public ExectionCodeResponse execute(ExecutionCodeRequest executionCodeRequest) {
        log.info("代码沙箱请求信息：{}", executionCodeRequest);
        ExectionCodeResponse exectionCodeResponse = codeSandBox.execute(executionCodeRequest);
        log.info("代码沙箱响应信息：{}", exectionCodeResponse.toString());
        return exectionCodeResponse;
    }
}
