module com.example.mapgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.example.mapgui;


    opens com.example.mapgui to javafx.fxml;
    exports com.example.mapgui;
}