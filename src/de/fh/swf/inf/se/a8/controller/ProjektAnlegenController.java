package de.fh.swf.inf.se.a8.controller;

import de.fh.swf.inf.se.a8.Main;
import de.fh.swf.inf.se.a8.model.Ansprechpartner;
import de.fh.swf.inf.se.a8.model.Organisation;
import de.fh.swf.inf.se.a8.model.Projekt;
import de.fh.swf.inf.se.a8.model.Student;
import de.fh.swf.inf.se.a8.view.InfoWindows;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by dsee on 19.12.2016.
 */
public class ProjektAnlegenController {
    @FXML
    private TextField txtTitel;
    @FXML
    private ComboBox<Student> cbDozent;
    @FXML
    private ComboBox<Ansprechpartner> cbAnsprechpartner;
    @FXML
    private Button btnOK;
    @FXML
    private Button btnCancel;
    private ObservableList<Ansprechpartner> ansprechpartnerList = FXCollections.observableArrayList();
    private ObservableList<Student> dozenteListe = FXCollections.observableArrayList();
    private ObservableList<Projekt> projektListe = FXCollections.observableArrayList();
    private Student user = null;
    Projekt p = null;
    private boolean isOkClicked = false;

    @FXML
    private void initialize() {

    }

    private Main mainApp;
    Stage dialogStage;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void handleOK() {
        isOkClicked = true;
        boolean isUnique = true;
        try {
            for (Projekt p : projektListe)
            {
                if(p.getProjekttitel().equals(txtTitel.getText()))
                {
                    isUnique =false;
                }
            }

            if (!txtTitel.getText().isEmpty() && !cbAnsprechpartner.getSelectionModel().isEmpty() && !cbDozent.getSelectionModel().isEmpty())
            p = new Projekt(txtTitel.getText(),user,cbAnsprechpartner.getSelectionModel().getSelectedItem(),cbDozent.getSelectionModel().getSelectedItem());
            else throw new IllegalArgumentException();
            dialogStage.close();
        }catch (Exception e){
            isOkClicked =false;
            if (isUnique)
            new InfoWindows("Fehler","Projekt nicht angelegt","Bitte füllen sie alle Daten aus");
            else             new InfoWindows("Fehler","Projekt bereits vorhanden","Ein Projekt mit dem Titel '"+txtTitel.getText()+"' existiert bereits");
        }
    }

    @FXML
    void handleClose() {
        dialogStage.close();
    }

    public void setLists(ObservableList<Ansprechpartner> ansprechpartnerListe, ObservableList<Student> dozentenListe,ObservableList<Projekt> projekts,Student user) {
        this.ansprechpartnerList = ansprechpartnerListe;
        this.dozenteListe = dozentenListe;
        cbDozent.setItems(dozenteListe);
        cbAnsprechpartner.setItems(ansprechpartnerList);
        this.user = user;
        this.projektListe = projekts;
    }
    public boolean isOkClicked(){
        return isOkClicked;
    }
    public Projekt getNewProject(){
        DBcontroller db = new DBcontroller();
        db.connectDB();
        db.insertNewProjekt(p,user);
        return this.p;
    }
}
