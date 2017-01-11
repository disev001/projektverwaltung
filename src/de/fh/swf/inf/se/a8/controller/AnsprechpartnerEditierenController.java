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

/**
 * Created by dsee on 10.12.2016.
 */
public class AnsprechpartnerEditierenController {
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
    private Ansprechpartner newAnsprechpartner;
    private boolean okClicked = false;
    private Main mainApp;
    private ObservableList<Organisation> organisationList;
    private Stage dialogStage;
    private Ansprechpartner oldAnsprechpartner;


    /**
     * initialisiert handler der Stage
     */
    @FXML
    private void initialize() {
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
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Setze verfügbare Organisationen in die Liste zur auswahl
     */
    public void setListe(ObservableList<Organisation> org) {
        ObservableList<String> organisationAuswahl = FXCollections.observableArrayList();
        this.ansprechpartnerList = this.mainApp.getAnsprechpartners();
        this.organisationList = org;
        for (Organisation o : organisationList) {
            organisationAuswahl.add(o.getName());
        }
        cb_Org.setItems(organisationAuswahl);
    }

    /**
     * Versuche das setzen der neuen werte für das editierte Objekt
     */
    @FXML
    public void handleOK() {
        try {
            if (isValidEmailAddress(txtMail.getText())) {
                newAnsprechpartner.setName(txtNname.getText());
                newAnsprechpartner.setVorname(txtVname.getText());
                newAnsprechpartner.setTelefon(txtTel.getText());
                newAnsprechpartner.setEmail(txtMail.getText());
                newAnsprechpartner.setUnternehmen(org);
                setAnsp();
                dialogStage.close();
            } else throw new IllegalArgumentException();
        } catch (IllegalArgumentException e) {
            new InfoWindows("FEHLER", "Ungültige Parameter für einen Ansprechpartner", "Ungültiges Format für Email");
        } catch (Exception e) {
            new InfoWindows("FEHLER", null, "Ungültige Parameter für einen Ansprechpartner");
        }

    }

    @FXML
    private void handleCancel() {
        okClicked = false;
        dialogStage.close();
    }

    @FXML

    /**
     * Hinzufügen von Organisationen während des Editierens
     */
    public boolean handleNewOrg() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/orgNewWindow.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Organisation anlegen");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.dialogStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            OrganisationAnlegenController controller = loader.getController();
            controller.setMainApp(this.mainApp);
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();
            setListe(this.organisationList);
            return controller.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * Setze Aktuelle Werte in die TextEdit boxen
     *
     * @param newAnsprechpartner
     */
    public void setNewAnsprechpartner(Ansprechpartner newAnsprechpartner) {
        this.oldAnsprechpartner = newAnsprechpartner;
        setListe(this.organisationList);
        this.newAnsprechpartner = newAnsprechpartner;
        txtVname.setText(newAnsprechpartner.getVorname());
        txtNname.setText(newAnsprechpartner.getName());
        txtMail.setText(newAnsprechpartner.getEmail());
        txtTel.setText(newAnsprechpartner.getTelefon());
        org = newAnsprechpartner.getUnternehmen();
        cb_Org.getSelectionModel().select(newAnsprechpartner.getUnternehmen().getName());
    }

    private void setAnsp() {
        try {

            DBcontroller db = new DBcontroller();
            db.connectDB();
            db.updateAnsprechpartner(newAnsprechpartner, oldAnsprechpartner);
            db.disconnectDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
