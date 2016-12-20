package de.fh.swf.inf.se.a8.controller;

import de.fh.swf.inf.se.a8.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by dsee on 19.12.2016.
 */
public class ProjektDetailsAddController {
    @FXML
    private TextField txtProjektTitel;
    @FXML
    private Button btnSkizze;
    @FXML
    private Button btnBeschreibung;
    @FXML
    private Button btnOK;
    @FXML
    private Button btnCancel;

    private Main mainApp;
    Stage dialogStage;

    @FXML
    private void initialize() {
        btnBeschreibung.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        final FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Upload Beschreibung");
                        File file = fileChooser.showOpenDialog(dialogStage);
                        if (file != null) {
                            ;
                        }
                    }
                });
        btnSkizze.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        final FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Upload Skizze");
                        File file = fileChooser.showOpenDialog(dialogStage);
                        if (file != null) {
                            ;
                        }
                    }
                });
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    @FXML
    private void handleOK() {
        dialogStage.close();
    }

    @FXML
    private void handleClose() {
        dialogStage.close();
    }
}
