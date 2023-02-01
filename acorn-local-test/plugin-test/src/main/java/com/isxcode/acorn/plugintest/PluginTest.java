package com.isxcode.acorn.plugintest;

import com.isxcode.acorn.plugin.sql.SqlJob;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class PluginTest {

    private static final Logger log = LogManager.getLogger("PluginTest");

    public static void main(String[] args) {

        log.error("hello");
        SqlJob.main(new String[0]);

    }
}
