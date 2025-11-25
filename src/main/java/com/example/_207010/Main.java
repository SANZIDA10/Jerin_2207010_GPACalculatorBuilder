package com.example._207010;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private DB db = new DB();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Home.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("/com/example/_207010/Styles.css").toExternalForm());
        stage.setTitle("My GPA Calculator!");
        stage.setScene(scene);
        stage.show();

        db.getConnection();
    }
}
