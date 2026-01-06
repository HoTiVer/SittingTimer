package org.hotiver.sittingtimer;

import javafx.application.Application;
import javafx.application.Platform;
import org.hotiver.sittingtimer.app.TimerApplication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Launcher {
    private static final int PORT = 45678;
    private static ServerSocket serverSocket;
    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(PORT);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try { serverSocket.close(); } catch (IOException ignored) {}
            }));

            new Thread(() -> {
                while (!serverSocket.isClosed()) {
                    try {
                        serverSocket.accept();
                        Platform.runLater(TimerApplication::focusExistingWindow);
                    } catch (IOException e) {
                        if (!serverSocket.isClosed()) e.printStackTrace();
                    }
                }
            }).start();

            Application.launch(TimerApplication.class, args);

        } catch (IOException e) {
            try {
                new Socket("localhost", PORT).close();
            } catch (IOException ex) {
                System.err.println("Failed to notify existing instance");
            }
            System.exit(0);
        }
    }
}
