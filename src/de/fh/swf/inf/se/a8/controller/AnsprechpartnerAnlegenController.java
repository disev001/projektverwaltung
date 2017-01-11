package de.fh.swf.inf.se.a8.controller;

import de.fh.swf.inf.se.a8.Main;
import de.fh.swf.inf.se.a8.model.Ansprechpartner;
import de.fh.swf.inf.se.a8.model.Organisation;
import de.fh.swf.inf.se.a8.view.InfoWindows;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.IllegalFormatException;

/**
 * Created by dsee on 10.12.2016.
 */
public class AnsprechpartnerAnlegenController {
    @FXML
    private ComboBox<String> cb_Org;
    @FXML
    private TextField txtVname;
    @FXML
    private TextField txtNname;
    @FXML
    private TextField txtMail;
    @FXML
    private TextField txtTel;
    private ObservableList<Ansprechpartner> ansprechpartnerList;
    private Organisation org = null;
    private boolean okClicked = false;
    private Main mainApp;
    private ObservableList<Organisation> organisationList;
    private Stage dialogStage;
    private Ansprechpartner newAnsprechpartner  = null;
    private Organisation newOrg = null;


    /**
     * initialisiert handler der Stage
     */
    @FXML
    private void initialize() {
        //Reaktion auf die Organisationsauswahl
        cb_Org.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                for (Organisation o : organisationList) {
                    if (o.getName().equals(newValue)) {
                        org = o;
                    }
                }
            }
        });
    }

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

    /**
     * Füllen der Organisationsauswahl
     */
    public void setListe() {
        ObservableList<String> organisationAuswahl = FXCollections.observableArrayList();
        this.ansprechpartnerList = this.mainApp.getAnsprechpartners();
        try {
            DBcontroller db = new DBcontroller();
            db.connectDB();
            this.organisationList = db.readOrgTable();
            db.disconnectDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Organisation o : organisationList) {
            organisationAuswahl.add(o.getName());
        }
        cb_Org.setItems(organisationAuswahl);
    }

    /**
     * Versuch der Bestätigung der Eingaben
     */
    @FXML
    public void handleOK() {
        try {
            if (isValidEmailAddress(txtMail.getText())) {
                newAnsprechpartner = new Ansprechpartner(txtNname.getText(), txtVname.getText(), txtMail.getText(), txtTel.getText(), org);
            } else throw new IllegalArgumentException();
            okClicked = true;
            DBcontroller db = new DBcontroller();
            db.connectDB();
            db.inserNewAnsprechpartner(newAnsprechpartner);
            db.disconnectDB();
            dialogStage.close();
        } catch (IllegalArgumentException e) {
            new InfoWindows("FEHLER", "Ungültige Parameter für einen Ansprechpartner", "Ungültiges Format für Email");
        } catch (Exception e) {
            new InfoWindows("FEHLER", "Ungültige Parameter für einen Ansprechpartner", "Ungültige Parameter für einen Ansprechpartner\nBitte überprüfen Sie ihre eingaben");
        }
    }

    /**
     * Anlegen von Neuen organisation während Anlegen von AP
     *
     * @return Bestätigunsart
     */
    @FXML
    public void handleNewOrg() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/orgNewWindow.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Ogranisation anlegen");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.dialogStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            OrganisationAnlegenController controller = loader.getController();
            controller.setMainApp(this.mainApp);
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();
            if (controller.isOkClicked()) {
                newOrg = controller.getNewOrg();
                setListe();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
    public Ansprechpartner getNewAnsprechpartner(){
        return this.newAnsprechpartner;
    }
    /**
     * Abbruch ohne bestätigung
     */
    @FXML
    private void handleCancel() {
        okClicked = false;
        dialogStage.close();
    }

    public Organisation getNewOrg() {
        return newOrg;
    }
}
