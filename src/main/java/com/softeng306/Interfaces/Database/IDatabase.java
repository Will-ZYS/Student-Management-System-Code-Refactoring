package com.softeng306.Interfaces.Database;

import com.softeng306.Entity.*;

import java.util.ArrayList;
import java.util.List;

public interface IDatabase {
    // TODO I DONT THINK WE EVER USE THE SET METHODS NEED TO CHECK

    /**
     * returns a list of Students in the system
     * @return list of students
     */
    List<Student> getStudents();

    /**
     * sets student collection
     * @param students
     */
    void setStudents(List<Student> students);

    /**
     * returns a list of courses in the system
     * @return list of courses
     */
    List<Course> getCourses() ;

    /**
     * sets course collection
     * @param courses
     */
    void setCourses(List<Course> courses);

    /**
     * returns a list of course registrations in the system
     * @return list of course registrations
     */
    List<CourseRegistration> getCourseRegistrations();

    /**
     * sets courseRegistrations collection
     * @param courseRegistrations
     */
    void setCourseRegistrations(List<CourseRegistration> courseRegistrations);

    /**
     * returns a list of marks in the system
     * @return list of marks
     */
    List<Mark> getMarks();

    /**
     * sets marks collection
     * @param marks
     */
    void setMarks(List<Mark> marks) ;

    /**
     * returns a list of professors in the system
     * @return list of marks
     */
    List<Professor> getProfessors() ;

    /**
     * sets professor collection
     * @param professors
     */
    void setProfessors(List<Professor> professors);
}
