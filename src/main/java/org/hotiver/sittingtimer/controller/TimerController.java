package org.hotiver.sittingtimer.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;


public class TimerController {
    @FXML
    private Label timerLabel;
    @FXML
    private Button startButton;

    private Timeline workTimeline;
    private Timeline restTimeline;

    private final int workSecondsValue = 10;
    private final int restSecondsValue = 11;
    private int workSeconds = workSecondsValue;
    private int restSeconds = restSecondsValue;

    private boolean running = false;
    private boolean workCompleted = false;

    @FXML
    public void initialize() {
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
        restSeconds = restSecondsValue;
        timerLabel.setText(formatTime(restSeconds));
        workCompleted = true;
    }

    private void initializeWorkTimer() {
        restTimeline.stop();
        startButton.setText("Start Timer");
        running = false;
        workSeconds = workSecondsValue;
        timerLabel.setText(formatTime(workSeconds));
        workCompleted = false;
    }
}
