package de.fh.swf.inf.se.a8.controller;

import de.fh.swf.inf.se.a8.Main;
import de.fh.swf.inf.se.a8.model.Ansprechpartner;
import de.fh.swf.inf.se.a8.model.Organisation;
import de.fh.swf.inf.se.a8.model.Projekt;
import de.fh.swf.inf.se.a8.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

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
    private ObservableList<Projekt> projekte = FXCollections.observableArrayList();

    Student student1 = new Student("Severgin", "Dieter", "severgin.dieter@fh-swf.de", 10040845);
    Student student2 = new Student("Mustermann", "Max", "musterman.max@fh-swf.de", 10042334);
    Student student3 = new Student("Doe", "John", "doe.john@anon.de", 2933923);
    Student student4 = new Student("Mono", "Willi", "krassertypXXX@t-online.de", 32423423);

    Organisation org1 = new Organisation("Musterorg", 58762, "Altena", "Amselweg 6a");
    Organisation org2 = new Organisation("FHSWF", 55442, "Iserlohn", "Frauenstuhlweg 31");

    Ansprechpartner an1 = new Ansprechpartner("Katze", "Wasilisa", "dsee@doldrums.de", "02352/546521", org1);
    Ansprechpartner an2 = new Ansprechpartner("Klug", "Uwe", "klug.uwe@fh-swf.de", "02242/8087652", org1);

    Projekt p1 = new Projekt("Projektverwaltung", "Projektbeschreibung mit Skizze usw...", "20-03-2017", "10-04-2017", student1, an1);

    public ObservableList<Student> studentenListe;
    public ObservableList<Organisation> organisationsListe;
    public ObservableList<Ansprechpartner> ansprechpartnerListe;
    public ObservableList<Projekt> projektListe;

    @FXML
    public void initialize() {
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        setList();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setList() {
        projekte.add(p1);
        p1.addStudent(student2);
        listProjekte.getItems().addAll(projekte);
    }

    @FXML
    private void handleDetails(){
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

            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleDescription(){
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
    @FXML
    private void handleNewProject(){
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

            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
