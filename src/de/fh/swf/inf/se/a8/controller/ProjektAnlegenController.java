package de.fh.swf.inf.se.a8.controller;

import de.fh.swf.inf.se.a8.model.Ansprechpartner;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.Objects;

/**
 * Created by dsee on 19.12.2016.
 */
public class ProjektAnlegenController {
    @FXML
    private TextField txtTitel;
    @FXML
    private ComboBox<Objects> cbDozentM;
    @FXML
    private ComboBox<Ansprechpartner> cbAnsprechpartner;
    @FXML
    private Button btnOK;
    @FXML
    private Button btnCancel;

}
