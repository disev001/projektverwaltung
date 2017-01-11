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
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ConcurrentModificationException;

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
    private Projekt selectedProject = null;
    private Student user;

    private ObservableList<Student> studentenListe = FXCollections.observableArrayList();
    private ObservableList<Organisation> organisationsListe = FXCollections.observableArrayList();
    private ObservableList<Ansprechpartner> ansprechpartnerListe = FXCollections.observableArrayList();
    private ObservableList<Projekt> projektListe = FXCollections.observableArrayList();
    private ObservableList<Student> dozentenListe = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        listProjekte.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Projekt>() {
            @Override
            public void changed(ObservableValue<? extends Projekt> observable, Projekt oldValue, Projekt newValue) {
                selectedProject = newValue;
            }
        });

        listProjekte.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() >= 2) {
                    handleSelect();
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

    public void setList() {
        // listProjekte.setItems(FXCollections.observableArrayList());
        listProjekte.getItems().addAll(projektListe);
    }

    public void handleSelect() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/projDozentView.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Projektdetails");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.dialogStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            ProjektAnzeigenDozentContoller controller = loader.getController();
            controller.setMainApp(this.mainApp);
            controller.setSelected(selectedProject);
            controller.setDialogStage(dialogStage);
           // controller.setSelected(selectedProject);
            dialogStage.showAndWait();
            if(controller.isOKclicked())
            {
                controller.setNewProject();
                setUser(this.user);
            }
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

    public void setUser(Student user) {
        listProjekte.setItems(FXCollections.observableArrayList());
        this.user = user;
        dialogStage.setTitle(user.getNachname() + " - Projektverwaltung");
        DBcontroller db = new DBcontroller();
        db.connectDB();
        try {

            studentenListe = db.readStudentTable();
            sortStudentList();
            organisationsListe = db.readOrgTable();
            ansprechpartnerListe = db.readAnspTable(organisationsListe);
            projektListe = db.readDozentProjects(user, studentenListe, dozentenListe, ansprechpartnerListe);

        } catch (Exception e) {
            System.out.print(e);
        }finally {
            db.disconnectDB();
            setList();
        }
    }
    @FXML
    private void  handleDelete(){
        DBcontroller db = new DBcontroller();
        try {
            db.connectDB();
            db.deleteProject(selectedProject);
        }
        catch (Exception e){
        e.printStackTrace();
        }finally {
            db.disconnectDB();
            setUser(this.user);
        }
    }
    @FXML
    private void handleAnsp(){
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
            controller.setSettinAnsp(false);
            controller.setMainApp(this.mainApp);
            controller.setDialogStage(this.dialogStage);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void sortStudentList() {
        boolean hasChanged = false;
        int i = 0;
        int size = studentenListe.size();

            for (Student s : studentenListe) {
                if (s.getMatrikelnummer() == 0) {
                    dozentenListe.add(s);
                    studentenListe.remove(s);
                    hasChanged = true;
                    break;
                }
            }
            if(hasChanged){
                sortStudentList();
            }
        }

}
