package com.codingpartner.codingpartnerbackend.codesandbox.impl.java;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.dfa.FoundWord;
import cn.hutool.dfa.WordTree;
import com.codingpartner.codingpartnerbackend.codesandbox.CodeSandBox;
import com.codingpartner.codingpartnerbackend.model.dto.judge.codesandbox.ExectionCodeResponse;
import com.codingpartner.codingpartnerbackend.model.dto.judge.codesandbox.ExecuteMessage;
import com.codingpartner.codingpartnerbackend.model.dto.judge.codesandbox.ExecutionCodeRequest;
import com.codingpartner.codingpartnerbackend.model.entity.JudgeConfig;
import com.codingpartner.codingpartnerbackend.model.entity.JudgeInfo;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.codingpartner.codingpartnerbackend.constant.CodeSandBoxConstant.*;

/**
 * java代码沙箱模板
 */
public abstract class JavaSandBoxTemplate implements CodeSandBox {

    /**
     * 词树，用于过滤敏感词
     */
    private static final WordTree WORD_TREE;

    /**
     * 静态代码块，初始化词树
     */
    static {
        WORD_TREE = new WordTree();
        WORD_TREE.addWords(BLACK_LIST);
    }

    /**
     * 执行代码
     *
     * @param executionCodeRequest 请求参数
     * @return 执行结果
     */
    @Override
    public ExectionCodeResponse execute(ExecutionCodeRequest executionCodeRequest) {
        List<String> inputList = executionCodeRequest.getInputList();
        JudgeConfig judgeConfig = executionCodeRequest.getJudgeConfig();
        String code = executionCodeRequest.getCode();
        FoundWord foundWord = WORD_TREE.matchWord(code);
        if (foundWord != null) {
            return getErrorMessage(new RuntimeException("代码中包含敏感词：" + foundWord.getFoundWord()));
        }
        // 1. 保存用户代码到文件
        File userCodeFile = saveCodeToFile(code);
        // 2. 编译代码
        ExecuteMessage compileMessage = compileFileToClass(userCodeFile);
        if (compileMessage.getExitCode() != 0) {
            deleteCodeFile(userCodeFile);
            return getErrorMessage(new RuntimeException("编译失败"));
        }
        // 3. 执行代码
        List<ExecuteMessage> executeMessageList = runClassGetOutput(userCodeFile, inputList, judgeConfig);
        // 4. 获取执行结果
        ExectionCodeResponse exectionCodeResponse = getOutputResponse(executeMessageList);
        // 5. 删除用户代码文件
        boolean del = deleteCodeFile(userCodeFile);
        if (!del) {
            return getErrorMessage(new RuntimeException("删除用户代码文件失败"));
        }
        // 6. 返回执行结果
        return exectionCodeResponse;
    }

    /**
     * 1. 保存用户代码到文件
     *
     * @param code 用户代码
     * @return 文件对象
     */
    public File saveCodeToFile(String code) {
        String userDir = System.getProperty(USER_DIR_NAME);
        String globalCodePathName = userDir + File.separator + CODE_DIR_NAME;
        // 判断是否存在全局代码命令，如果不存在则创建
        if (!FileUtil.exist(globalCodePathName)) {
            FileUtil.mkdir(globalCodePathName);
        }
        // 保存用户代码，隔离存放
        String userCodeParentPath = globalCodePathName + File.separator + UUID.randomUUID();
        String userCodePath = userCodeParentPath + File.separator + JAVA_CLASS_NAME;
        return FileUtil.writeString(code, userCodePath, StandardCharsets.UTF_8);
    }

    /**
     * 2. 编译代码
     *
     * @param userCodeFile 用户java代码文件
     * @return 编译结果
     */
    public ExecuteMessage compileFileToClass(File userCodeFile) {
        String compileCmd = String.format(COMPILE_COMMAND, userCodeFile.getAbsoluteFile());
        ExecuteMessage executeMessage = null;
        try {
            Process compileProcess = Runtime.getRuntime().exec(compileCmd);
            executeMessage = JavaProcessUtil.compileJavaCode(compileProcess);
            if (executeMessage.getExitCode() != 0) {
                return executeMessage;
            }
        } catch (IOException e) {
            executeMessage.setExitCode(0);
            return executeMessage;
        }
        return executeMessage;
    }

