package com.isxcode.acorn.demo4.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;

import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
public class ShellUtils {

    public static void executeCommand(String command, String logPath) {

        CommandLine cmdLine = new CommandLine("/opt/flink/bin/flink");
        cmdLine.addArgument("run");
        cmdLine.addArgument("/home/dehoop/acorn/tmp/executeIdIsxcode/target/flinkJob-1.0.0.jar");
        DefaultExecutor executor = new DefaultExecutor();
        executor.setExitValues(null);

        try {
            // 设置日志路径
            FileOutputStream fileOutputStream = new FileOutputStream(logPath, true);
            PumpStreamHandler streamHandler = new PumpStreamHandler(fileOutputStream, fileOutputStream, null);
            executor.setStreamHandler(streamHandler);

            // 设置watchdog，防止阻塞
            ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);
            executor.setWatchdog(watchdog);

            // 执行命令
            int exitValue = executor.execute(cmdLine);
            log.info(String.valueOf(exitValue));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}