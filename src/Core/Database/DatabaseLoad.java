package Core.Database;

import Core.Absence.Absence;
import Core.Grades.Grade;
import Core.Person.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseLoad
{
    private Connection connection;

    public DatabaseLoad()
    {
        connection = DatabaseConnection.connect();
    }

    public void dispose()
    {
        try
        {
            connection.close();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public ObservableList<Student> loadStudentsData()
    {
        ObservableList<Student> studentList = FXCollections.observableArrayList();
        String query = "SELECT * FROM Students";

        try (Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery(query))
        {
            while (results.next())
            {
                List<Grade> grades = loadGradesData(results.getInt("id"));
                List<Absence> absences = loadAbsencesData(results.getInt("id"));
                String name = results.getString("name");
                String surname = results.getString("surname");
                Student currentStudent = new Student(name, surname,
                        FXCollections.observableArrayList(grades), FXCollections.observableArrayList(absences));
                studentList.add(currentStudent);
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return studentList;
    }

    private List<Grade> loadGradesData(int index)
    {
        List<Grade> gradeList = new ArrayList<>();
        String query = "SELECT * FROM Grades WHERE studentId = ?";

        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setInt(1, index);
            ResultSet results = statement.executeQuery();

            while(results.next())
            {
                String subject = results.getString("subject");
                String grade = results.getString("grade");
                Grade currentGrade = new Grade(subject, grade);
                gradeList.add(currentGrade);
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return gradeList;
    }

    private List<Absence> loadAbsencesData(int index)
    {
        List<Absence> absenceList = new ArrayList<>();
        String query = "SELECT * FROM Absences WHERE studentId = ?";

        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setInt(1, index);
            ResultSet results = statement.executeQuery();

            while(results.next())
            {
                LocalDate date = results.getDate("date").toLocalDate();
                String absenceType = results.getString("absenceType");
                Absence currentAbsence = new Absence(date, absenceType);
                absenceList.add(currentAbsence);
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return absenceList;
    }

    public ObservableList<String> loadSubjectsData()
    {
        ObservableList<String> subjectsList = FXCollections.observableArrayList();
        String query = "SELECT * FROM Subjects";

        try (Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery(query))
        {
            while (results.next())
            {
                String currentSubject = results.getString("subject");
                subjectsList.add(currentSubject);
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return subjectsList;
    }
}
