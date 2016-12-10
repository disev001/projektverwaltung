package de.fh.swf.inf.se.a8.controller;

import de.fh.swf.inf.se.a8.Main;
import de.fh.swf.inf.se.a8.model.Ansprechpartner;
import de.fh.swf.inf.se.a8.model.Organisation;
import de.fh.swf.inf.se.a8.model.TreeViewHelper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;

/**
 * Created by dsee on 09.12.2016.
 */
public class AnsprechpartneranzeigeController {
    @FXML
    private TreeView<String> tv_AP;

    private ObservableList<Ansprechpartner> ansprechpartnerList;
    private ObservableList<Organisation> organisationList;


    private Main mainApp;

    // the initialize method is automatically invoked by the FXMLLoader - it's magic
    @FXML
    public void initialize() {

    }

    // loads some strings into the tree in the application UI.

    public void loadTreeItems() {
        TreeViewHelper helper = new TreeViewHelper(ansprechpartnerList,organisationList);
        ArrayList<TreeItem> unternehmen = helper.getUnternehmen();
        TreeItem rootItem = new TreeItem("Unternehmen");
        rootItem.setExpanded(false);
        rootItem.getChildren().addAll(unternehmen);
        tv_AP.setShowRoot(false);
        tv_AP.setRoot(rootItem);

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
    public  void handleNewOrg (){
       this.mainApp.showNewOrg(this.organisationList);
    }

    @FXML
    public  void handleNewAnsprechpartner (){
        this.mainApp.showNewAnsp(this.organisationList);
    }
}
