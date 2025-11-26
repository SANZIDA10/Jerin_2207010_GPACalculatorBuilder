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
import java.util.Optional;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

public class ResultController
{
    private DB d = new DB();
    private ObservableList<CourseController.Course> c;

    @FXML
    private TableView<CourseController.Course> a;

    @FXML
    private TableColumn<CourseController.Course, Integer> idCol;

    @FXML
    private TableColumn<CourseController.Course, String> nameCol, codeCol, teacher1Col, teacher2Col, gradeCol;

    @FXML
    private TableColumn<CourseController.Course, Double> creditCol;

    @FXML
    private Label j;

    @FXML
    public void initialize()
    {

    }

    private void s()
    {
        idCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleObjectProperty<>(cell.getValue().getId()));
        nameCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getName()));
        codeCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getCode()));
        creditCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleObjectProperty<>(cell.getValue().getCredit()));
        teacher1Col.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getTeacher1()));
        teacher2Col.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getTeacher2()));
        gradeCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getGrade()));
    }

    public void setCourseData(ObservableList<CourseController.Course> k)
    {
        s();
        c = k;
        a.setItems(k);
        m(k);

        for (CourseController.Course l : c)
        {
            if (l.getId() == 0)
            {
                d.insertCourse(l);
            }
        }
    }

    private void m(ObservableList<CourseController.Course> k)
    {
        double l = 0;
        double m = 0;

        for (CourseController.Course o : k)
        {
            l += o.getCredit() * gradeToPoint(o.getGrade());
            m += o.getCredit();
        }

        double o = m == 0 ? 0 : l / m;
        j.setText(String.format("%.2f", o));
    }

    private double gradeToPoint(String k)
    {
        return switch (k)
        {
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
    private void handleBack(ActionEvent k) throws IOException
    {
        FXMLLoader l = new FXMLLoader(getClass().getResource("/com/example/_207010/CourseEntry.fxml"));
        Parent m = l.load();

        Scene n = new Scene(m);
        n.getStylesheets().add(getClass().getResource("/com/example/_207010/Styles.css").toExternalForm());

        Stage o = new Stage();
        o.setTitle("GPA Calculator - Course Entry");
        o.setScene(n);
        o.show();

        Stage p = (Stage) ((Button) k.getSource()).getScene().getWindow();
        p.close();
    }

    @FXML
    private void handleUpdate()
    {
        CourseController.Course k = a.getSelectionModel().getSelectedItem();
        if (k == null)
        {
            showAlert("No Selection", "Please select a course to update.");
            return;
        }

        Dialog<CourseController.Course> l = new Dialog<>();
        l.setTitle("Update Course");
        l.setHeaderText("Update Grade and Credit for " + k.getName());

        ButtonType m = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        l.getDialogPane().getButtonTypes().addAll(m, ButtonType.CANCEL);

        GridPane n = new GridPane();
        n.setHgap(10);
        n.setVgap(10);
        n.setPadding(new Insets(20, 150, 10, 10));

        TextField o = new TextField(String.valueOf(k.getCredit()));
        ComboBox<String> p = new ComboBox<>();
        p.getItems().addAll("A", "A-", "B+", "B", "B-", "C+", "C", "D", "F");
        p.getSelectionModel().select(k.getGrade());

        n.add(new Label("Credit:"), 0, 0);
        n.add(o, 1, 0);
        n.add(new Label("Grade:"), 0, 1);
        n.add(p, 1, 1);

        l.getDialogPane().setContent(n);

        l.setResultConverter(q ->
        {
            if (q == m)
            {
                try
                {
                    double r = Double.parseDouble(o.getText());
                    String s = p.getValue();
                    if (s != null)
                    {
                        CourseController.Course u = new CourseController.Course(k.getId(), k.getName(), k.getCode(), r, k.getTeacher1(), k.getTeacher2(), s);
                        d.updateCourse(u);

                        int t = a.getItems().indexOf(k);
                        if (t != -1)
                        {
                            a.getItems().remove(k);
                            a.getItems().add(t, u);
                            a.refresh();
                            m(a.getItems());
                        }
                    }
                }
                catch (NumberFormatException r)
                {
                    showAlert("Error", "Credit must be a valid number.");
                }
            }
            return null;
        });

        l.showAndWait();
    }

    @FXML
    private void handleDelete()
    {
        CourseController.Course k = a.getSelectionModel().getSelectedItem();
        if (k == null)
        {
            showAlert("No Selection", "Please select a course to delete.");
            return;
        }

        Alert l = new Alert(Alert.AlertType.CONFIRMATION);
        l.setTitle("Confirm Deletion");
        l.setHeaderText(null);
        l.setContentText("Are you sure you want to delete course: " + k.getName() + "?");

        Optional<ButtonType> m = l.showAndWait();
        if (m.isPresent() && m.get() == ButtonType.OK)
        {
            d.deleteCourse(k.getId());
            a.getItems().remove(k);
            m(a.getItems());
        }
    }

    private void showAlert(String k, String l)
    {
        Alert m = new Alert(Alert.AlertType.WARNING);
        m.setTitle(k);
        m.setHeaderText(null);
        m.setContentText(l);
        m.showAndWait();
    }
}