    /**
     * 3. 执行代码
     *
     * @param userCodeFile 用户class代码文件
     * @param inputList    输入用例
     * @return 执行结果
     */
    public List<ExecuteMessage> runClassGetOutput(File userCodeFile, List<String> inputList, JudgeConfig judgeConfig) {
        // 设置超时时间
        long MaxTime = judgeConfig.getTimeLimit() + 1000L;
        String userCodeParentPath = userCodeFile.getParentFile().getAbsolutePath();
        List<ExecuteMessage> executeMessageList = new ArrayList<>();
        for (String input : inputList) {
            // 设置最大内存限制 -Xmx256m
            // 如果是交互式
            String runCmd = String.format(RUN_COMMAND_INTERPRETER, userCodeParentPath);
            // 如果是非交互式
            //String runCmd = String.format(RUN_COMMAND_ARGUMENTS, userCodeParentPath, input);
            try {
                Process process = Runtime.getRuntime().exec(runCmd);
                // 超时控制
                Thread thread = new Thread(() -> {
                    try {
                        Thread.sleep(MaxTime);
                        process.destroy();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                thread.start();
                // 如果是交互式
                ExecuteMessage executeMessage = JavaProcessUtil.executeJavaCodeInteractive(process, input);
                // 如果是非交互式
                //ExecuteMessage executeMessage = JavaProcessUtil.executeJavaCodeNonInteractive(process);
                executeMessageList.add(executeMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return executeMessageList;
    }

    /**
     * 4. 获取执行结果
     *
     * @param executeMessageList 执行结果列表
     * @return 执行结果
     */
    public ExectionCodeResponse getOutputResponse(List<ExecuteMessage> executeMessageList) {
        ExectionCodeResponse exectionCodeResponse = new ExectionCodeResponse();
        List<String> outputList = new ArrayList<>();
        Long maxTime = 0L;
        for (ExecuteMessage executeMessage : executeMessageList) {
            String errorMessage = executeMessage.getErrorMessage();
            if (StrUtil.isNotBlank(errorMessage)) {
                exectionCodeResponse.setMessage(errorMessage);
                exectionCodeResponse.setStatus(CODE_ERROR_MESSAGE);
                break;
            }
            // 获取最大时间
            Long time = executeMessage.getTime();
            if (time != null) {
                maxTime = Math.max(maxTime, time);
            }
            // 获取输出信息
            outputList.add(executeMessage.getMessage());
        }
        // 正常运行完成
        if (outputList.size() == executeMessageList.size()) {
            exectionCodeResponse.setStatus(CODE_SUCCESS);
        }
        JudgeInfo judgeInfo = new JudgeInfo();
        // 设置最大时间
        judgeInfo.setTime(maxTime);
        // 设置JudgeInfo
        exectionCodeResponse.setJudgeInfo(judgeInfo);
        // 设置输出信息
        exectionCodeResponse.setOutputList(outputList);
        return exectionCodeResponse;
    }

    /**
     * 5. 删除用户代码文件
     *
     * @param userCodeFile 用户代码文件
     * @return 删除结果
     */
    public boolean deleteCodeFile(File userCodeFile) {
        String userCodeParentPath = userCodeFile.getParentFile().getAbsolutePath();
        boolean del = true;
        if (userCodeParentPath != null) {
            del = FileUtil.del(userCodeParentPath);
        }
        return del;
    }

    public ExectionCodeResponse getErrorMessage(Throwable e) {
        ExectionCodeResponse exectionCodeResponse = new ExectionCodeResponse();
        exectionCodeResponse.setOutputList(new ArrayList<>());
        exectionCodeResponse.setMessage(e.getMessage());
        exectionCodeResponse.setStatus(CODE_ERROR_SYSTEM);
        exectionCodeResponse.setJudgeInfo(new JudgeInfo());
        return exectionCodeResponse;

    }
}
