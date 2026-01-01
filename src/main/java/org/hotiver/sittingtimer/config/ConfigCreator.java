package org.hotiver.sittingtimer.config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigCreator {

    private static final String DEFAULT_CONFIG_TEXT = """
    {
      "workSeconds": 10,
      "restSeconds": 11
    }
    """;

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
}
