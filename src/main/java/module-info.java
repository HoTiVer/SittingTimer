module org.hotiver.sittingtimer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;


    opens org.hotiver.sittingtimer to javafx.fxml;
    exports org.hotiver.sittingtimer;
    exports org.hotiver.sittingtimer.controller;
    opens org.hotiver.sittingtimer.controller to javafx.fxml;
    exports org.hotiver.sittingtimer.app;
    opens org.hotiver.sittingtimer.app to javafx.fxml;
}