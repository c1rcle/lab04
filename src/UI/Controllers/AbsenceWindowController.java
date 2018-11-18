package UI.Controllers;

import Core.Absence.Absence;
import Core.Holders.DataHolder;
import Core.Person.Student;
import UI.Cells.Factories.GenericFactory;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.StageStyle;

import java.net.URL;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.ResourceBundle;

public class AbsenceWindowController implements Initializable
{
    @FXML
    private DatePicker absenceDatePicker;

    @FXML
    private ComboBox<String> absenceComboBox;

    @FXML
    private Button absenceAddButton;

    @FXML
    private Button absenceRemoveButton;

    @FXML
    private ListView<Student> absenceStudentList;

    @FXML
    private ListView<Absence> absenceAbsenceList;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        ObservableList<String> options = FXCollections
                .observableArrayList("nieobecny", "usprawiedliwiony");
        absenceComboBox.setItems(options);
        absenceComboBox.setValue("nieobecny");

        absenceDatePicker.setValue(LocalDate.now());

        absenceStudentList.setItems(DataHolder.getStudentList());
        absenceStudentList.setCellFactory(new GenericFactory<>());
        absenceStudentList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        absenceStudentList.getSelectionModel().selectedItemProperty().addListener(studentChangeListener);
    }

    private ChangeListener<Student> studentChangeListener = (observable, oldValue, newValue) ->
    {
        Student student = absenceStudentList.getSelectionModel().getSelectedItem();
        absenceAbsenceList.setItems(student.getAbsences());
        absenceAbsenceList.setCellFactory(new GenericFactory<>());
        absenceAddButton.setDisable(false);

        absenceAbsenceList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        absenceAbsenceList.getSelectionModel().selectedItemProperty().addListener((x)
                -> absenceRemoveButton.setDisable(false));

        absenceAbsenceList.getItems().addListener((ListChangeListener<Absence>) x
                -> { if (absenceAbsenceList.getItems().size() == 0) absenceRemoveButton.setDisable(true); });

        if (absenceStudentList.getSelectionModel().getSelectedItem() == null) absenceAddButton.setDisable(true);
    };

    public void addButtonPressed()
    {
        LocalDate selectedDate = absenceDatePicker.getValue();
        String selectedAbsenceType = absenceComboBox.getSelectionModel().getSelectedItem();
        Student selectedStudent = absenceStudentList.getSelectionModel().getSelectedItem();
        if (!selectedStudent.absenceExists(selectedDate))
        {
            selectedStudent.getAbsences().add(new Absence(selectedDate, selectedAbsenceType));
            selectedStudent.getAbsences().sort(Comparator.comparing(Absence::getDate));
        }
        else showAlertWarning();
    }

    public void removeButtonPressed()
    {
        Absence absence = absenceAbsenceList.getSelectionModel().getSelectedItem();
        if (absence != null)
        {
            absenceStudentList.getSelectionModel().getSelectedItem().getAbsences().remove(absence);
        }
    }

    private void showAlertWarning()
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Błąd");
        alert.setHeaderText("Niedozwolona operacja");
        alert.setContentText("Nieobecność tego dnia została już wystawiona!");
        alert.initStyle(StageStyle.UTILITY);
        alert.showAndWait();
    }
}
