/**
 * Java FX controls
 */
module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    opens Model to javafx.base;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}