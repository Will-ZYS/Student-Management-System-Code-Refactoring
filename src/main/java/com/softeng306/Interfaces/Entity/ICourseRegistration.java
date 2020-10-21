package com.softeng306.Interfaces.Entity;
import com.softeng306.Entity.Student;


public interface ICourseRegistration {

    public Student getStudent();

    public ICourse getCourse();

    public String getLectureGroup();

    public String getTutorialGroup();

    public String getLabGroup();
}
