package com.codingpartner.codingpartnerbackend.codesandbox.factory;

import com.codingpartner.codingpartnerbackend.codesandbox.CodeSandBox;
import com.codingpartner.codingpartnerbackend.codesandbox.proxy.CodeSandBoxProxy;
import com.codingpartner.codingpartnerbackend.codesandbox.impl.DefaultCodeSandBoxImpl;
import com.codingpartner.codingpartnerbackend.codesandbox.impl.RemoteCodeSandBoxImpl;
import com.codingpartner.codingpartnerbackend.codesandbox.impl.ThirdPartyCodeSandBoxImpl;

/**
 * 代码沙箱单例工厂类
 */
public class SingletonCodeSandBoxFactory {

    /**
     * 私有静态变量，用于保存唯一实例
     */
    private static SingletonCodeSandBoxFactory instance;

    /**
     * 私有构造函数，防止外部直接实例化
     */
    private SingletonCodeSandBoxFactory() {
    }

    /**
     * 公有静态方法，用于获取唯一实例
     *
     * @return 代码沙箱实例
     */
    public static synchronized SingletonCodeSandBoxFactory getInstance() {
        // 如果实例为空，则创建新实例
        if (instance == null) {
            instance = new SingletonCodeSandBoxFactory();
        }
        return instance;
    }

    /**
     * 代码沙箱单例工厂类
     *
     * @param type 代码沙箱类型
     * @return 代码沙箱实例
     */
    public CodeSandBox newInstanceBySingletonFactory(String type) {
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
     * 代码沙箱单例工厂代理类
     *
     * @param type 代码沙箱类型
     * @return 代码沙箱代理类
     */
    public CodeSandBox newInstanceBySingletonFactoryProxy(String type) {
        return new CodeSandBoxProxy(newInstanceBySingletonFactory(type));
    }
}
