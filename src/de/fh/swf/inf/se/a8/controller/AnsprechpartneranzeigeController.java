package de.fh.swf.inf.se.a8.controller;

import de.fh.swf.inf.se.a8.Main;
import de.fh.swf.inf.se.a8.model.Ansprechpartner;
import de.fh.swf.inf.se.a8.model.Organisation;
import de.fh.swf.inf.se.a8.model.TreeViewHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
    @FXML
    private Label lblAPname;
    @FXML
    private Label lblAPmail;
    @FXML
    private Label lblAPtel;
    @FXML
    private Label lblAPorg;
    @FXML
    private Label lblAPansch;

    private ObservableList<Ansprechpartner> ansprechpartnerList;
    private ObservableList<Organisation> organisationList;


    private Main mainApp;

    // the initialize method is automatically invoked by the FXMLLoader - it's magic
    @FXML
    public void initialize() {
        //TODO: Auswahlhandler

        tv_AP.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<TreeItem<String>>() {

                    @Override
                    public void changed(
                            ObservableValue<? extends TreeItem<String>> observable,
                            TreeItem<String> old_val, TreeItem<String> new_val) {
                        for (Organisation o : organisationList) {
                            if (o.getName().equals(new_val.getValue())) {

                                lblAPname.setText("");
                                lblAPmail.setText("");
                                lblAPtel.setText("");
                                lblAPorg.setText(new_val.getValue());
                                lblAPansch.setText(o.getPlz() + " " + o.getOrt() + "\n" + o.getStrasse());
                            } else {
                                for (Ansprechpartner a : ansprechpartnerList) {
                                    String cache = a.getName() + ", " + a.getVorname();
                                    if (cache.equals(new_val.getValue())) {
                                        lblAPname.setText(new_val.getValue());
                                        lblAPmail.setText(a.getEmail());
                                        lblAPtel.setText(a.getTelefon());
                                        lblAPorg.setText(a.getUnternehmen().getName());
                                        lblAPansch.setText(a.getUnternehmen().getPlz() + " " + a.getUnternehmen().getOrt() + "\n" + a.getUnternehmen().getStrasse());
                                    }
                                }
                            }
                        }
                    }
                });
    }

    // loads some strings into the tree in the application UI.

    public void loadTreeItems() {
        TreeViewHelper helper = new TreeViewHelper(ansprechpartnerList, organisationList);
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
    public void handleNewOrg() {
        if(this.mainApp.showNewOrg(this.organisationList))
        {
            loadTreeItems();
        }

    }

    @FXML
    public void handleNewAnsprechpartner() {
        if (this.mainApp.showNewAnsp(this.organisationList))
        {
            loadTreeItems();
        }
    }
}
