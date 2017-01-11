package de.fh.swf.inf.se.a8.controller;

import de.fh.swf.inf.se.a8.Main;
import de.fh.swf.inf.se.a8.model.Ansprechpartner;
import de.fh.swf.inf.se.a8.model.Student;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by dsee on 08.01.2017.
 */
public class ContactController {

    @FXML
    private Label lblName;
    @FXML
    private Hyperlink lblEmail;
    @FXML
    private Label lblTel;
    @FXML
    private Label lblOrg;
    @FXML
    private Label lblAnsp;
    @FXML
    private Label name;
    @FXML
    private Label mail;
    @FXML
    private Label tel;
    @FXML
    private Label org;
    @FXML
    private Label anschrift;
    private Main mainApp;
    private Stage dialogStage;
    private Student student;
    private Ansprechpartner ansprechpartner;


    @FXML
    private void initialize() {
        lblEmail.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() >= 2) {
                    if (Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();
                        if (desktop.isSupported(Desktop.Action.MAIL)) {
                            try {
                                desktop.mail(new URI("mailto:" + lblEmail.getText())); // alternately, pass a mailto: URI in here
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
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void handleOK() {
        dialogStage.close();
    }

    public void setObject(Object object) {
        if (object instanceof Student) {
            this.student = (Student) object;
            lblName.setText(student.getNachname() + ", " + student.getVorname());
            lblEmail.setText(student.getEmail());
            tel.setVisible(false);
            org.setVisible(false);
            anschrift.setVisible(false);
        }
        if (object instanceof Ansprechpartner) {
            this.ansprechpartner = (Ansprechpartner) object;
            lblName.setText(ansprechpartner.getName() + ", " + ansprechpartner.getVorname());
            lblEmail.setText(ansprechpartner.getEmail());
            lblTel.setText(ansprechpartner.getTelefon());
            lblOrg.setText(ansprechpartner.getUnternehmen().getName());
            String plz;
            if (ansprechpartner.getUnternehmen().getPlz() == 0)
                plz = "";
            else plz = Integer.toString(ansprechpartner.getUnternehmen().getPlz());
            lblAnsp.setText(plz + " " + ansprechpartner.getUnternehmen().getOrt() + "\n" + ansprechpartner.getUnternehmen().getStrasse());
        }
    }
}
