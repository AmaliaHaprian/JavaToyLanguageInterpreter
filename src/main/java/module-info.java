module com.example.mapgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.mapgui to javafx.fxml;
    exports com.example.mapgui;
}