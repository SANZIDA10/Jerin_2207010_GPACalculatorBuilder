package com.example._207010;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    @FXML
    private void handleStart(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/_207010/CourseEntry.fxml"));
        Parent root = loader.load();

        Scene courseScene = new Scene(root);
        courseScene.getStylesheets().add(getClass().getResource("/com/example/_207010/Styles.css").toExternalForm());

        Stage stage = new Stage();
        stage.setTitle("GPA Calculator - Course Entry");
        stage.setScene(courseScene);
        stage.show();

        Stage currentStage = (Stage) ((javafx.scene.control.Button) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
}
