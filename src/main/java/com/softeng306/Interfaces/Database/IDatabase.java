package com.softeng306.Interfaces.Database;

import com.softeng306.Entity.*;

import java.util.ArrayList;

public interface IDatabase {

    ArrayList<Student> getStudents();

    void setStudents(ArrayList<Student> students);

    ArrayList<Course> getCourses() ;

    void setCourses(ArrayList<Course> courses);

    ArrayList<CourseRegistration> getCourseRegistrations();

    void setCourseRegistrations(ArrayList<CourseRegistration> courseRegistrations);

    ArrayList<Mark> getMarks();

    void setMarks(ArrayList<Mark> marks) ;

    ArrayList<Professor> getProfessors() ;

    void setProfessors(ArrayList<Professor> professors);
}
