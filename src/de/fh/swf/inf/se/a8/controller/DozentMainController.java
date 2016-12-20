package de.fh.swf.inf.se.a8.controller;

import de.fh.swf.inf.se.a8.model.Projekt;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

/**
 * Created by dsee on 19.12.2016.
 */
public class DozentMainController {
    @FXML
    private ListView<Projekt> listProjekte;
    @FXML
    private Button btnSelect;
    @FXML
    private Button btnDelete;
}
