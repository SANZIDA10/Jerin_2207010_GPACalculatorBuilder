package com.example._207010;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application
{
    private DB d = new DB();

    @Override
    public void start(Stage a) throws IOException
    {
        FXMLLoader c = new FXMLLoader(Main.class.getResource("Home.fxml"));
        Scene e = new Scene(c.load());
        e.getStylesheets().add(getClass().getResource("/com/example/_207010/Styles.css").toExternalForm());
        a.setTitle("My GPA Calculator!");
        a.setScene(e);
        a.show();

        d.getConnection();
    }
}