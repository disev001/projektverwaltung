package de.fh.swf.inf.se.a8.controller;

import de.fh.swf.inf.se.a8.model.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

/**
 * Created by dsee on 19.12.2016.
 */
public class ProjektAnzeigenController {

    @FXML
    private Label lblProjekttitel;
    @FXML
    private Label lblEnscheidung;
    @FXML
    private TextArea tfComment;
    @FXML
    private Button btnOK;
    @FXML
    private Button btnCancel;
    @FXML
    private ListView<Student> listTeilnehmer;
}
