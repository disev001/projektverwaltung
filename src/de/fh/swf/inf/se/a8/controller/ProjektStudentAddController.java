package de.fh.swf.inf.se.a8.controller;

import de.fh.swf.inf.se.a8.Main;
import de.fh.swf.inf.se.a8.model.Student;
import de.fh.swf.inf.se.a8.view.InfoWindows;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

/**
 * Created by dsee on 20.12.2016.
 */
public class ProjektStudentAddController {
    @FXML
    private ComboBox<Student> cbStudents;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnCancel;
    private Stage dialogStage;
    private Main mainApp;
    private Student selected = null;
    private ObservableList<Student> studentenListe = FXCollections.observableArrayList();
    private boolean isOKclicked = false;
    private ObservableList<Student> activeStudents = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        cbStudents.valueProperty().addListener(new ChangeListener<Student>() {
            @Override
            public void changed(ObservableValue ov, Student t, Student t1) {
                selected = t1;
            }
        });
    }

    private void setList() {

        DBcontroller db = new DBcontroller();
        try {
            db.connectDB();
            studentenListe = db.readStudentTable();
            sortStudentList();
            cbStudents.setItems(studentenListe);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.disconnectDB();
        }

    }

    @FXML
    private void handleAdd() {

        if (selected != null) {
            isOKclicked = true;
            dialogStage.close();
        } else {
            new InfoWindows("Fehler", "Keine Auswahl getroffen", "Bitte wähle einen Studenten welcher zum Projekt hinzugefügt werden soll");
        }
    }

    public boolean isOKclicked() {
        return isOKclicked;
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        setList();
    }

    public Student getSelected() {

        return selected;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    private void sortStudentList() {
        boolean hasChanged = false;

        for (Student s : studentenListe) {
            if (s.getMatrikelnummer() == 0) {
                studentenListe.remove(s);
                hasChanged = true;
                break;
            }
        }
        if (hasChanged) {
            sortStudentList();
        }
    }

    public void setActiveStudents(ObservableList<Student> activStudents) {
        this.activeStudents = activStudents;
        setNonActiveStudents();
    }

    private void setNonActiveStudents() {
        boolean hasChanged = false;
        for (Student s : studentenListe) {
            for (Student a : activeStudents) {
                if (a.getMatrikelnummer() == s.getMatrikelnummer()) {
                    studentenListe.remove(s);
                    hasChanged = true;
                    break;
                }
            }
            if (hasChanged) {
                break;
            }
        }
        if (hasChanged) {
            setNonActiveStudents();
        }
    }
}
