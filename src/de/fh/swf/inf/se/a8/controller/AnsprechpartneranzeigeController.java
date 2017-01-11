package de.fh.swf.inf.se.a8.controller;

import de.fh.swf.inf.se.a8.Main;
import de.fh.swf.inf.se.a8.model.Ansprechpartner;
import de.fh.swf.inf.se.a8.model.Organisation;
import de.fh.swf.inf.se.a8.model.TreeViewHelper;
import de.fh.swf.inf.se.a8.view.InfoWindows;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by dsee on 09.12.2016.
 */
public class AnsprechpartneranzeigeController {
    @FXML
    private TreeView<Object> tv_AP;
    @FXML
    private Button ok;
    @FXML
    private Label name;
    @FXML
    private Label email;
    @FXML
    private Label tel;
    @FXML
    private Label lblAPname;
    @FXML
    private Hyperlink lblAPmail;
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

    private ObservableList<Organisation> organisationList = FXCollections.observableArrayList();

    private Ansprechpartner selectedAP;
    private Organisation selectedOrg;
    // private boolean isOrg;
    private SimpleBooleanProperty isOrgP = new SimpleBooleanProperty(false);
    private Stage dialogStage;
    private Main mainApp;
    private boolean settinAnsp = true;


    private boolean isOkClicked;

