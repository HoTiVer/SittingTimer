package org.hotiver.sittingtimer.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigTools {

    private static ObjectMapper mapper = new ObjectMapper();

    private static final String CONFIG_FILE_NAME = "config.json";

    private static final String DEFAULT_CONFIG_TEXT = """
    {
      "workSeconds": 10,
      "restSeconds": 11
    }
    """;

    public static ConfigDto loadConfig() {
        File configFile = new File(CONFIG_FILE_NAME);

        try {
            return mapper.readValue(configFile, ConfigDto.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createConfigFile() {
        try {
            File file = new File("config.json");

            if (!file.exists()) {
                file.createNewFile();
            }

            try (FileWriter writer = new FileWriter(file)) {
                writer.write(DEFAULT_CONFIG_TEXT);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveConfig(ConfigDto configDto) {
        try {
            File file = new File("config.json");

            String newConfigValue = mapper.writeValueAsString(configDto);

            try (FileWriter writer = new FileWriter(file)) {
                writer.write(newConfigValue);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
