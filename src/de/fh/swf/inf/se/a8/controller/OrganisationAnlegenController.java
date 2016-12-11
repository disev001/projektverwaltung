package de.fh.swf.inf.se.a8.controller;

import de.fh.swf.inf.se.a8.Main;
import de.fh.swf.inf.se.a8.model.Organisation;
import de.fh.swf.inf.se.a8.view.InfoWindows;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;


/**
 * Created by dsee on 10.12.2016.
 */

public class OrganisationAnlegenController {
    @FXML
    private TextField txtOrg;
    @FXML
    private TextField txtPLZ;
    @FXML
    private javafx.scene.control.TextField txtOrt;
    @FXML
    private TextField txtStreet;
    @FXML
    private Button btnOK;
    @FXML
    private Button btnCancel;
    private ObservableList<Organisation> organisationList;
    private Stage dialogStage;
    private boolean okClicked = false;
    private Main mainApp;

    public boolean isOkClicked() {
        return okClicked;
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        setListe();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setListe() {
        this.organisationList = this.mainApp.getOrganisations();
    }

    @FXML
    private void handleOk() {
        okClicked = true;
        try {
            organisationList.add(new Organisation(txtOrg.getText(), Integer.parseInt(txtPLZ.getText()), txtOrt.getText(), txtStreet.getText()));
        } catch (Exception e) {
            new InfoWindows("FEHLER", null, "SO NICHT!");
        }
        dialogStage.close();
    }

    @FXML
    private void handleCancel() {
        okClicked = false;
        dialogStage.close();
    }
}
