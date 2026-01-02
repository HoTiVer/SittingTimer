module org.hotiver.sittingtimer {
    requires javafx.media;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires com.fasterxml.jackson.databind;


    opens org.hotiver.sittingtimer to javafx.fxml;
    exports org.hotiver.sittingtimer;
    exports org.hotiver.sittingtimer.controller;
    opens org.hotiver.sittingtimer.controller to javafx.fxml;
    exports org.hotiver.sittingtimer.app;
    opens org.hotiver.sittingtimer.app to javafx.fxml;

    opens org.hotiver.sittingtimer.config to com.fasterxml.jackson.databind;
}