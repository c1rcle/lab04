package Core.Database;

import Core.Absence.Absence;
import Core.Grades.Grade;
import Core.Person.Student;

import java.sql.*;
import java.util.List;

public class DatabaseSave
{
    private Connection connection;

    public DatabaseSave()
    {
        connection = DatabaseConnection.connect();
    }

    public void saveStudentsData(List<Student> studentList)
    {
        deleteFromTable("Students");
        deleteFromTable("Grades");
        deleteFromTable("Absences");
        String query = "INSERT INTO Students(id, name, surname) VALUES(?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            for (int i = 0; i < studentList.size(); i++)
            {
                statement.setInt(1, i);
                statement.setString(2, studentList.get(i).getName());
                statement.setString(3, studentList.get(i).getSurname());
                statement.executeUpdate();

                saveGradeData(i, studentList.get(i).getGrades());
                saveAbsenceData(i, studentList.get(i).getAbsences());
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void saveGradeData(int index, List<Grade> gradeList)
    {
        String query = "INSERT INTO Grades(studentId, subject, grade) VALUES(?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            for (Grade grade : gradeList)
            {
                statement.setInt(1, index);
                statement.setString(2, grade.getSubject());
                statement.setString(3, grade.getGrade());
                statement.executeUpdate();
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void saveAbsenceData(int index, List<Absence> absenceList)
    {
        String query = "INSERT INTO Absences(studentId, date, absenceType) VALUES(?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            for (Absence absence : absenceList)
            {
                statement.setInt(1, index);
                statement.setDate(2, Date.valueOf(absence.getDate()));
                statement.setString(3, absence.getAbsenceType());
                statement.executeUpdate();
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void saveSubjectsData(List<String> subjectList)
    {
        deleteFromTable("Subjects");
        String query = "INSERT INTO Subjects(subject) VALUES(?)";

        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            for (String subject : subjectList)
            {
                statement.setString(1, subject);
                statement.executeUpdate();
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void deleteFromTable(String tableName)
    {
        String query = "DELETE FROM " + tableName;
        try (Statement statement = connection.createStatement())
        {
            statement.executeUpdate(query);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
