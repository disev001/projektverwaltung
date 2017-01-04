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
