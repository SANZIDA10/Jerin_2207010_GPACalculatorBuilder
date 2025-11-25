package com.example._207010;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class ResultController {
private DB db = new DB();
    @FXML
    private TableView<CourseController.Course> courseTable;

    @FXML
    private TableColumn<CourseController.Course, String> nameCol, codeCol, teacher1Col, teacher2Col, gradeCol;

    @FXML
    private TableColumn<CourseController.Course, Double> creditCol;

    @FXML
    private Label totalGpaLabel;

    @FXML
    public void initialize() {
        nameCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getName()));
        codeCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getCode()));
        creditCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleObjectProperty<>(cell.getValue().getCredit()));
        teacher1Col.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getTeacher1()));
        teacher2Col.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getTeacher2()));
        gradeCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getGrade()));
    }

    public void setCourseData(ObservableList<CourseController.Course> courses) {
        courseTable.setItems(courses);
        calculateGPA(courses);
    }

    private void calculateGPA(ObservableList<CourseController.Course> courses) {
        double totalPoints = 0;
        double totalCredits = 0;

        for (CourseController.Course c : courses) {
            totalPoints += c.getCredit() * gradeToPoint(c.getGrade());
            totalCredits += c.getCredit();
        }

        double gpa = totalCredits == 0 ? 0 : totalPoints / totalCredits;
        totalGpaLabel.setText(String.format("%.2f", gpa));
    }

    private double gradeToPoint(String grade) {
        return switch (grade) {
            case "A" -> 4.0;
            case "A-" -> 3.7;
            case "B+" -> 3.3;
            case "B" -> 3.0;
            case "B-" -> 2.7;
            case "C+" -> 2.3;
            case "C" -> 2.0;
            case "D" -> 1.0;
            case "F" -> 0.0;
            default -> 0.0;
        };
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        db.insertNote("fgh",
                "kbbkh");



        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/_207010/CourseEntry.fxml"));
        Parent root = loader.load();

        Scene courseScene = new Scene(root);
        courseScene.getStylesheets().add(getClass().getResource("/com/example/_207010/Styles.css").toExternalForm());

        Stage stage = new Stage();
        stage.setTitle("GPA Calculator - Course Entry");
        stage.setScene(courseScene);
        stage.show();

        // Close current Result window
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
}
