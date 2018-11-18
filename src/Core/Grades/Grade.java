package Core.Grades;

import java.util.Objects;

public class Grade
{
    private String subject;

    private String grade;

    public Grade(String subject, String grade)
    {
        this.subject = subject;
        this.grade = grade;
    }

    public String getSubject()
    {
        return subject;
    }

    public String getGrade()
    {
        return grade;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grade grade1 = (Grade) o;
        return Objects.equals(subject, grade1.subject) &&
                Objects.equals(grade, grade1.grade);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(subject, grade);
    }
}
