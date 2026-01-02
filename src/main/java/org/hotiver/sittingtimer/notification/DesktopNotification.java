package org.hotiver.sittingtimer.notification;

import java.awt.*;

public class DesktopNotification {

    public static void showNotification(String title, String message) {
        if (!SystemTray.isSupported()) {
            System.out.println("System tray not supported!");
            return;
        }

        try {
            SystemTray tray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
            TrayIcon trayIcon = new TrayIcon(image, "Timer App");
            trayIcon.setImageAutoSize(true);
            tray.add(trayIcon);

            trayIcon.displayMessage(title, message, TrayIcon.MessageType.INFO);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
