package de.fh.swf.inf.se.a8.controller;

import de.fh.swf.inf.se.a8.Main;
import de.fh.swf.inf.se.a8.model.Ansprechpartner;
import de.fh.swf.inf.se.a8.model.Organisation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

/**
 * Created by dsee on 10.12.2016.
 */
public class AnsprechpartnerAnlegenController {
    //TODO: Editable bool für offenes Org fenster
    @FXML
    private ComboBox<String> cb_Org;

    private ObservableList<Ansprechpartner> ansprechpartnerList;
    private boolean okClicked = false;
    private Main mainApp;
    private ObservableList<Organisation> organisationList;


    /**
     * initialisiert handler der Stage
     */
    @FXML
    private void initialize() {

    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        setListe();
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
    public void handleNewOrg() {
        this.mainApp.showNewOrg(this.organisationList);
    }
}
