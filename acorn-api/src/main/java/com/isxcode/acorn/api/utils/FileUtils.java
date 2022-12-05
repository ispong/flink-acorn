package com.isxcode.acorn.api.utils;

import com.isxcode.acorn.api.exception.AcornException;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.IOUtils;

public class FileUtils {

    /**
     * translate windows path to str
     *
     * @param pathStr pathStr
     * @return windows path
     */
    public static String translateWindowsPath(String pathStr) {

        return pathStr.contains("\\") ? pathStr.replace("\\", "\\\\") : pathStr;
    }

    /**
     * generate dirs
     *
     * @param dirPath dirPath
     */
    public static void generateDirs(String dirPath) {

        Path dir = Paths.get(translateWindowsPath(dirPath));
        if (!Files.exists(dir)) {
            try {
                Files.createDirectories(dir);
            } catch (IOException e) {
                throw new AcornException("500", "create dir path error");
            }
        }
    }

    /**
     * generate file
     *
     * @param filePath filePath
     * @return filePath
     */
    public static Path generateFile(String filePath) {

        // generate dir
        int endSeparator = filePath.lastIndexOf(File.separator);
        String dirPath = filePath.substring(0, endSeparator);
        generateDirs(dirPath);

        // generate file
        Path file = Paths.get(filePath);
        if (!Files.exists(file)) {
            try {
                return Files.createFile(file);
            } catch (IOException e) {
                throw new AcornException("500", "create file path error");
            }
        } else {
            return file;
        }
    }

    /**
     * write string to file or new file
     *
     * @param content strContent
     * @param dirPath dir
     * @param fileName fileName+suffix
     * @param options StandardOpenOption.WRITE/StandardOpenOption.APPEND
     */
    public static void StringToFile(String content, String dirPath, String fileName, OpenOption... options) {

        // generate filePath
        Path file = generateFile(dirPath + File.separator + fileName);

        // write string to file
        try {
            Files.write(file, content.getBytes(), options);
        } catch (IOException e) {
            throw new AcornException("500", "write content error");
        }
    }


    /**
     * copy resources dir file to new file
     *
     * @param resourceFilePath resourceFilePath
     * @param filePath filePath
     * @param options options
     */
    public static void copyResourceFile(String resourceFilePath, String filePath, OpenOption... options) {

        DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
        Resource resource = defaultResourceLoader.getResource(resourceFilePath);

        Path file = generateFile(filePath);
        try {
            Files.write(file, IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8).getBytes(), options);
        } catch (IOException e) {
            throw new AcornException("500", "copy resource file error");
        }
    }

    /**
     * write string to file or new file
     *
     * @param content  strContent
     * @param filePath file
     * @param options  StandardOpenOption.WRITE/StandardOpenOption.APPEND
     */
    public static void StringToFile(String content, String filePath, OpenOption... options) {

        // split filePath
        int endSeparator = filePath.lastIndexOf(File.separator);
        String dirPath = filePath.substring(0, endSeparator);
        String fileName = filePath.substring(endSeparator + 1);

        //  generate file
        StringToFile(content, dirPath, fileName, options);
    }

    /**
     * recursion delete file
     *
     * @param path path
     */
    public static void RecursionDeleteFile(Path path) {

        try {
            if (Files.isDirectory(path)) {
                Files.list(path).forEach(FileUtils::RecursionDeleteFile);
            }
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new AcornException("500", "delete dir error");
        }
    }
}
