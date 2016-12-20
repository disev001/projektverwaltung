package de.fh.swf.inf.se.a8.controller;

import de.fh.swf.inf.se.a8.Main;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Node;

/**
 * Created by dsee on 19.12.2016.
 */
public class LoginController {
    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField pw;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnClose;

    private Main mainApp;
    private boolean okClicked = false;
    private Stage dialogStage;


    @FXML
    public void initialize() {
        txtUser.setText("Dozent");
        userevent();
        pwevent();

    }

    @FXML
    public void handleOK() {
        okClicked = true;
        if (isStudent())
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("view/studiMain.fxml"));
                AnchorPane page = loader.load();
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Studenten Projektverwaltung");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(this.dialogStage);
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);
                StudentMainController controller = loader.getController();
                controller.setMainApp(mainApp);
                controller.setDialogStage(dialogStage);
                mainApp.getPrimaryStage().close();
                dialogStage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        if (isDozent())
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("view/dozentMain.fxml"));
                AnchorPane page = loader.load();
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Dozent Projektverwaltung");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(this.dialogStage);
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);
                DozentMainController controller = loader.getController();
                controller.setMainApp(mainApp);
                controller.setDialogStage(dialogStage);
                controller.setList();
                mainApp.getPrimaryStage().close();
                dialogStage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    /**
     * Abbruch ohne bestätigung
     */
    @FXML
    private void handleCancel() {
        System.exit(0);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public boolean isDozent() {
        if (txtUser.getText().equals("Dozent"))
            return true;
        else return false;
    }

    public boolean isStudent() {
        if (txtUser.getText().equals("Student"))
            return true;
        else return false;
    }


    private void userevent() {
        txtUser.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    btnLogin.fire();
                }
            }
        });
    }

    private void pwevent() {
        pw.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    btnLogin.fire();
                }
            }
        });
    }

}
