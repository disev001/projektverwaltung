package de.fh.swf.inf.se.a8.controller;

import de.fh.swf.inf.se.a8.Main;
import de.fh.swf.inf.se.a8.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

/**
 * Created by dsee on 20.12.2016.
 */
public class ProjektStudentAddController {
    @FXML
    private ComboBox<Student> cbStudents;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnCancel;
    private Stage dialogStage;
    private Main mainApp;

    private void setList() {

        Student student1 = new Student("Severgin", "Dieter", "severgin.dieter@fh-swf.de", 10040845);
        Student student2 = new Student("Mustermann", "Max", "musterman.max@fh-swf.de", 10042334);
        Student student3 = new Student("Doe", "John", "doe.john@anon.de", 2933923);
        Student student4 = new Student("Mono", "Willi", "krassertypXXX@t-online.de", 32423423);
        ObservableList<Student> students = FXCollections.observableArrayList();
        students.addAll(student1, student2, student3, student4);
        cbStudents.setItems(students);
    }
    @FXML
    private void handleAdd() {
        dialogStage.close();
    }
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        setList();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}
