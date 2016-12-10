package de.fh.swf.inf.se.a8;

import de.fh.swf.inf.se.a8.controller.AnsprechpartnerAnlegenController;
import de.fh.swf.inf.se.a8.controller.AnsprechpartneranzeigeController;
import de.fh.swf.inf.se.a8.controller.OrganisationAnlegenController;
import de.fh.swf.inf.se.a8.model.Ansprechpartner;
import de.fh.swf.inf.se.a8.model.Organisation;
import de.fh.swf.inf.se.a8.model.Projekt;
import de.fh.swf.inf.se.a8.model.Student;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class Main extends Application {
    private AnchorPane rootLayout;
    private Stage primaryStage;
    public ObservableList<Ansprechpartner> ansprechpartners = FXCollections.observableArrayList();

    public ObservableList<Organisation> organisations = FXCollections.observableArrayList();


    public void main(){

    }
    /**
     * Laden der Oberfläche, der Controller und weiteren Initialisierungsdaten
     *
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Pruefungsverwaltung");

        //TODO: Gehört hier nicht rein!
        Organisation org1 = new Organisation("Musterorg", 58762, "Altena", "Amselweg 6a");
        Organisation org2 = new Organisation("FHSWF", 55442, "Iserlohn", "Frauenstuhlweg 31");

        Ansprechpartner an1 = new Ansprechpartner("Katze", "Wasilisa", "dsee@doldrums.de", "02352/546521", org1);
        Ansprechpartner an2 = new Ansprechpartner("Klug", "Uwe", "klug.uwe@fh-swf.de", "02242/8087652", org1);
        ansprechpartners.addAll(an1,an2);
        organisations.addAll(org1,org2);
        //

        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/ansprechpartneranzeige.fxml"));
            rootLayout = loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            AnsprechpartneranzeigeController controller = loader.getController();
            controller.setMainApp(this);
            controller.loadTreeItems();

        } catch (IOException e) {
            e.printStackTrace();
        }
        //Fenster Schließen per Plattformabhängigem Closebutton
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        System.exit(0);
                    }
                });
            }
        });
    }
    /**
     * Laden der Stage für Unternehmen anlegen Dialogfenster
     * @param list aktuelle unternehmen
     *
     * @return reaktion auf die art des fenster schließens
     */
    public boolean showNewOrg(ObservableList<Organisation> list) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/organisationanlegen.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Ogranisation anlegen");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            OrganisationAnlegenController controller= loader.getController();
            controller.setMainApp(this);
            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Laden der Stage für Unternehmen anlegen Dialogfenster
     * @param list aktuelle unternehmen
     *
     * @return reaktion auf die art des fenster schließens
     */
    public boolean showNewAnsp(ObservableList<Organisation> list) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/ansprechpartneranlegen.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Ansprechpartner anlegen");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            AnsprechpartnerAnlegenController controller= loader.getController();
            controller.setMainApp(this);
            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    //Übergabe an fremde Klassen um damit zu interagieren
    public ObservableList<Ansprechpartner> getAnsprechpartners() {
        return ansprechpartners;
    }

    public ObservableList<Organisation> getOrganisations() {
        return organisations;
    }

    //Übergabe der Stage an fremde Klassen um damit zu interagieren
    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
