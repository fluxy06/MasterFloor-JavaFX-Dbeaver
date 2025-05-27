module com.example.partners {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.partners to javafx.fxml;
    exports com.example.partners;
}