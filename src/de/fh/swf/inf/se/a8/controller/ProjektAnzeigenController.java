package de.fh.swf.inf.se.a8.controller;

import de.fh.swf.inf.se.a8.Main;
import de.fh.swf.inf.se.a8.model.Projekt;
import de.fh.swf.inf.se.a8.model.Student;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by dsee on 19.12.2016.
 */
public class ProjektAnzeigenController {

    @FXML
    private Label lblProjekttitel;
    @FXML
    private Label lblEnscheidung;
    @FXML
    private Hyperlink lblDozent;
    @FXML
    private Hyperlink lblAnsprechpartner;
    @FXML
    private TextArea tfComment;
    @FXML
    private Button btnOK;
    @FXML
    private Button btnCancel;
    @FXML
    private ListView<Student> listTeilnehmer;

    private Main mainApp;
    private Stage dialogStage;
    private Projekt selectedProject;
    private Student selectedItem;

    @FXML
    private void initialize() {
        lblDozent.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("view/contacts.fxml"));
                AnchorPane page = loader.load();
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Kontaktdetails");
                dialogStage.initOwner(this.dialogStage);
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);
                ContactController controller = loader.getController();
                controller.setMainApp(this.mainApp);
                controller.setObject(selectedProject.getDozent());
                controller.setDialogStage(dialogStage);
                dialogStage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        lblAnsprechpartner.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("view/contacts.fxml"));
                AnchorPane page = loader.load();
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Kontaktdetails");
                dialogStage.initOwner(this.dialogStage);
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);
                ContactController controller = loader.getController();
                controller.setMainApp(this.mainApp);
                controller.setObject(selectedProject.getAnsprechpartner());
                controller.setDialogStage(dialogStage);
                dialogStage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        listTeilnehmer.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Student>() {
            @Override
            public void changed(ObservableValue<? extends Student> observable, Student oldValue, Student newValue) {
                selectedItem = newValue;
            }
        });
        listTeilnehmer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount() >= 2){
                    if (Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();
                        if (desktop.isSupported(Desktop.Action.MAIL)) {
                            try {
                                desktop.mail(new URI( "mailto:"+selectedItem.getEmail()+"?subject="+selectedProject.getProjekttitel())); // alternately, pass a mailto: URI in here
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                        }
                    }
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
    void handleClose() {
        dialogStage.close();
    }

    public void setSelectedProject(Projekt selectedProject) {
        this.selectedProject = selectedProject;
        lblProjekttitel.setText(selectedProject.getProjekttitel());
        lblEnscheidung.setText(selectedProject.getEntscheidung());
        tfComment.setText(selectedProject.getKommentar());
        lblAnsprechpartner.setText(selectedProject.getAnsprechpartner().getName()+", "+selectedProject.getAnsprechpartner().getVorname());
        lblDozent.setText(selectedProject.getDozent().getNachname()+", "+selectedProject.getDozent().getVorname());
        ObservableList<Student> teilnehmer = FXCollections.observableArrayList();
        for (Student s : selectedProject.getProjektgruppe()) {
            teilnehmer.add(s);
        }
        listTeilnehmer.setItems(teilnehmer);
    }
}
