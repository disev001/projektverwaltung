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
import java.util.Optional;

/**
 * Created by dsee on 09.12.2016.
 */
public class AnsprechpartneranzeigeController {
    @FXML
    private TreeView<Object> tv_AP;
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
    @FXML
    private MenuItem miSave;
    @FXML
    private MenuItem miClose;
    @FXML
    private MenuItem miAddAP;
    @FXML
    private MenuItem miAddOrg;
    @FXML
    private MenuItem miEdit;
    @FXML
    private MenuItem miDel;
    @FXML
    private MenuItem miAbout;
    private ObservableList<Ansprechpartner> ansprechpartnerList;
    private ObservableList<Organisation> organisationList;
    private Ansprechpartner selectedAP;
    private Organisation selectedOrg;
    private boolean isOrg;

    private Main mainApp;

    // the initialize method is automatically invoked by the FXMLLoader - it's magic
    @FXML
    public void initialize() {
        //TODO: Auswahlhandler

        tv_AP.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<Object>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<Object>> observable, TreeItem<Object> oldValue, TreeItem<Object> newValue) {

                if (newValue.getValue() instanceof Organisation) {
                    selectedOrg = (Organisation) newValue.getValue();
                    isOrg = true;
                    lblAPname.setText("");
                    lblAPmail.setText("");
                    lblAPtel.setText("");
                    lblAPorg.setText(selectedOrg.getName());
                    lblAPansch.setText(selectedOrg.getPlz() + " " + selectedOrg.getOrt() + "\n" + selectedOrg.getStrasse());

                } else if (newValue.getValue() instanceof Ansprechpartner) {
                    selectedAP = (Ansprechpartner) newValue.getValue();
                    isOrg = false;
                    lblAPname.setText(selectedAP.getName() + ", " + selectedAP.getVorname());
                    lblAPmail.setText(selectedAP.getEmail());
                    lblAPtel.setText(selectedAP.getTelefon());
                    lblAPorg.setText(selectedAP.getUnternehmen().getName());
                    lblAPansch.setText(selectedAP.getUnternehmen().getPlz() + " " + selectedAP.getUnternehmen().getOrt() + "\n" + selectedAP.getUnternehmen().getStrasse());
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
        if (this.mainApp.showNewOrg()) {
            loadTreeItems();
        }
    }

    @FXML
    public void handleNewAnsprechpartner() {
        if (this.mainApp.showNewAnsp(this.organisationList)) {
            loadTreeItems();
        }
    }

    @FXML
    public boolean handelDelete() {
        boolean found = false;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Look, a Confirmation Dialog");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            while (!found) {
                if (isOrg) {
                    for (Organisation o : organisationList) {
                        if (o.equals(selectedOrg)) {
                            organisationList.remove(o);
                            loadTreeItems();
                            found = true;
                            break;
                        }
                    }
                } else for (Ansprechpartner a : ansprechpartnerList) {
                    if (a.equals(selectedAP)) {
                        ansprechpartnerList.remove(a);
                        loadTreeItems();
                        found = true;
                        break;
                    }
                }
            }
        }
        return found;
    }

    @FXML
    public void handleEdit() {
        if (isOrg) {
            for (Organisation o : organisationList) {
                if (o.equals(selectedOrg)) {
                    this.mainApp.showEditOrg(o);
                }
                loadTreeItems();
            }
        } else for (Ansprechpartner a : ansprechpartnerList) {
            if (a.equals(selectedAP)) {
                this.mainApp.showEditAnsp(a);
            }
            loadTreeItems();
        }
    }

    @FXML
    public  void  handleSave(){
        DBcontroller db = new DBcontroller();
        db.trunkTable();
        db.fillOrgTable(this.organisationList);
        db.fillAnspTable(this.ansprechpartnerList,this.organisationList);
    }
}