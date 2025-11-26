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

public class CourseController
{
    @FXML
    private TextField a, b, c, d, e, f;

    @FXML
    private ComboBox<String> g;

    @FXML
    private Button h;

    private final ObservableList<Course> i = FXCollections.observableArrayList();

    @FXML
    public void initialize()
    {
        g.getItems().addAll("A", "A-", "B+", "B", "B-", "C+", "C", "D", "F");
    }

    @FXML
    private void handleAddCourse()
    {
        String j = b.getText();
        String k = c.getText();
        String l = d.getText();
        String m = e.getText();
        String n = f.getText();
        String o = g.getValue();

        if (j.isEmpty() || k.isEmpty() || l.isEmpty() || o == null)
        {
            showAlert("Error", "Please fill all required fields.");
            return;
        }

        double p;
        try
        {
            p = Double.parseDouble(l);
        }
        catch (NumberFormatException q)
        {
            showAlert("Error", "Credit must be a number.");
            return;
        }

        Course q = new Course(j, k, p, m, n, o);
        i.add(q);

        a.clear();
        b.clear();
        c.clear();
        d.clear();
        e.clear();
        f.clear();
        g.getSelectionModel().clearSelection();

        h.setDisable(i.isEmpty());
    }

    private void showAlert(String j, String k)
    {
        Alert l = new Alert(Alert.AlertType.WARNING);
        l.setTitle(j);
        l.setHeaderText(null);
        l.setContentText(k);
        l.showAndWait();
    }

    @FXML
    private void handleCalculate(ActionEvent j) throws IOException
    {
        if (i.isEmpty())
        {
            showAlert("Error", "Please add at least one course before calculating GPA.");
            return;
        }

        FXMLLoader k = new FXMLLoader(getClass().getResource("/com/example/_207010/Result.fxml"));
        Parent l = k.load();

        ResultController m = k.getController();
        m.setCourseData(i);

        Scene n = new Scene(l);
        n.getStylesheets().add(getClass().getResource("/com/example/_207010/Styles.css").toExternalForm());

        Stage o = new Stage();
        o.setTitle("GPA Calculator - Results");
        o.setScene(n);
        o.show();

        Stage p = (Stage) ((Button) j.getSource()).getScene().getWindow();
        p.close();
    }

    public static class Course
    {
        private final int id;
        private final String name, code, teacher1, teacher2, grade;
        private final double credit;

        public Course(String name, String code, double credit, String teacher1, String teacher2, String grade)
        {
            this(0, name, code, credit, teacher1, teacher2, grade);
        }

        public Course(int id, String name, String code, double credit, String teacher1, String teacher2, String grade)
        {
            this.id = id;
            this.name = name;
            this.code = code;
            this.credit = credit;
            this.teacher1 = teacher1;
            this.teacher2 = teacher2;
            this.grade = grade;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public String getCode() { return code; }
        public double getCredit() { return credit; }
        public String getTeacher1() { return teacher1; }
        public String getTeacher2() { return teacher2; }
        public String getGrade() { return grade; }
    }
}