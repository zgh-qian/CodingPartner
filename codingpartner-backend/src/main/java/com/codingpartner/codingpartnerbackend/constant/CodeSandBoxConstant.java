package com.codingpartner.codingpartnerbackend.constant;

import java.util.Arrays;
import java.util.List;

public interface CodeSandBoxConstant {
    /**
     * 成功返回码
     */
    Integer CODE_SUCCESS = 1;

    /**
     * 系统错误返回码
     */
    Integer CODE_ERROR_SYSTEM = 2;

    /**
     * 错误消息返回码
     */
    Integer CODE_ERROR_MESSAGE = 3;

    /**
     * 最大超时时间 (单位：毫秒)
     */
    long MAX_TIME_OUT = 5000L;

    /**
     * 黑名单
     */
    List<String> BLACK_LIST = Arrays.asList(
            "File",
            "Files",
            "Runtime",
            "Process",
            "exec"
    );

    /**
     * 用户目录
     */
    String USER_DIR_NAME = "user.dir";

    /**
     * 用户提交代码临时目录
     */
    String CODE_DIR_NAME = "tmpCode";

    /**
     * 编译前的Java文件目录的Java类名
     */
    String JAVA_CLASS_NAME = "Main.java";

    /**
     * 编译后的Java文件目录的Java类名
     */
    String JAVA_CLASS_NAME_WITHOUT_SUFFIX = "Main";

    /**
     * 编译命令参数
     */
    String COMPILE_COMMAND_PARAM_ENCODING = "-encoding utf-8";


    /**
     * 编译命令 javac -encoding utf-8 %s
     */
    String COMPILE_COMMAND = "javac -encoding utf-8 %s";

    /**
     * 运行命令参数 -Dfile.encoding=UTF-8
     */
    String RUN_COMMAND_PARAM_ENCODING = "-Dfile.encoding=UTF-8";

    /**
     * 运行命令参数 -Xmx256m
     */
    String RUN_COMMAND_PARAM_STACK_SIZE = "-Xmx256m";

    /**
     * 运行命令
     * java -Dfile.encoding=UTF-8 -cp %s Main
     * java -Dfile.encoding=UTF-8 -cp %s Main %s
     * java -Xmx256m -Dfile.encoding=UTF-8 -cp %s;%s -Djava.security.manager=%s Main %s
     */
    String RUN_COMMAND_INTERPRETER = "java -Dfile.encoding=UTF-8 -cp %s Main";

    String RUN_COMMAND_ARGUMENTS = "java -Dfile.encoding=UTF-8 -cp %s Main %s";

    /**
     * Java镜像
     */
    String JAVA_IMAGE = "openjdk:8-alpine";

    /**
     * Java容器路径
     */
    String JAVA_VOLUME_PATH = "/app";

    /**
     * 安全管理器类名
     */
    String SECURITY_MANAGER_CLASS_NAME = "MySecurityManager";
    /**
     * 安全管理器路径
     */
    String SECCOMP_PROFILE_PATH = "/src/main/resources/security/profile.json";
}
