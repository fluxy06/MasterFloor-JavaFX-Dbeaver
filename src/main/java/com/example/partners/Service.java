package com.example.partners;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Service {

    public void openForm(String NameForm, Stage CurrentStage) throws IOException {
        FXMLLoader fxml_loader = new FXMLLoader(HelloApplication.class.getResource(NameForm));

        Scene scene = new Scene(fxml_loader.load(), 837, 563);
        Stage stage = new Stage();
        stage.setScene(scene);
        if (CurrentStage != null) {
            CurrentStage.close();
        }
        stage.show();
    }

    public static void ErorMessage(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void WarningMessage(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
