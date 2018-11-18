package UI.Cells;

import Core.Absence.Absence;
import Core.Grades.Grade;
import Core.Person.Student;
import javafx.scene.control.ListCell;

public class GenericCell<T> extends ListCell<T>
{
    @Override
    protected void updateItem(T item, boolean empty)
    {
        super.updateItem(item, empty);

        int index = this.getIndex();
        String name = null;

        if (item != null)
        {
            if (item instanceof Student) name = (index + 1) + ". "
                    + ((Student)item).getName() + " " + ((Student)item).getSurname();
            else if (item instanceof Grade) name = (index + 1) + ". "
                    + ((Grade)item).getSubject() + " - " + ((Grade)item).getGrade();
            else if (item instanceof Absence) name = (index + 1) + ". "
                    + ((Absence)item).getDate().toString() + " - " + ((Absence)item).getAbsenceType();
        }

        this.setText(name);
        setGraphic(null);
    }
}
