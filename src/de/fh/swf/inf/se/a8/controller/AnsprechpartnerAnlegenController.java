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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by dsee on 10.12.2016.
 */
public class AnsprechpartnerAnlegenController {
    //TODO: Editable bool für offenes Org fenster
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


    /**
     * initialisiert handler der Stage
     */
    @FXML
    private void initialize() {
        cb_Org.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                for (Organisation o : organisationList) {
                    if(o.getName().equals(newValue)){
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
    public void setListe() {
        ObservableList<String> organisationAuswahl = FXCollections.observableArrayList();
        this.ansprechpartnerList = this.mainApp.getAnsprechpartners();
        this.organisationList = this.mainApp.getOrganisations();
        for (Organisation o : organisationList) {
          organisationAuswahl.add(o.getName());

        }
        cb_Org.setItems(organisationAuswahl);

    }
    @FXML
    public void handleOK(){
        try {
            ansprechpartnerList.add(new Ansprechpartner(txtNname.getText(), txtVname.getText(), txtMail.getText(), txtTel.getText(),org));
            okClicked=true;
        } catch (Exception e) {
            new InfoWindows("FEHLER", null, "SO NICHT!");
        }
        dialogStage.close();
    }
    @FXML
    public boolean handleNewOrg() {

        return this.mainApp.showNewOrg(this.organisationList);
    }
    @FXML
    private void handleCancel() {
        okClicked = false;
        dialogStage.close();
    }
}
