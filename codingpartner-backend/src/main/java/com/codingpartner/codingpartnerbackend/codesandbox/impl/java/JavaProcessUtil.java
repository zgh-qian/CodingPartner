package com.codingpartner.codingpartnerbackend.codesandbox.impl.java;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.util.StrUtil;
import com.codingpartner.codingpartnerbackend.model.dto.judge.codesandbox.ExecuteMessage;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JavaProcessUtil {
    /**
     * 编译Java代码
     *
     * @param process 编译Java代码的进程
     * @return 编译结果
     */
    public static ExecuteMessage compileJavaCode(Process process) {
        ExecuteMessage executeMessage = new ExecuteMessage();
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            int exitValue = process.waitFor();
            executeMessage.setExitCode(exitValue);
            if (exitValue == 0) {
                // 正常退出，编译成功
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                List<String> outputStrList = new ArrayList<>();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    outputStrList.add(line);
                }
                // 设置进程的正常输出
                executeMessage.setMessage(StringUtils.join(outputStrList, '\n').toString());
            } else {
                // 异常退出，编译失败
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                List<String> errorStrList = new ArrayList<>();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    errorStrList.add(line);
                }
                // 设置进程的错误输出
                executeMessage.setMessage(StringUtils.join(errorStrList, '\n').toString());
            }
            stopWatch.stop();
            // 设置执行时间
            executeMessage.setTime(stopWatch.getTotalTimeMillis());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return executeMessage;
    }

    /**
     * 非交互式执行Java代码
     *
     * @param process Java代码执行的进程
     * @return 执行结果
     */
    public static ExecuteMessage executeJavaCodeNonInteractive(Process process) {
        return compileJavaCode(process);
    }

    /**
     * 交互式执行Java代码
     *
     * @param process Java代码交互式执行的进程
     * @param input   input参数
     * @return 交互式执行结果
     */
    public static ExecuteMessage executeJavaCodeInteractive(Process process, String input) {
        ExecuteMessage executeMessage = new ExecuteMessage();
        try {
            StopWatch stopWatch = new StopWatch();
            // 向进程输入数据
            OutputStream outputStream = process.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            outputStreamWriter.write(input + "\n");
            // 刷新缓冲区
            outputStreamWriter.flush();
            // 回车后才会执行，开始计时
            stopWatch.start();
            // 获取进程的输出
            InputStream inputStream = process.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder executeOutputStringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                executeOutputStringBuilder.append(line);
            }
            executeMessage.setMessage(executeOutputStringBuilder.toString());
            stopWatch.stop();
            // 设置执行时间
            executeMessage.setTime(stopWatch.getTotalTimeMillis());
            // 等待进程退出
            outputStreamWriter.close();
            outputStream.close();
            inputStream.close();
            process.destroy();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return executeMessage;
    }
}
