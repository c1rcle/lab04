package UI.Controllers;

import Core.Holders.DataHolder;
import Core.Grades.Grade;
import Core.Person.Student;
import UI.Cells.Factories.GenericFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class DataWindowController implements Initializable
{
    @FXML
    private Button dataGenerateButton;

    @FXML
    private ListView<Student> dataStudentList;

    @FXML
    private BarChart<String, Float> dataBarChart;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        dataBarChart.getYAxis().setLabel("Średnia");
        dataBarChart.setAnimated(false);

        dataStudentList.setItems(DataHolder.getStudentList());
        dataStudentList.setCellFactory(new GenericFactory<>());
        dataStudentList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        dataStudentList.getSelectionModel().selectedItemProperty().addListener((x)
                -> dataGenerateButton.setDisable(false));
    }

    public void generatePressed()
    {
        dataBarChart.getData().clear();
        XYChart.Series<String, Float> studentSeries = new XYChart.Series<>();
        studentSeries.getData().add(new XYChart.Data<>("Uczeń", calculateArithmeticMean()));
        studentSeries.getData().add(new XYChart.Data<>("Klasa", calculateClassArithmeticMean()));
        dataBarChart.getData().add(studentSeries);
        for (XYChart.Series<String, Float> series : dataBarChart.getData())
        {
            for (XYChart.Data<String, Float> data : series.getData())
            {
                Tooltip tooltip = new Tooltip();
                tooltip.setText(data.getYValue().toString());
                Tooltip.install(data.getNode(), tooltip);
            }
        }
    }

    private float calculateArithmeticMean()
    {
        Student selectedStudent = dataStudentList.getSelectionModel().getSelectedItem();
        int summation = 0;
        for (Grade grade : selectedStudent.getGrades())
        {
            summation += Integer.parseInt(grade.getGrade());
        }
        return selectedStudent.getGrades().size() > 0 ? (float) summation / selectedStudent.getGrades().size() : 0;
    }

    private float calculateClassArithmeticMean()
    {
        float summation = 0;
        for (Student student : dataStudentList.getItems())
        {
            int studentSummation = 0;
            for (Grade grade : student.getGrades())
            {
                studentSummation += Integer.parseInt(grade.getGrade());
            }
            summation += student.getGrades().size() > 0 ? (float) studentSummation / student.getGrades().size() : 0;
        }
        return summation / dataStudentList.getItems().size();
    }
}
