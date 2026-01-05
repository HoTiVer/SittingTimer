package org.hotiver.sittingtimer.service;

import javafx.application.Platform;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TrayService {
    private static TrayService instance;
    private TrayIcon trayIcon;
    private SystemTray tray;

    private TrayService() {}

    public static TrayService getInstance() {
        if (instance == null) {
            instance = new TrayService();
        }
        return instance;
    }

    public void setupTray(Stage stage) {
        if (!SystemTray.isSupported()) {
            return;
        }

        this.tray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit()
                .getImage(getClass().getResource("/tray-icon.jpg"));

        PopupMenu popup = new PopupMenu();
        MenuItem openItem = new MenuItem("Open");
        MenuItem exitItem = new MenuItem("Exit");

        openItem.addActionListener(e -> { showStage(stage); });
        exitItem.addActionListener(e -> {
            tray.remove(trayIcon);
            Platform.exit();
            System.exit(0);
        });

        popup.add(openItem);
        popup.addSeparator();
        popup.add(exitItem);

        this.trayIcon = new TrayIcon(image, "Timer app", popup);
        this.trayIcon.setImageAutoSize(true);

        this.trayIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    showStage(stage);
                }
            }
        });

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void showNotification(String title, String message) {
        if (trayIcon != null) {
            trayIcon.displayMessage(title, message, TrayIcon.MessageType.NONE);
        }
    }

    private void showStage(Stage stage) {
        Platform.runLater(() -> {
            stage.show();
            stage.setIconified(false);
            stage.toFront();
            stage.requestFocus();
        });
    }

    public void openByTrayNotification(Stage stage) {
        trayIcon.addActionListener(e -> {
            Platform.runLater(() -> {
                stage.show();
                stage.setIconified(false);
                stage.toFront();
            });
        });
    }
}
