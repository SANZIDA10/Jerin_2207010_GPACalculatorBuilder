package com.example._207010;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class CourseController {

    @FXML
    private TextField nameField, codeField, creditField, teacher1Field, teacher2Field, totalCreditField;
    @FXML
    private ComboBox<String> gradeBox;
    @FXML
    private Button calculateButton;

    private ObservableList<Course> courseList = FXCollections.observableArrayList();
    private double sumCredits = 0.0;
    private double totalCredit = 0.0;

    @FXML
    public void initialize() {
        gradeBox.getItems().addAll("A", "A-", "B+", "B", "B-", "C+", "C", "D", "F");

        // Enable calculate only when total credit is set
        totalCreditField.textProperty().addListener((obs, oldText, newText) -> {
            try {
                totalCredit = Double.parseDouble(newText);
                checkTotalCredits();
            } catch (NumberFormatException e) {
                calculateButton.setDisable(true);
            }
        });

        // Initially disable calculate button
        calculateButton.setDisable(true);
    }

    @FXML
    private void handleAddCourse() {
        try {
            String name = nameField.getText();
            String code = codeField.getText();
            double credit = Double.parseDouble(creditField.getText());
            String teacher1 = teacher1Field.getText();
            String teacher2 = teacher2Field.getText();
            String grade = gradeBox.getValue();

            if (name.isEmpty() || code.isEmpty() || grade == null) {
                showAlert("Error", "Please fill all required fields (Name, Code, Grade).");
                return;
            }

            courseList.add(new Course(name, code, credit, teacher1, teacher2, grade));
            sumCredits += credit;

            // Clear input fields
            nameField.clear();
            codeField.clear();
            creditField.clear();
            teacher1Field.clear();
            teacher2Field.clear();
            gradeBox.setValue(null);

            checkTotalCredits();

        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid number for credit.");
        }
    }

    private void checkTotalCredits() {
        calculateButton.setDisable(sumCredits != totalCredit);
    }

    @FXML
    private void handleCalculate() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/_207010/Result.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));

            // Pass course data to ResultController
            ResultController resultController = loader.getController();
            resultController.setCourseData(courseList);

            stage.setTitle("GPA Result");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load Result window.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Inner class for Course
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
