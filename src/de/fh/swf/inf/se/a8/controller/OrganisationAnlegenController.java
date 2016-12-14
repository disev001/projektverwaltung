package de.fh.swf.inf.se.a8.controller;

import de.fh.swf.inf.se.a8.Main;
import de.fh.swf.inf.se.a8.model.Organisation;
import de.fh.swf.inf.se.a8.view.InfoWindows;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.IllegalFormatWidthException;


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

    /**
     * initialisiert handler der Stage
     */
    @FXML
    public void initialize() {
        txtPLZ.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {


                    if (newValue.length() > 0)
                        Integer.parseInt(newValue);
                    if (newValue.length() > 5) {
                        throw new IllegalFormatWidthException(5);
                    }
                } catch (Exception e) {
                    txtPLZ.setText(oldValue);
                }
            }
        });
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

    /**
     * Bestätigen der Werte für neue Orga
     */
    @FXML
    private void handleOk() {
        okClicked = true;
        int plz;
        try {
            if (!isValidPLZ()) {
                plz = 0; // Für ungültige/Leere PLZ
            } else {
                plz = Integer.parseInt(txtPLZ.getText());
            }
            organisationList.add(new Organisation(txtOrg.getText(), plz, txtOrt.getText(), txtStreet.getText()));
        } catch (Exception e) {
            new InfoWindows("FEHLER", null, "Ungültige Parameter für eine Organisation");
        }
        dialogStage.close();
    }


    @FXML
    private void handleCancel() {
        okClicked = false;
        dialogStage.close();
    }

    private boolean isValidPLZ() {
        String ePattern = "^((?:0[1-46-9]\\d{3})|(?:[1-357-9]\\d{4})|(?:[4][0-24-9]\\d{3})|(?:[6][013-9]\\d{3}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(txtPLZ.getText().toString());
        return m.matches();
    }
}

