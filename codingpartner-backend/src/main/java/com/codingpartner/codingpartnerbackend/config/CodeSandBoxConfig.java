package com.codingpartner.codingpartnerbackend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 代码沙箱配置类
 */
@Data
@Component
@ConfigurationProperties(prefix = "codesandbox")
public class CodeSandBoxConfig {
    /**
     * 代码沙箱类型
     */
    private String type;

    /**
     * 代码沙箱实现方式
     */
    private String implementation;

    /**
     * 远程代码沙箱地址
     */
    private String url;

    /**
     * 代码沙箱工厂类
     */
    private String factory;
}
