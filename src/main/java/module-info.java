module org.hotiver.sittingtimer {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.hotiver.sittingtimer to javafx.fxml;
    exports org.hotiver.sittingtimer;
}