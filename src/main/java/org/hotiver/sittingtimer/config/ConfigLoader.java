package org.hotiver.sittingtimer.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ConfigLoader {

    private static final String CONFIG_FILE_NAME = "config.json";

    public static Config loadConfig() {
        ObjectMapper mapper = new ObjectMapper();
        File configFile = new File(CONFIG_FILE_NAME);

        try {
            return mapper.readValue(configFile, Config.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
