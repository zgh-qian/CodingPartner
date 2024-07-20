package com.codingpartner.codingpartnerbackend.codesandbox.factory;

import com.codingpartner.codingpartnerbackend.codesandbox.CodeSandBox;
import com.codingpartner.codingpartnerbackend.codesandbox.proxy.CodeSandBoxProxy;
import com.codingpartner.codingpartnerbackend.codesandbox.impl.DefaultCodeSandBoxImpl;
import com.codingpartner.codingpartnerbackend.codesandbox.impl.RemoteCodeSandBoxImpl;
import com.codingpartner.codingpartnerbackend.codesandbox.impl.ThirdPartyCodeSandBoxImpl;

/**
 * 代码沙箱静态工厂类
 */
public class StaticCodeSandBoxFactory {
    /**
     * 代码沙箱静态工厂类
     *
     * @param type 代码沙箱类型
     * @return 代码沙箱实例
     */
    public static CodeSandBox newInstanceByStaticFactory(String type) {
        CodeSandBox codeSandBox = null;
        switch (type) {
            case "default":
                codeSandBox = new DefaultCodeSandBoxImpl();
                break;
            case "remote":
                codeSandBox = new RemoteCodeSandBoxImpl();
                break;
            case "thirdparty":
                codeSandBox = new ThirdPartyCodeSandBoxImpl();
                break;
            default:
                codeSandBox = new DefaultCodeSandBoxImpl();
                break;
        }
        return codeSandBox;
    }

    /**
     * 代码沙箱静态工厂代理类
     *
     * @param type 代码沙箱类型
     * @return 代码沙箱代理实例
     */
    public static CodeSandBox newInstanceByStaticFactoryProxy(String type) {
        return new CodeSandBoxProxy(newInstanceByStaticFactory(type));
    }
}
