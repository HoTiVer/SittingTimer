package org.hotiver.sittingtimer.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import org.hotiver.sittingtimer.config.ConfigDto;
import org.hotiver.sittingtimer.config.ConfigTools;


public class TimerController {
    @FXML
    private Label timerLabel;
    @FXML
    private Label infoLabel;
    @FXML
    private Button startButton;
    @FXML
    private Button muteButton;
    @FXML
    private VBox settingsPane;
    @FXML
    private TextField workSecondsField;
    @FXML
    private TextField restSecondsField;
    @FXML
    private Slider volumeSlider;


    private Timeline workTimeline;
    private Timeline restTimeline;

    private MediaPlayer soundPlayer;

    private ConfigDto configDto;

    private int workSeconds;
    private int restSeconds;

    private boolean running = false;
    private boolean isWorkCompleted = false;
    private boolean isMuted = false;

    @FXML
    public void initialize() {
        configDto = ConfigTools.loadConfig();
        workSeconds = configDto.workSeconds;
        restSeconds = configDto.restSeconds;
        Media sound = new Media(getClass().getResource("/notification.wav").toExternalForm());
        soundPlayer = new MediaPlayer(sound);

        if (isWorkCompleted) {
            infoLabel.setText("Time to rest");
            timerLabel.setText(formatTime(restSeconds));
        }
        else {
            infoLabel.setText("Time to work");
            timerLabel.setText(formatTime(workSeconds));
        }

        workTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (workSeconds > 0) {
                workSeconds--;
                timerLabel.setText(formatTime(workSeconds));
            } else {
                playSoundNotification();
                initializeRestTimer();
            }
        }));
        workTimeline.setCycleCount(Timeline.INDEFINITE);

        restTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (restSeconds > 0) {
                restSeconds--;
                timerLabel.setText(formatTime(restSeconds));
            } else  {
                playSoundNotification();
                initializeWorkTimer();
            }
        }));
        restTimeline.setCycleCount(Timeline.INDEFINITE);
    }

    @FXML
    private void toggleTimer() {
        if (isWorkCompleted) {
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
        volumeSlider.setValue(configDto.soundVolume);
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
            double soundVolume = volumeSlider.getValue();

            if (work < 0 || rest < 0) {
                return;
            }

            ConfigDto configDto = new ConfigDto(work, rest, soundVolume);
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
        isWorkCompleted = true;
        infoLabel.setText("Time to rest");
    }

    private void initializeWorkTimer() {
        restTimeline.stop();
        startButton.setText("Start Timer");
        running = false;
        workSeconds = configDto.workSeconds;
        timerLabel.setText(formatTime(workSeconds));
        isWorkCompleted = false;
        infoLabel.setText("Time to work");
    }

    private void playSoundNotification() {
        soundPlayer.setVolume(configDto.soundVolume);

        if (!isMuted)
            soundPlayer.play();
    }

    @FXML
    private void toggleMute() {
        isMuted = !isMuted;
        muteButton.setText(isMuted ? "On" : "Off");
    }
}
