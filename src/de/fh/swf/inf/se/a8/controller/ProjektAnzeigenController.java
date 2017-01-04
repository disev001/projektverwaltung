package de.fh.swf.inf.se.a8.controller;

import de.fh.swf.inf.se.a8.Main;
import de.fh.swf.inf.se.a8.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * Created by dsee on 19.12.2016.
 */
public class ProjektAnzeigenController {

    @FXML
    private Label lblProjekttitel;
    @FXML
    private Label lblEnscheidung;
    @FXML
    private TextArea tfComment;
    @FXML
    private Button btnOK;
    @FXML
    private Button btnCancel;
    @FXML
    private ListView<Student> listTeilnehmer;

    private Main mainApp;
    Stage dialogStage;

    @FXML
    private void initialize(){
        lblProjekttitel.setText("Projektverwaltung");
        lblEnscheidung.setText("Zugelassen");
        tfComment.setText("Alles OK, Termin kommt später");
        ObservableList<Student> students = FXCollections.observableArrayList();

    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    @FXML
    private void handleOK(){
        dialogStage.close();
    }
    @FXML void handleClose(){
        dialogStage.close();
    }
}
