package org.hotiver.sittingtimer.app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.hotiver.sittingtimer.config.ConfigTools;
import org.hotiver.sittingtimer.service.TrayService;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TimerApplication extends Application {
    private static Stage primaryStage;
    @Override
    public void start(Stage stage) throws IOException {
        Platform.setImplicitExit(false);

        File configFile = new File("config.json");
        if (!configFile.exists()) {
            ConfigTools.createConfigFile();
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Timer.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root, 420, 340);
        scene.setFill(Paint.valueOf("#000000"));
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        stage.setTitle("Timer app");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/tray-icon.jpg")));

        stage.initStyle(StageStyle.TRANSPARENT);
        final double[] xOffset = new double[1];
        final double[] yOffset = new double[1];

        root.setOnMousePressed(event -> {
            xOffset[0] = event.getSceneX();
            yOffset[0] = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset[0]);
            stage.setY(event.getScreenY() - yOffset[0]);
        });

        TrayService.getInstance().setupTray(stage);
        TrayService.getInstance().openByTrayNotification(stage);

        primaryStage = stage;
        stage.show();
    }

    public static void focusExistingWindow() {
        if (primaryStage != null) {
            primaryStage.toFront();
            primaryStage.requestFocus();
        }
    }
}
