package com.isxcode.acorn.demo4.utils;

import java.io.IOException;

public class ShellUtils {

    public static void executeCommand(String executeId, String command, String logPath) {

        try {
            logPath = logPath + "/" + executeId + ".log";
            Runtime.getRuntime().exec(new String[]{"nohup", command, ">> " + logPath + " 2>&1 &"});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
