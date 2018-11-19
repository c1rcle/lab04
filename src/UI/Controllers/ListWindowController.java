package UI.Controllers;

import Core.Holders.DataHolder;
import Core.Person.Student;
import UI.Alerts.CustomAlert;
import UI.Cells.Factories.GenericFactory;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

public class ListWindowController implements Initializable
{
    @FXML
    private TextField listNameTextbox;

    @FXML
    private TextField listSurnameTextbox;

    @FXML
    private Button listRemoveButton;

    @FXML
    private ListView<Student> listStudentList;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        listStudentList.setItems(DataHolder.getStudentList());
        listStudentList.setCellFactory(new GenericFactory<>());

        listStudentList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listStudentList.getSelectionModel().selectedItemProperty().addListener((x)
                -> listRemoveButton.setDisable(false));

        listStudentList.getItems().addListener((ListChangeListener<Student>) x
                -> { if (listStudentList.getItems().size() == 0) listRemoveButton.setDisable(true); });
    }

    public void addButtonPressed()
    {
        String name = listNameTextbox.getText();
        String surname = listSurnameTextbox.getText();
        if (!name.isEmpty() && !surname.isEmpty())
        {
            DataHolder.getStudentList().add(new Student(name, surname));
            DataHolder.getStudentList().sort(Comparator.comparing(Student::getSurname));
            listNameTextbox.setText("");
            listSurnameTextbox.setText("");
        }
        else showAlertWarning();
    }

    public void removeButtonPressed()
    {
        Student selectedStudent = listStudentList.getSelectionModel().getSelectedItem();
        if (selectedStudent != null)
        {
            listStudentList.getItems().remove(selectedStudent);
        }
    }

    public void textboxKeyTyped(KeyEvent keyEvent)
    {
        if (((TextField)keyEvent.getSource()).getText().length() > 49) keyEvent.consume();
    }

    private void showAlertWarning()
    {
        Alert alert = new CustomAlert(Alert.AlertType.WARNING).getAlert();
        alert.setTitle("Błąd");
        alert.setHeaderText("Niedozwolona operacja");
        alert.setContentText("Sprawdź czy wymagane pole zostały wypełnione!");
        alert.showAndWait();
    }
}
