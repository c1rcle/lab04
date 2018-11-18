package Core.Person;

import Core.Absence.Absence;
import Core.Grades.Grade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.Objects;

public class Student
{
    private String name;

    private String surname;

    private ObservableList<Grade> grades;

    private ObservableList<Absence> absences;

    public Student(String name, String surname)
    {
        this.name = name;
        this.surname = surname;
        this.grades = FXCollections.observableArrayList();
        this.absences = FXCollections.observableArrayList();
    }

    public Student(String name, String surname, ObservableList<Grade> grades, ObservableList<Absence> absences)
    {
        this.name = name;
        this.surname = surname;
        this.grades = grades;
        this.absences = absences;
    }

    public boolean absenceExists(LocalDate date)
    {
        return absences.stream().anyMatch(x -> x.getDate().equals(date));
    }

    public String getName()
    {
        return name;
    }

    public String getSurname()
    {
        return surname;
    }

    @Override
    public String toString()
    {
        return name + " " + surname;
    }

    public ObservableList<Grade> getGrades()
    {
        return grades;
    }

    public ObservableList<Absence> getAbsences()
    {
        return absences;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(name, student.name) &&
                Objects.equals(surname, student.surname) &&
                Objects.equals(grades, student.grades) &&
                Objects.equals(absences, student.absences);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, surname, grades, absences);
    }
}
