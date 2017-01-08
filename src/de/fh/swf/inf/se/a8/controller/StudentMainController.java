package de.fh.swf.inf.se.a8.controller;

import de.fh.swf.inf.se.a8.Main;
import de.fh.swf.inf.se.a8.model.Ansprechpartner;
import de.fh.swf.inf.se.a8.model.Organisation;
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
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ConcurrentModificationException;

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
    private ObservableList<Projekt> projekteListe = FXCollections.observableArrayList();
    private ObservableList<Student> studentenListe = FXCollections.observableArrayList();
    ;
    private ObservableList<Student> dozentenListe = FXCollections.observableArrayList();
    ;
    private ObservableList<Organisation> organisationsListe = FXCollections.observableArrayList();
    ;
    private ObservableList<Ansprechpartner> ansprechpartnerListe = FXCollections.observableArrayList();
    ;

    private Student user;
    private Projekt selectedItem;

    @FXML
    public void initialize() {
        listProjekte.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Projekt>() {
            @Override
            public void changed(ObservableValue<? extends Projekt> observable, Projekt oldValue, Projekt newValue) {
                selectedItem = newValue;
            }
        });
        listProjekte.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() >= 2) {
                    handleDetails();
                }
            }
        });
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        setList();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    private void setList() {

        listProjekte.getItems().addAll(projekteListe);
    }
    private void addToList(Projekt p){
        listProjekte.getItems().add(p);
    }
    @FXML
    private void handleDetails() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/projView.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Projektdetails");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.dialogStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            ProjektAnzeigenController controller = loader.getController();
            controller.setMainApp(this.mainApp);
            controller.setSelectedProject(selectedItem);
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDescription() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/projAddDetails.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Projektbeschreibung Einreichen");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.dialogStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            ProjektDetailsAddController controller = loader.getController();
            controller.setMainApp(this.mainApp);

            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public StudentMainController() {
    }

    @FXML
    private void handleNewProject() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/projNewWindow.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Neues Projekt Anlegen");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.dialogStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            ProjektAnlegenController controller = loader.getController();
            controller.setMainApp(this.mainApp);
            controller.setLists(ansprechpartnerListe, dozentenListe,projekteListe, user);
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();
            if (controller.isOkClicked())
            addToList(controller.getNewProject());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUser(Student user) {
        this.user = user;
        dialogStage.setTitle(user.getNachname() + " - Projektverwaltung");
        try {
            DBcontroller db = new DBcontroller();
            db.connectDB();
            studentenListe = db.readStudentTable();
            try {
                for (Student s : studentenListe) {
                    if (s.getMatrikelnummer() == 0) {
                        dozentenListe.add(s);
                        studentenListe.remove(s);
                    }
                }
            } catch (ConcurrentModificationException o) {
                //ist ok
            }
            organisationsListe = db.readOrgTable();
            ansprechpartnerListe = db.readAnspTable(organisationsListe);
            projekteListe = db.readProjects(user, studentenListe, dozentenListe, ansprechpartnerListe);

            db.disconnectDB();
        } catch (Exception e) {
            System.out.print(e);
        }
        setList();
    }
}
