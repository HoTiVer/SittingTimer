package org.hotiver.sittingtimer.app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hotiver.sittingtimer.config.ConfigTools;
import org.hotiver.sittingtimer.service.TrayService;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TimerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Platform.setImplicitExit(false);

        File configFile = new File("config.json");
        if (!configFile.exists()) {
            ConfigTools.createConfigFile();
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Timer.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 420, 340);
        stage.setTitle("Timer app");
        stage.setScene(scene);
        stage.setResizable(false);

        TrayService.getInstance().setupTray(stage);

        stage.setOnCloseRequest(event -> {
            event.consume();
            stage.hide();
        });
        stage.show();
    }
}
