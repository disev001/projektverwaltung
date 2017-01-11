package de.fh.swf.inf.se.a8.controller;

import de.fh.swf.inf.se.a8.Main;
import de.fh.swf.inf.se.a8.model.Projekt;
import de.fh.swf.inf.se.a8.view.InfoWindows;
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
    private Projekt selectedProject = null;
    private Main mainApp;
    private Stage dialogStage;
    private String oldTitle = "";
    private File beschreibung = null;
    private File skizze = null;
    private boolean isOkClicked;

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
                            beschreibung = file;
                            btnBeschreibung.setText(file.getName());
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
                            skizze = file;
                            btnSkizze.setText(file.getName());
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
    private void handleOK() throws Exception {
        isOkClicked = true;
        try {
            selectedProject.setProjekttitel(txtProjektTitel.getText());
            selectedProject.setProjektskizze(skizze);
            selectedProject.setProjektbeschreibung(beschreibung);

            dialogStage.close();
        } catch (IllegalArgumentException e) {
            new InfoWindows("Fehler", "Keine Daten zum Upload Hinzugefügt", "Bitte wählen Sie Daten zum Upload aus");
            isOkClicked = false;
            selectedProject.setProjekttitel(oldTitle);
        } catch (Exception e) {
            isOkClicked = false;
            selectedProject.setProjekttitel(oldTitle);
        }
    }

    public boolean isOkClicked() {
        return isOkClicked;
    }

    @FXML
    private void handleClose() {
        dialogStage.close();
    }

    public void setSelectedProject(Projekt selectedProject) {
        this.selectedProject = selectedProject;
        txtProjektTitel.setText(selectedProject.getProjekttitel());
        oldTitle = selectedProject.getProjekttitel();
    }

    public void writeFiles() {
        DBcontroller db = new DBcontroller();
        try {
            db.connectDB();
            if (db.insertFile(selectedProject, oldTitle)) {
                oldTitle = selectedProject.getProjekttitel();
            } else throw new IllegalArgumentException();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.disconnectDB();
        }
    }
}
