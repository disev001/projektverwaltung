package de.fh.swf.inf.se.a8.controller;

import de.fh.swf.inf.se.a8.Main;
import de.fh.swf.inf.se.a8.model.Ansprechpartner;
import de.fh.swf.inf.se.a8.model.Organisation;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

/**
 * Created by dsee on 10.12.2016.
 */
public class AnsprechpartnerAnlegenController {
    //TODO: Editable bool für offenes Org fenster
    private ObservableList<Ansprechpartner> ansprechpartnerList;
    private boolean okClicked = false;
    private Main mainApp;
    private ObservableList<Organisation> organisationList;

    public boolean isOkClicked() {
        return okClicked;
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        setListe();
    }

    public void setListe() {
        this.ansprechpartnerList = this.mainApp.getAnsprechpartners();
        this.organisationList = this.mainApp.getOrganisations();
    }

    @FXML
    public void handleNewOrg() {
        this.mainApp.showNewOrg(this.organisationList);
    }
}
