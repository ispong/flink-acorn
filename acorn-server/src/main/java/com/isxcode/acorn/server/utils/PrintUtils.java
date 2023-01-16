package com.isxcode.acorn.server.utils;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public class PrintUtils {

    public static void printErrorLog(String errMsg) {

        StringBuilder tmpStr = new StringBuilder();
        
        int consolePreLength = 20;
        int logLength = (consolePreLength * 2) + errMsg.length() + (errMsg.getBytes(StandardCharsets.UTF_8).length - errMsg.length()) / 3;
        for (int i = 0; i < logLength; i++) {
            tmpStr.append("=");
        }
        System.out.println(tmpStr);

        tmpStr = new StringBuilder();
        for (int i = 0; i < consolePreLength; i++) {
            tmpStr.append(" ");
        }
        System.out.println(tmpStr.append(errMsg));
        tmpStr = new StringBuilder();
        for (int i = 0; i < logLength; i++) {
            tmpStr.append("=");
        }
        System.out.println(tmpStr.append("\n"));
    }
}
