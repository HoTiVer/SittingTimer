package org.hotiver.sittingtimer.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.hotiver.sittingtimer.config.ConfigDto;
import org.hotiver.sittingtimer.config.ConfigTools;


public class TimerController {
    @FXML
    private Label timerLabel;
    @FXML
    private Button startButton;
    @FXML
    private VBox settingsPane;

    private Timeline workTimeline;
    private Timeline restTimeline;

    private ConfigDto configDto;

    @FXML private TextField workSecondsField;
    @FXML private TextField restSecondsField;

    private int workSeconds;
    private int restSeconds;

    private boolean running = false;
    private boolean workCompleted = false;

    @FXML
    public void initialize() {
        configDto = ConfigTools.loadConfig();
        workSeconds = configDto.workSeconds;
        restSeconds = configDto.restSeconds;

        timerLabel.setText(formatTime(workSeconds));

        workTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (workSeconds > 0) {
                workSeconds--;
                timerLabel.setText(formatTime(workSeconds));
            } else {
                initializeRestTimer();
            }
        }));
        workTimeline.setCycleCount(Timeline.INDEFINITE);

        restTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (restSeconds > 0) {
                restSeconds--;
                timerLabel.setText(formatTime(restSeconds));
            } else  {
                initializeWorkTimer();
            }
        }));
        restTimeline.setCycleCount(Timeline.INDEFINITE);
    }

    @FXML
    private void toggleTimer() {
        if (workCompleted) {
            if (!running) {
                restTimeline.play();
                startButton.setText("Stop Timer");
                running = true;
            } else {
                restTimeline.stop();
                startButton.setText("Start Timer");
                running = false;
            }
        }
        else {
            if (!running) {
                workTimeline.play();
                startButton.setText("Stop Timer");
                running = true;
            } else {
                workTimeline.stop();
                startButton.setText("Start Timer");
                running = false;
            }
        }
    }

    @FXML
    private void openSettings() {
        workSecondsField.setText(String.valueOf(configDto.workSeconds));
        restSecondsField.setText(String.valueOf(configDto.restSeconds));
        settingsPane.setVisible(true);
        settingsPane.setMouseTransparent(false);
    }

    @FXML
    private void closeSettings() {
        settingsPane.setVisible(false);
        settingsPane.setMouseTransparent(true);
    }

    @FXML
    private void saveSettings() {
        try {
            int work = Integer.parseInt(workSecondsField.getText());
            int rest = Integer.parseInt(restSecondsField.getText());

            if (work < 0 || rest < 0) {
                return;
            }

            ConfigDto configDto = new ConfigDto(work, rest);
            ConfigTools.saveConfig(configDto);

            initialize();

            closeSettings();

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private String formatTime(int totalSeconds) {
        int h = totalSeconds / 3600;
        int m = (totalSeconds % 3600) / 60;
        int s = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", h, m, s);
    }

    private void initializeRestTimer() {
        workTimeline.stop();
        startButton.setText("Start Timer");
        running = false;
        restSeconds = configDto.restSeconds;
        timerLabel.setText(formatTime(restSeconds));
        workCompleted = true;
    }

    private void initializeWorkTimer() {
        restTimeline.stop();
        startButton.setText("Start Timer");
        running = false;
        workSeconds = configDto.workSeconds;
        timerLabel.setText(formatTime(workSeconds));
        workCompleted = false;
    }
}
