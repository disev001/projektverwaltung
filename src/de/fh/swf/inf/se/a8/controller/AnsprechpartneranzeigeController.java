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
import javafx.stage.Stage;

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
    private ObservableList<Ansprechpartner> ansprechpartnerList = FXCollections.observableArrayList();
    ;
    private ObservableList<Organisation> organisationList = FXCollections.observableArrayList();
    ;
    private Ansprechpartner selectedAP;
    private Organisation selectedOrg;
    private boolean isOrg;
    private Stage dialogStage;
    private Main mainApp;

    /**
     * initialisiert handler der Stage
     */
    @FXML
    public void initialize() {

        tv_AP.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<Object>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<Object>> observable, TreeItem<Object> oldValue, TreeItem<Object> newValue) {
                try {


                    if (newValue.getValue() instanceof Organisation) {
                        selectedOrg = (Organisation) newValue.getValue();
                        isOrg = true;
                        lblAPname.setText("");
                        lblAPmail.setText("");
                        lblAPtel.setText("");
                        lblAPorg.setText(selectedOrg.getName());
                        String plz;
                        if (selectedOrg.getPlz() == 0)
                            plz = "";
                        else plz = Integer.toString(selectedOrg.getPlz());
                        lblAPansch.setText(plz + " " + selectedOrg.getOrt() + "\n" + selectedOrg.getStrasse());

                    } else if (newValue.getValue() instanceof Ansprechpartner) {
                        selectedAP = (Ansprechpartner) newValue.getValue();
                        isOrg = false;
                        lblAPname.setText(selectedAP.getName() + ", " + selectedAP.getVorname());
                        lblAPmail.setText(selectedAP.getEmail());
                        lblAPtel.setText(selectedAP.getTelefon());
                        lblAPorg.setText(selectedAP.getUnternehmen().getName());
                        String plz;
                        if (selectedOrg.getPlz() == 0)
                            plz = "";
                        else plz = Integer.toString(selectedOrg.getPlz());
                        lblAPansch.setText(plz + " " + selectedAP.getUnternehmen().getOrt() + "\n" + selectedAP.getUnternehmen().getStrasse());
                    }
                } catch (Exception e) {
                    ;
                }
            }
        });
    }

    /**
     * Lade der Listeninhalte in TreeItems und setzte sie in TreeView
     * wird nach jeder änderung an listen aufgerufen
     */
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
        Organisation org1 = new Organisation("Musterorg", 58762, "Altena", "Amselweg 6a");
        Organisation org2 = new Organisation("FHSWF", 55442, "Iserlohn", "Frauenstuhlweg 31");
        Ansprechpartner an1 = new Ansprechpartner("Katze", "Wasilisa", "dsee@doldrums.de", "02352/546521", org1);
        Ansprechpartner an2 = new Ansprechpartner("Klug", "Uwe", "klug.uwe@fh-swf.de", "02242/8087652", org1);

        this.organisationList.addAll(org1, org2);
        this.ansprechpartnerList.addAll(an1, an2);
        loadTreeItems();
        //  this.ansprechpartnerList = this.mainApp.getAnsprechpartners();
        //  this.organisationList = this.mainApp.getOrganisations();
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

    /**
     * Löschen der Aktuellen TreeView auswahl
     *
     * @return erfolgs bool
     */
    @FXML
    public boolean handelDelete() {
        boolean found = false;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Löschen");
        if (isOrg) {
            alert.setHeaderText("Lösche Organisation");
            alert.setContentText("Lösche  Organisation " + selectedOrg.getName() + " und alle zugehörigen Ansprechpartner?");
        } else {
            alert.setHeaderText("Lösche  Ansprechpartner");
            alert.setContentText("Lösche  Ansprechpartner " + selectedAP.getName() + ", " + selectedAP.getVorname() + " und alle zugehörigen Ansprechpartner?");
        }
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            while (!found) {
                if (isOrg) {
                    for (Organisation o : organisationList) {
                        if (o.equals(selectedOrg)) {
                            for (Ansprechpartner a : ansprechpartnerList) {
                                if (a.getUnternehmen().equals(o)) {
                                    ansprechpartnerList.remove(a);
                                    break;
                                }
                            }
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

    /**
     * Editieren der aktuellen TreeView auswahl
     */
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

    /**
     * Leere aktuelle Tabelle und persistiere die aktuellen Listeninhalte
     */
    @FXML
    public void handleSave() {
        DBcontroller db = new DBcontroller();
        db.trunkTable();
        db.fillOrgTable(this.organisationList);
        db.fillAnspTable(this.ansprechpartnerList, this.organisationList);
    }

    @FXML
    private void handleCancel() {
        mainApp.closeApp();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    @FXML void handleOK(){
            dialogStage.close();
    }

}