package org.hotiver.sittingtimer.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.hotiver.sittingtimer.config.ConfigDto;
import org.hotiver.sittingtimer.config.ConfigTools;
import org.hotiver.sittingtimer.service.TrayService;


public class TimerController {
    @FXML
    private Label timerLabel;
    @FXML
    private Label infoLabel;
    @FXML
    private Label overlayMessage;
    @FXML
    private Button startButton;
    @FXML
    private Button muteButton;
    @FXML
    private VBox settingsPane;
    @FXML
    private VBox overlayPane;
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
    private boolean overlayVisible = false;

    @FXML
    public void initialize() {
        configDto = ConfigTools.loadConfig();
        workSeconds = configDto.workSeconds;
        restSeconds = configDto.restSeconds;
        Media sound = new Media(getClass().getResource("/notification.wav").toExternalForm());
        soundPlayer = new MediaPlayer(sound);

        muteButton.setText(configDto.isMuted ? "On" : "Off");
        changeMuteBackground();

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
                initializeNotification("Timer", "Time to rest");
                initializeRestTimer();
            }
        }));
        workTimeline.setCycleCount(Timeline.INDEFINITE);

        restTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (restSeconds > 0) {
                restSeconds--;
                timerLabel.setText(formatTime(restSeconds));
            } else  {
                initializeNotification("Timer", "Time to work");
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
                startButton.setText("Stop");
                running = true;
            } else {
                restTimeline.stop();
                startButton.setText("Start");
                running = false;
            }
        }
        else {
            if (!running) {
                workTimeline.play();
                startButton.setText("Stop");
                running = true;
            } else {
                workTimeline.stop();
                startButton.setText("Start");
                running = false;
            }
        }
    }

    @FXML
    public void skipSession() {
        if (isWorkCompleted) {
            initializeWorkTimer();
        }
        else {
            initializeRestTimer();
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

            ConfigDto configDto = new ConfigDto(work, rest, soundVolume, this.configDto.isMuted);
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
        startButton.setText("Start");
        running = false;
        restSeconds = configDto.restSeconds;
        timerLabel.setText(formatTime(restSeconds));
        isWorkCompleted = true;
        infoLabel.setText("Time to rest");
    }

    private void initializeWorkTimer() {
        restTimeline.stop();
        startButton.setText("Start");
        running = false;
        workSeconds = configDto.workSeconds;
        timerLabel.setText(formatTime(workSeconds));
        isWorkCompleted = false;
        infoLabel.setText("Time to work");
    }

    private void initializeNotification(String title, String message) {
        playSoundNotification();
        TrayService.getInstance().showNotification(title, message);
        showOverlay(message);
    }

    private void playSoundNotification() {
        ConfigDto dto = ConfigTools.loadConfig();
        if (!dto.isMuted) {
            soundPlayer.setVolume(configDto.soundVolume);
            soundPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            soundPlayer.play();
        }
    }

    @FXML
    private void toggleMute() {
        configDto.isMuted = !configDto.isMuted;
        ConfigTools.saveConfig(configDto);
        muteButton.setText(configDto.isMuted ? "On" : "Off");
        changeMuteBackground();
    }

    private void changeMuteBackground() {
        if (configDto.isMuted) {
            muteButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: black;");
        } else {
            muteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        }
    }

    private void showOverlay(String message) {
        overlayMessage.setText(message);
        overlayPane.setVisible(true);
        overlayPane.setMouseTransparent(false);
        overlayVisible = true;
    }

    @FXML
    private void closeOverlay() {
        overlayPane.setVisible(false);
        overlayPane.setMouseTransparent(true);
        overlayVisible = false;
        soundPlayer.stop();
    }

    @FXML
    private void minimizeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void hideToTray(javafx.event.ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.hide();
    }
}
