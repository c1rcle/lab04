package Core.Holders;

import Core.Database.DatabaseLoad;
import Core.Database.DatabaseSave;
import Core.Person.Student;
import javafx.collections.ObservableList;

public class DataHolder
{
    private static ObservableList<Student> studentList;

    private static ObservableList<String> subjectList;

    public static void initialize()
    {
        DatabaseLoad load = new DatabaseLoad();
        studentList = load.loadStudentsData();
        subjectList = load.loadSubjectsData();
        load.dispose();
    }

    public static void save()
    {
        DatabaseSave save = new DatabaseSave();
        save.saveStudentsData(studentList);
        save.saveSubjectsData(subjectList);
        save.dispose();
    }

    public static ObservableList<Student> getStudentList()
    {
        return studentList;
    }

    public static ObservableList<String> getSubjectList()
    {
        return subjectList;
    }
}
