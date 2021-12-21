package com.isxcode.acorn.plugin.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class AcornFileUtils {

    public static void RecursionDeleteFile(Path path) {

        try {
            if (Files.isDirectory(path)) {
                Files.list(path).forEach(AcornFileUtils::RecursionDeleteFile);
            }
            log.debug("删除文件" + path.getFileName());
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
