package de.fh.swf.inf.se.a8.controller;

import de.fh.swf.inf.se.a8.Main;
import de.fh.swf.inf.se.a8.model.Ansprechpartner;
import de.fh.swf.inf.se.a8.model.Organisation;
import de.fh.swf.inf.se.a8.model.Projekt;
import de.fh.swf.inf.se.a8.model.Student;
import de.fh.swf.inf.se.a8.testrun;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * Created by dsee on 19.12.2016.
 */
public class DozentMainController {
    @FXML
    private ListView<Projekt> listProjekte;
    @FXML
    private Button btnSelect;
    @FXML
    private Button btnDelete;
    private Main mainApp;
    private Stage dialogStage = null;
    private ObservableList<Projekt> projekte = FXCollections.observableArrayList();
    private Projekt selectedProject = null;

    Student student1 = new Student("Severgin", "Dieter", "severgin.dieter@fh-swf.de", 10040845);
    Student student2 = new Student("Mustermann", "Max", "musterman.max@fh-swf.de", 10042334);
    Student student3 = new Student("Doe", "John", "doe.john@anon.de", 2933923);
    Student student4 = new Student("Mono", "Willi", "krassertypXXX@t-online.de", 32423423);

    Organisation org1 = new Organisation("Musterorg", 58762, "Altena", "Amselweg 6a");
    Organisation org2 = new Organisation("FHSWF", 55442, "Iserlohn", "Frauenstuhlweg 31");

    Ansprechpartner an1 = new Ansprechpartner("Katze", "Wasilisa", "dsee@doldrums.de", "02352/546521", org1);
    Ansprechpartner an2 = new Ansprechpartner("Klug", "Uwe", "klug.uwe@fh-swf.de", "02242/8087652", org1);

    Projekt p1 = new Projekt("Projektverwaltung", "Projektbeschreibung mit Skizze usw...", "20-03-2017", "10-04-2017", student1, an1);

    public ObservableList<Student> studentenListe ;



    public  ObservableList<Organisation> organisationsListe;
    public ObservableList<Ansprechpartner> ansprechpartnerListe;
    public  ObservableList<Projekt> projektListe;
    @FXML
    public void initialize(){
        listProjekte.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Projekt>() {
            @Override
            public void changed(ObservableValue<? extends Projekt> observable, Projekt oldValue, Projekt newValue) {
                selectedProject = newValue;
            }
        });
    }
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp; setList();
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setList() {
        projekte.add(p1);
        p1.addStudent(student2);
        listProjekte.getItems().addAll(projekte);
    }

    public void handleSelect(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/projDozentView.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Ansprechpartner anlegen");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.dialogStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            ProjektAnzeigenDozentContoller controller = loader.getController();
            controller.setMainApp(this.mainApp);
            controller.setSelected(selectedProject);
            controller.setDialogStage(dialogStage);
            controller.setSelected(selectedProject);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public ObservableList<Student> getStudentenListe() {
        return studentenListe;
    }

    public ObservableList<Organisation> getOrganisationsListe() {
        return organisationsListe;
    }

    public ObservableList<Ansprechpartner> getAnsprechpartnerListe() {
        return ansprechpartnerListe;
    }

    public ObservableList<Projekt> getProjektListe() {
        return projektListe;
    }
}
