package de.fh.swf.inf.se.a8.controller;

import de.fh.swf.inf.se.a8.Main;
import de.fh.swf.inf.se.a8.model.Ansprechpartner;
import de.fh.swf.inf.se.a8.model.Organisation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;

import static javafx.collections.FXCollections.*;

/**
 * Created by dsee on 19.12.2016.
 */
public class ProjektAnlegenController {
    @FXML
    private TextField txtTitel;
    @FXML
    private ComboBox<String> cbDozent;
    @FXML
    private ComboBox<Ansprechpartner> cbAnsprechpartner;
    @FXML
    private Button btnOK;
    @FXML
    private Button btnCancel;
    private ObservableList<Ansprechpartner> ansprechpartnerList = FXCollections.observableArrayList();
    private ObservableList<String> dozenten = FXCollections.observableArrayList();
    @FXML
    private void initialize(){

        Organisation org1 = new Organisation("Musterorg", 58762, "Altena", "Amselweg 6a");
        Organisation org2 = new Organisation("FHSWF", 55442, "Iserlohn", "Frauenstuhlweg 31");
        Ansprechpartner an1 = new Ansprechpartner("Katze", "Wasilisa", "dsee@doldrums.de", "02352/546521", org1);
        Ansprechpartner an2 = new Ansprechpartner("Overmann", "", "klug.uwe@fh-swf.de", "02242/8087652", org1);

        this.ansprechpartnerList.addAll(an1, an2);
        cbAnsprechpartner.setItems(ansprechpartnerList);

        this.dozenten.add("Klug, Uwe");
        this.dozenten.add("Rübsam, Michael");
        cbDozent.setItems(dozenten);

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
    private void handleOK(){
        dialogStage.close();
    }
    @FXML void handleClose(){
        dialogStage.close();
    }
}
