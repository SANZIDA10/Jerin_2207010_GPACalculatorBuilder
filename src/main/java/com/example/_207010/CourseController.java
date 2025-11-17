package com.example._207010;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class CourseController {

    @FXML
    private TextField totalCreditField, nameField, codeField, creditField, teacher1Field, teacher2Field;

    @FXML
    private ComboBox<String> gradeBox;

    @FXML
    private Button calculateButton;

    private final ObservableList<Course> courseList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Populate gradeBox
        gradeBox.getItems().addAll("A", "A-", "B+", "B", "B-", "C+", "C", "D", "F");
    }

    @FXML
    private void handleAddCourse() {
        String name = nameField.getText();
        String code = codeField.getText();
        String creditText = creditField.getText();
        String teacher1 = teacher1Field.getText();
        String teacher2 = teacher2Field.getText();
        String grade = gradeBox.getValue();

        if (name.isEmpty() || code.isEmpty() || creditText.isEmpty() || grade == null) {
            showAlert("Error", "Please fill all required fields.");
            return;
        }

        double credit;
        try {
            credit = Double.parseDouble(creditText);
        } catch (NumberFormatException e) {
            showAlert("Error", "Credit must be a number.");
            return;
        }

        Course course = new Course(name, code, credit, teacher1, teacher2, grade);
        courseList.add(course);

        // Clear fields
        nameField.clear();
        codeField.clear();
        creditField.clear();
        teacher1Field.clear();
        teacher2Field.clear();
        gradeBox.getSelectionModel().clearSelection();

        // Enable calculate button
        calculateButton.setDisable(courseList.isEmpty());
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleCalculate(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/_207010/Result.fxml"));
        Parent root = loader.load();

        // Pass course data to ResultController
        ResultController resultController = loader.getController();
        resultController.setCourseData(courseList);

        Scene resultScene = new Scene(root);
        resultScene.getStylesheets().add(getClass().getResource("/com/example/_207010/Styles.css").toExternalForm());

        Stage stage = new Stage();
        stage.setTitle("GPA Calculator - Results");
        stage.setScene(resultScene);
        stage.show();

        // Close current Course Entry window
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    // Inner class to store course info
    public static class Course {
        private final String name, code, teacher1, teacher2, grade;
        private final double credit;

        public Course(String name, String code, double credit, String teacher1, String teacher2, String grade) {
            this.name = name;
            this.code = code;
            this.credit = credit;
            this.teacher1 = teacher1;
            this.teacher2 = teacher2;
            this.grade = grade;
        }

        public String getName() { return name; }
        public String getCode() { return code; }
        public double getCredit() { return credit; }
        public String getTeacher1() { return teacher1; }
        public String getTeacher2() { return teacher2; }
        public String getGrade() { return grade; }
    }
}
