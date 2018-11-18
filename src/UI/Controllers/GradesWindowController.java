package UI.Controllers;

import Core.Holders.DataHolder;
import Core.Grades.Grade;
import Core.Person.Student;
import UI.Cells.Factories.GenericFactory;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;

public class GradesWindowController implements Initializable
{
    @FXML
    private TextField gradesSubjectTextbox;

    @FXML
    private ComboBox<String> gradesSubjectComboBox;

    @FXML
    private ComboBox<String> gradesComboBox;

    @FXML
    private Button gradesRemoveSubjectButton;

    @FXML
    private Button gradesAddButton;

    @FXML
    private Button gradesRemoveButton;

    @FXML
    private ListView<Student> gradesStudentList;

    @FXML
    private ListView<Grade> gradesGradeList;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        gradesSubjectComboBox.setItems(DataHolder.getSubjectList());
        gradesSubjectComboBox.getSelectionModel().selectFirst();

        if (gradesSubjectComboBox.getItems().size() == 0) gradesRemoveSubjectButton.setDisable(true);
        gradesSubjectComboBox.getSelectionModel().selectedItemProperty().addListener((x)
                -> gradesRemoveSubjectButton.setDisable(false));

        gradesSubjectComboBox.getItems().addListener((ListChangeListener<String>) x
                -> { if (gradesSubjectComboBox.getItems().size() == 0) gradesRemoveSubjectButton.setDisable(true); });

        gradesStudentList.setItems(DataHolder.getStudentList());
        gradesStudentList.setCellFactory(new GenericFactory<>());
        gradesStudentList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        gradesStudentList.getSelectionModel().selectedItemProperty().addListener(studentChangeListener);

        ObservableList<String> options = FXCollections
                .observableArrayList("1", "2", "3", "4", "5", "6");
        gradesComboBox.setItems(options);
        gradesComboBox.setValue("5");
    }

    private ChangeListener<Student> studentChangeListener = (observable, oldValue, newValue) ->
    {
        Student student = gradesStudentList.getSelectionModel().getSelectedItem();
        String subject = gradesSubjectComboBox.getSelectionModel().getSelectedItem();
        if (subject != null && student != null)
        {
            gradesGradeList.setItems(student.getGrades());
            gradesGradeList.setCellFactory(new GenericFactory<>());
            gradesAddButton.setDisable(false);

            gradesGradeList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            gradesGradeList.getSelectionModel().selectedItemProperty().addListener((x)
                    -> gradesRemoveButton.setDisable(false));

            gradesGradeList.getItems().addListener((ListChangeListener<Grade>) x
                    -> { if (gradesGradeList.getItems().size() == 0) gradesRemoveButton.setDisable(true); });
        }
        else Platform.runLater(() -> gradesStudentList.getSelectionModel().clearSelection());

        if (gradesStudentList.getSelectionModel().getSelectedItem() == null) gradesAddButton.setDisable(true);
    };

    public void addSubjectPressed()
    {
        String subject = gradesSubjectTextbox.getText();
        if (!subject.isEmpty() && !gradesSubjectComboBox.getItems().contains(subject))
        {
            DataHolder.getSubjectList().add(subject);
            Collections.sort(DataHolder.getSubjectList());
            gradesSubjectTextbox.setText("");
            gradesSubjectComboBox.getSelectionModel().select(subject);
        }
        else showAlertWarning();
    }

    public void removeSubjectPressed()
    {
        String selectedSubject = gradesSubjectComboBox.getSelectionModel().getSelectedItem();
        gradesSubjectComboBox.getItems().remove(selectedSubject);
        gradesSubjectComboBox.getSelectionModel().selectLast();
        if (gradesSubjectComboBox.getItems().size() == 0) gradesStudentList.getSelectionModel().clearSelection();
    }

    public void addGradePressed()
    {
        String selectedSubject = gradesSubjectComboBox.getSelectionModel().getSelectedItem();
        String selectedGrade = gradesComboBox.getSelectionModel().getSelectedItem();
        Student selectedStudent = gradesStudentList.getSelectionModel().getSelectedItem();
        selectedStudent.getGrades().add(new Grade(selectedSubject, selectedGrade));
        selectedStudent.getGrades().sort(Comparator.comparing(Grade::getSubject));
    }

    public void removeGradePressed()
    {
        Grade grade = gradesGradeList.getSelectionModel().getSelectedItem();
        if (grade != null)
        {
            gradesStudentList.getSelectionModel().getSelectedItem().getGrades().remove(grade);
        }
    }

    public void textboxKeyTyped(KeyEvent keyEvent)
    {
        if (((TextField)keyEvent.getSource()).getText().length() > 49) keyEvent.consume();
    }

    private void showAlertWarning()
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Błąd");
        alert.setHeaderText("Niedozwolona operacja");
        alert.setContentText("Wymagane pole nie zostało wypełnione lub taki przedmiot już został wpisany!");
        alert.initStyle(StageStyle.UTILITY);
        alert.showAndWait();
    }
}
