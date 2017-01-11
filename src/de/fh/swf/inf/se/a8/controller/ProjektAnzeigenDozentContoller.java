package de.fh.swf.inf.se.a8.controller;

import de.fh.swf.inf.se.a8.Main;
import de.fh.swf.inf.se.a8.model.Projekt;
import de.fh.swf.inf.se.a8.model.Student;
import de.fh.swf.inf.se.a8.view.InfoWindows;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

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
    private Button delStudent;
    @FXML
    private RadioButton radio2;
    @FXML
    private RadioButton radio3;
    @FXML
    private Label lblAnsprechpartner;
    @FXML
    private TextArea tfComment;
    @FXML
    private Button btnOK;
    @FXML
    private Button btnCancel;
    @FXML
    private ListView<Student> listTeilnehmer;
    @FXML
    private Button btnAddStudent;
    private Stage dialogStage;
    ObservableList<Student> students = FXCollections.observableArrayList();
    private Projekt selected;
    private Projekt oldProject = null;
    private Main mainApp;
    private int entscheidung;
    private Student selectedStudent = null;
    String beschreibungName = "";
    String skizzeName = "";
    private boolean isOKclicked;

    public void initialize() {
        ToggleGroup group = new ToggleGroup();
        radio1.setToggleGroup(group);
        radio2.setToggleGroup(group);
        radio3.setToggleGroup(group);
        tfComment.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                selected.setKommentar(newValue);
            }
        });
        btnDskizze.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Skizze");
                try {

                    fileChooser.setInitialFileName(skizzeName);
                    String fileArray[] = skizzeName.split("\\.");
                    FileChooser.ExtensionFilter extFilter =
                            new FileChooser.ExtensionFilter("" + fileArray[fileArray.length - 1], "*." + fileArray[fileArray.length - 1]);
                    fileChooser.getExtensionFilters().add(extFilter);
                    File file = fileChooser.showSaveDialog(dialogStage);

                    if (file != null) {
                        DBcontroller db = new DBcontroller();
                        try {

                            db.connectDB();
                            db.getSkizze(selected, file);

                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            db.disconnectDB();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btnDbeschreibung.setOnAction(new EventHandler<ActionEvent>() {
                                         public void handle(ActionEvent event) {
                                             FileChooser fileChooser = new FileChooser();
                                             fileChooser.setTitle("Save Skizze");
                                             try {
                                                 fileChooser.setInitialFileName(beschreibungName);
                                                 String fileArray[] = beschreibungName.split("\\.");
                                                 FileChooser.ExtensionFilter extFilter =
                                                         new FileChooser.ExtensionFilter("" + fileArray[fileArray.length - 1], "*." + fileArray[fileArray.length - 1]);
                                                 fileChooser.getExtensionFilters().add(extFilter);
                                                 File file = fileChooser.showSaveDialog(dialogStage);

                                                 if (file != null) {
                                                     DBcontroller db = new DBcontroller();
                                                     try {
                                                         db.connectDB();
                                                         db.getBeschreibung(selected, file);

                                                     } catch (Exception e) {
                                                         e.printStackTrace();
                                                     } finally {
                                                         db.disconnectDB();
                                                     }
                                                 }
                                             } catch (Exception e) {
                                                 e.printStackTrace();
                                             }
                                         }
                                     }
        );
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {

                if (radio1 == (RadioButton) t1.getToggleGroup().getSelectedToggle()) {
                    selected.setEntscheidung(1);
                }
                if (radio2 == (RadioButton) t1.getToggleGroup().getSelectedToggle()) {
                    selected.setEntscheidung(2);
                }
                if (radio3 == (RadioButton) t1.getToggleGroup().getSelectedToggle()) {
                    selected.setEntscheidung(3);
                }
            }
        });

        listTeilnehmer.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Student>() {
            @Override
            public void changed(ObservableValue<? extends Student> observable, Student oldValue, Student newValue) {
                delStudent.setDisable(false);
                selectedStudent = newValue;
            }
        });

        listTeilnehmer.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() >= 2) {
                    showStudent(selectedStudent);
                }
            }

        });

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setSelected(Projekt selected) {
        this.oldProject= selected;
        this.selected = selected;
        lblProjekttitel.setText(selected.getProjekttitel());
        lblAnsprechpartner.setText(selected.getAnsprechpartner().getName() + ", " + selected.getAnsprechpartner().getVorname());
        tfComment.setText(selected.getKommentar());
        if (selected.getEntscheidung() == 1) {
            radio1.setSelected(true);
        } else if (selected.getEntscheidung() == 2) {
            radio2.setSelected(true);
        } else if (selected.getEntscheidung() == 3) {
            radio3.setSelected(true);
        } else {
            radio1.setSelected(false);
            radio2.setSelected(false);
            radio3.setSelected(false);
        }
        setStudentenListe();
        DBcontroller db = new DBcontroller();

        try {
            db.connectDB();
            beschreibungName = db.getBeschreibungName(selected);
            skizzeName = db.getSkizzeName(selected);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (beschreibungName.isEmpty()) {
                btnDbeschreibung.setDisable(true);
                btnDbeschreibung.setText("nicht vorliegend");
            } else btnDbeschreibung.setText("Download " + beschreibungName);
            if (skizzeName.isEmpty()) {
                btnDskizze.setDisable(true);
                btnDskizze.setText("nicht vorliegend");
            } else btnDskizze.setText("Download " + skizzeName);
            db.disconnectDB();
        }
    }

    private void setStudentenListe() {
        students.clear();
        int i = 0;
        for (Student s : selected.getProjektgruppe()) {
            students.add(s);
            i++;
        }
        listTeilnehmer.setItems(FXCollections.observableArrayList());
        listTeilnehmer.setItems(students);
        if (i == 3)
            btnAddStudent.setDisable(true);
        if (i == 1)
        delStudent.setDisable(true);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void handlebtnAddAnsp() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/anspView.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Ansprechpartner");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.dialogStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            AnsprechpartneranzeigeController controller = loader.getController();
            controller.setMainApp(this.mainApp);
            controller.setDialogStage(dialogStage);
            controller.setSettinAnsp(true);
            dialogStage.showAndWait();
            if (controller.isOkClicked()) {
                selected.setAnsprechpartner(controller.getNewAP());
                setSelected(this.selected);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    @FXML
    private void handleOK() {
        isOKclicked=true;
        dialogStage.close();
    }

    @FXML
    private void handleAddStudent() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/addStudent.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Student hinzufügen");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.dialogStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            ProjektStudentAddController controller = loader.getController();
            controller.setMainApp(this.mainApp);
            controller.setActiveStudents(students);
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();
            if (controller.isOKclicked())
                try {
                    selected.addStudent(controller.getSelected());
                    setStudentenListe();
                } catch (Exception e) {
                    new InfoWindows("Fehler", "Student nicht hinzugefügt", e.getMessage());
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteStudent() {
        selected.removeStudent(selectedStudent);
        setStudentenListe();
    }

    public boolean isOKclicked() {
        return isOKclicked;
    }

    private void showStudent(Student selectedStudent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/contacts.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Kontaktdetails");
            dialogStage.initOwner(this.dialogStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            ContactController controller = loader.getController();
            controller.setMainApp(this.mainApp);
            controller.setObject(selectedStudent);
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setNewProject(){
        DBcontroller db = new DBcontroller();
        db.connectDB();
        if(oldProject != null)
        db.updateProject(this.selected,oldProject);
        db.disconnectDB();
    }
}
