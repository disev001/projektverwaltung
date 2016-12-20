package de.fh.swf.inf.se.a8.controller;

import de.fh.swf.inf.se.a8.model.Ansprechpartner;
import de.fh.swf.inf.se.a8.model.Student;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Created by dsee on 19.12.2016.
 */
public class ProjektAnzeigenDozentContoller {
    @FXML
    private Label lblProjekttitel;
    @FXML
    private Button btnDbeschreibung;
    @FXML
    private Button btnDskizze;
    @FXML
    private Button btnAddAnsp;
    @FXML
    private RadioButton radio1;
    @FXML
    private RadioButton radio2;
    @FXML
    private RadioButton radio3;
    @FXML
    private Label lblAnsprechpartner;
    @FXML
    private TextArea tfNote;
    @FXML
    private Button btnOK;
    @FXML
    private Button btnCancel;
    @FXML
    private ListView<Student> listTeilnehmer;
    @FXML
    private Button btnAddStudent;
}
