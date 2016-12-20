package de.fh.swf.inf.se.a8.controller;

import de.fh.swf.inf.se.a8.Main;
import de.fh.swf.inf.se.a8.model.Ansprechpartner;
import de.fh.swf.inf.se.a8.model.Projekt;
import de.fh.swf.inf.se.a8.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * Created by dsee on 19.12.2016.
 */
public class ProjektAnzeigenDozentContoller {
    @FXML
    private Label lblProjekttitel;
    @FXML
    private Button btnDbeschreibung;
    @FXML
    private Button btnDskizze;
    @FXML
    private Button btnAddAnsp;
    @FXML
    private RadioButton radio1;
    @FXML
    private RadioButton radio2;
    @FXML
    private RadioButton radio3;
    @FXML
    private Label lblAnsprechpartner;
    @FXML
    private TextArea tfNote;
    @FXML
    private Button btnOK;
    @FXML
    private Button btnCancel;
    @FXML
    private ListView<Student> listTeilnehmer;
    @FXML
    private Button btnAddStudent;

    private Stage dialogStage;
    private Projekt selected;
    private Main mainApp;

    public void initialize() {
        ToggleGroup group = new ToggleGroup();
        radio1.setToggleGroup(group);
        radio2.setToggleGroup(group);
        radio3.setToggleGroup(group);
        btnDskizze.setOnAction(new EventHandler<ActionEvent>() {
                                  public void handle(ActionEvent event) {
                                      FileChooser fileChooser = new FileChooser();
                                      fileChooser.setTitle("Save Skizze");
                                      File file = fileChooser.showSaveDialog(dialogStage);
                                      if (file != null) {
                                          try {
                                              ;
                                          } catch (Exception e) {
                                              ;
                                          }
                                      }
                                  }
                              }
        );

        btnDbeschreibung.setOnAction(new EventHandler<ActionEvent>() {
                                        public void handle(ActionEvent event) {
                                            FileChooser fileChooser = new FileChooser();
                                            fileChooser.setTitle("Save Beschreibung");
                                            File file = fileChooser.showSaveDialog(dialogStage);
                                            if (file != null) {
                                                try {
                                                    ;
                                                } catch (Exception e) {
                                                    ;
                                                }
                                            }
                                        }
                                    }
        );
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setSelected(Projekt selected) {
        this.selected = selected;
        lblProjekttitel.setText(selected.getProjekttitel());
        lblAnsprechpartner.setText(selected.getAnsprechpartner().getName() + ", " + selected.getAnsprechpartner().getVorname());
        ObservableList<Student> students = FXCollections.observableArrayList();
        for (Student s : selected.getProjektgruppe()) {
            students.add(s);
        }
        listTeilnehmer.setItems(students);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void handlebtnAddAnsp(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/anspView.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Ansprechpartner");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.dialogStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            AnsprechpartneranzeigeController controller = loader.getController();
            controller.setMainApp(this.mainApp);
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    @FXML
    private void handleOK() {
        dialogStage.close();
    }
    @FXML
    private void handleAddStudent() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/addStudent.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Ansprechpartner");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.dialogStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            ProjektStudentAddController controller = loader.getController();
            controller.setMainApp(this.mainApp);

            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
