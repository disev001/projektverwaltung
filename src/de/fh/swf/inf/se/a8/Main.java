package de.fh.swf.inf.se.a8;

import de.fh.swf.inf.se.a8.controller.*;
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

    public ObservableList<Student> students = FXCollections.observableArrayList();
    public ObservableList<Ansprechpartner> ansprechpartners = FXCollections.observableArrayList();
    public ObservableList<Organisation> organisations = FXCollections.observableArrayList();
    public ObservableList<Projekt> projekts = FXCollections.observableArrayList();
    public ObservableList<Student> dozents = FXCollections.observableArrayList();

    public void main() {

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

        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/loginWindow.fxml"));
            rootLayout = loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            LoginController controller = loader.getController();
            controller.setMainApp(this);

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
     *
     * @return reaktion auf die art des fenster schließens
     */
    public boolean showNewOrg() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/orgNewWindow.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Ogranisation anlegen");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            OrganisationAnlegenController controller = loader.getController();
            controller.setMainApp(this);
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Laden der Stage für Unternehmen editieren Dialogfenster
     *
     * @param o aktuelle unternehmen
     * @return reaktion auf die art des fenster schließens
     */
    public boolean showEditOrg(Organisation o) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/orgEditWindow.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Ansprechpartner anlegen");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            OrganisationEditierenController controller = loader.getController();
            controller.setMainApp(this);
            controller.setDialogStage(dialogStage);
            controller.setOrg(o);
            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Laden der Stage für Ansprechpartner editieren Dialogfenster
     *
     * @param a aktueller Ansprechpartner
     * @return reaktion auf die art des fenster schließens
     */
    public boolean showEditAnsp(Ansprechpartner a) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/anspEditWindow.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Ansprechpartner Editieren");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            AnsprechpartnerEditierenController controller = loader.getController();
            controller.setMainApp(this);
            controller.setDialogStage(dialogStage);
            controller.setAnsprechpartner(a);
            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Laden der Stage für Unternehmen anlegen Dialogfenster
     *
     * @param list aktuelle unternehmen
     * @return reaktion auf die art des fenster schließens
     */
    public boolean showNewAnsp(ObservableList<Organisation> list) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/anspNewWindow.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Ansprechpartner anlegen");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            AnsprechpartnerAnlegenController controller = loader.getController();
            controller.setMainApp(this);
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
/*    private void getDBdata() {
        DBcontroller db = new DBcontroller();
        db.connectDB();
        db.loginUser("severgin.dieter@fh-swf.de","test");
        students = db.readStudentTable();
        for (Student s : students)
        {
            if(s.getMatrikelnummer() == 0) {
                dozents.add(s);
                students.remove(s);
            }
        }
        organisations = db.readOrgTable();
        ansprechpartners = db.readAnspTable(organisations);
        db.disconnectDB();
    } */
    //Übergabe an fremde Klassen um damit zu interagieren
    public ObservableList<Ansprechpartner> getAnsprechpartners() {
        return ansprechpartners;
    }
    public ObservableList<Organisation> getOrganisations() {
        return organisations;
    }
    public ObservableList<Student> getStudents() {
        return students;
    }
    public ObservableList<Projekt> getProjekts() {
        return projekts;
    }
    public ObservableList<Student> getDozents() {
        return dozents;
    }

    //Übergabe der Stage an fremde Klassen um damit zu interagieren
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    //Schließe per MenuItem
    public void closeApp() {
        System.exit(0);
    }
}