    /**
     * initialisiert handler der Stage
     */
    @FXML
    public void initialize() {
        isOrgP.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                ok.setDisable(newValue);
            }
        });
        lblAPmail.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() >= 2) {
                    if (Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();
                        if (desktop.isSupported(Desktop.Action.MAIL)) {
                            try {
                                desktop.mail(new URI("mailto:" + lblAPmail.getText())); // alternately, pass a mailto: URI in here
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
        tv_AP.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<Object>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<Object>> observable, TreeItem<Object> oldValue, TreeItem<Object> newValue) {
                try {

                    if (newValue.getValue() instanceof Organisation) {
                        selectedOrg = (Organisation) newValue.getValue();
                        isOrgP.setValue(true);
                        name.setVisible(false);
                        tel.setVisible(false);
                        email.setVisible(false);
                        lblAPname.setText("");
                        lblAPmail.setText("");
                        lblAPtel.setText("");
                        lblAPorg.setText(selectedOrg.getName());
                        String plz;
                        if (selectedOrg.getPlz() == 0)
                            plz = "";
                        else plz = Integer.toString(selectedOrg.getPlz());
                        lblAPansch.setText(plz + " " + selectedOrg.getOrt() + "\n" + selectedOrg.getStrasse());
                        ok.setDisable(true);
                    }
                     if (newValue.getValue() instanceof Ansprechpartner) {
                        name.setVisible(true);
                        tel.setVisible(true);
                        email.setVisible(true);
                        selectedAP = (Ansprechpartner) newValue.getValue();
                        isOrgP.setValue(false);
                        lblAPname.setText(selectedAP.getName() + ", " + selectedAP.getVorname());
                        lblAPmail.setText(selectedAP.getEmail());
                        lblAPtel.setText(selectedAP.getTelefon());
                        lblAPorg.setText(selectedAP.getUnternehmen().getName());
                        String plz;
                        if (selectedAP.getUnternehmen().getPlz() == 0)
                            plz = "";
                        else plz = Integer.toString(selectedAP.getUnternehmen().getPlz());
                        lblAPansch.setText(plz + " " + selectedAP.getUnternehmen().getOrt() + "\n" + selectedAP.getUnternehmen().getStrasse());
                        ok.setDisable(false);
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
        ok.setDisable(true);
    }

    public void setListe() {

        DBcontroller db = new DBcontroller();
        try {
            db.connectDB();
            organisationList = db.readOrgTable();
            ansprechpartnerList = db.readAnspTable(organisationList);
        } catch (Exception e) {
            new InfoWindows("Fehler", "Ansprechpartner konnten nicht abgerufen werden", e.getLocalizedMessage());
        } finally {
            db.disconnectDB();
        }


        loadTreeItems();
        //  this.ansprechpartnerList = this.mainApp.getAnsprechpartners();
        //  this.organisationList = this.mainApp.getOrganisations();
    }


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
                organisationList.add(controller.getNewOrg());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            loadTreeItems();
        }
    }

    @FXML
    public void handleNewAnsprechpartner() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/anspNewWindow.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Ansprechpartner anlegen");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.dialogStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            AnsprechpartnerAnlegenController controller = loader.getController();
            controller.setMainApp(this.mainApp);
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();
            if (controller.isOkClicked()) {
                ansprechpartnerList.add(controller.getNewAnsprechpartner());
                if (controller.getNewOrg() != null)
                    organisationList.add(controller.getNewOrg());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        loadTreeItems();
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
        if (isOrgP.getValue()) {
            alert.setHeaderText("Lösche Organisation");
            alert.setContentText("Lösche  Organisation " + selectedOrg.getName() + " und alle zugehörigen Ansprechpartner und Projekte?");
        } else {
            alert.setHeaderText("Lösche  Ansprechpartner");
            alert.setContentText("Lösche  Ansprechpartner " + selectedAP.getName() + ", " + selectedAP.getVorname()+"und alle zugehörigen Projekte?");
        }
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            while (!found) {
                if (isOrgP.getValue()) {
                    for (Organisation o : organisationList) {
                        if (o.equals(selectedOrg)) {
                            for (Ansprechpartner a : ansprechpartnerList) {
                                if (a.getUnternehmen().equals(o)) {
                                    try {
                                        ansprechpartnerList.remove(a);
                                        break;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            DBcontroller db = new DBcontroller();
                            db.connectDB();
                            if (db.deleteOrg(o))
                                organisationList.remove(o);
                            db.disconnectDB();
                            loadTreeItems();
                            found = true;
                            break;
                        }
                    }
                } else for (Ansprechpartner a : ansprechpartnerList) {
                    if (a.equals(selectedAP)) {
                        try {
                            DBcontroller db = new DBcontroller();
                            db.connectDB();
                            if (db.deleteAnsprechpartner(a)) ;
                            ansprechpartnerList.remove(a);
                            db.disconnectDB();
                            loadTreeItems();
                            found = true;
                            break;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
        if (isOrgP.getValue()) {
            for (Organisation o : organisationList) {
                if (o.equals(selectedOrg)) {
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(Main.class.getResource("view/orgEditWindow.fxml"));
                        AnchorPane page = loader.load();
                        Stage dialogStage = new Stage();
                        dialogStage.setTitle("Ansprechpartner anlegen");
                        dialogStage.initModality(Modality.WINDOW_MODAL);
                        dialogStage.initOwner(this.dialogStage);
                        Scene scene = new Scene(page);
                        dialogStage.setScene(scene);
                        OrganisationEditierenController controller = loader.getController();
                        controller.setMainApp(this.mainApp);
                        controller.setDialogStage(dialogStage);
                        controller.setOrg(o);
                        dialogStage.showAndWait();
                        loadTreeItems();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                loadTreeItems();
            }
        } else for (Ansprechpartner a : ansprechpartnerList) {
            if (a.equals(selectedAP)) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Main.class.getResource("view/anspEditWindow.fxml"));
                    AnchorPane page = loader.load();
                    Stage dialogStage = new Stage();
                    dialogStage.setTitle("Ansprechpartner Editieren");
                    dialogStage.initModality(Modality.WINDOW_MODAL);
                    dialogStage.initOwner(this.dialogStage);
                    Scene scene = new Scene(page);
                    dialogStage.setScene(scene);
                    AnsprechpartnerEditierenController controller = loader.getController();
                    controller.setMainApp(this.mainApp);
                    controller.setDialogStage(dialogStage);
                    controller.setListe(organisationList);
                    controller.setNewAnsprechpartner(a);
                    dialogStage.showAndWait();
                    loadTreeItems();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

    @FXML
    void handleOK() {
        isOkClicked = true;
        dialogStage.close();
    }

    public void setSettinAnsp(boolean settinAnsp) {
        this.settinAnsp = settinAnsp;
        if (!settinAnsp)
            isOrgP.set(true);
    }
    public boolean isOkClicked() {
        return isOkClicked;
    }

    public Ansprechpartner getNewAP() {
        return selectedAP;
    }
}