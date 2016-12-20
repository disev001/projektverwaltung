package de.fh.swf.inf.se.a8.controller;

import de.fh.swf.inf.se.a8.Main;
import de.fh.swf.inf.se.a8.model.Projekt;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

/**
 * Created by dsee on 19.12.2016.
 */
public class StudentMainController {
    @FXML
    private ListView<Projekt> listProjekte;
    @FXML
    private Button btnDetails;
    @FXML
    private Button btnEinreichen;
    @FXML
    private Button btnNewProject;
    private Main mainApp;
    private Stage dialogStage;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}
