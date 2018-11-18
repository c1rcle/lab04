package Core.Absence;

import java.time.LocalDate;
import java.util.Objects;

public class Absence
{
    private LocalDate date;

    private String absenceType;

    public Absence(LocalDate date, String absenceType)
    {
        this.date = date;
        this.absenceType = absenceType;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public String getAbsenceType()
    {
        return absenceType;
    }

    @Override
    public String toString()
    {
        return date.toString() + " - " + absenceType;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Absence absence = (Absence) o;
        return Objects.equals(date, absence.date) &&
                Objects.equals(absenceType, absence.absenceType);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(date, absenceType);
    }
}
