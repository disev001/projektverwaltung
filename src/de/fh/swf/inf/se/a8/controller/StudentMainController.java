package de.fh.swf.inf.se.a8.controller;

import de.fh.swf.inf.se.a8.model.Projekt;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeView;

/**
 * Created by dsee on 19.12.2016.
 */
public class StudentMainController{
    @FXML
    private ListView<Projekt> listProjekte;
    @FXML
    private Button btnDetails;
    @FXML
    private Button btnEinreichen;
    @FXML
    private Button btnNewProject;
}
