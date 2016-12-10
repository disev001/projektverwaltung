package de.fh.swf.inf.se.a8.controller;

import de.fh.swf.inf.se.a8.Main;
import de.fh.swf.inf.se.a8.model.Organisation;
import javafx.collections.ObservableList;

/**
 * Created by dsee on 10.12.2016.
 */
public class OrganisationAnlegenController {
    private ObservableList<Organisation> organisationList;
    private boolean okClicked = false;
    private Main mainApp;

    public boolean isOkClicked() {
        return okClicked;
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        setListe();
    }

    public void setListe() {
        this.organisationList = this.mainApp.getOrganisations();
    }
}
