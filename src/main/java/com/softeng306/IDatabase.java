package com.softeng306;

import java.util.List;

/**
 * Interface for using the Database singleton.
 * Getters used to retrieve data from the database
 * Setters currently used for testing purposes
 */
public interface IDatabase {

    /**
     * returns a list of Students in the system
     * @return list of students
     */
    List<IStudent> getStudents();

    /**
     * sets student collection
     * @param students
     */
    void setStudents(List<IStudent> students);

    /**
     * returns a list of courses in the system
     * @return list of courses
     */
    List<ICourse> getCourses() ;

    /**
     * sets course collection
     * @param courses
     */
    void setCourses(List<ICourse> courses);

    /**
     * returns a list of course registrations in the system
     * @return list of course registrations
     */
    List<ICourseRegistration> getCourseRegistrations();

    /**
     * sets courseRegistrations collection
     * @param courseRegistrations
     */
    void setCourseRegistrations(List<ICourseRegistration> courseRegistrations);

    /**
     * returns a list of marks in the system
     * @return list of marks
     */
    List<IMark> getMarks();

    /**
     * sets marks collection
     * @param marks
     */
    void setMarks(List<IMark> marks) ;

    /**
     * returns a list of professors in the system
     * @return list of marks
     */
    List<IProfessor> getProfessors() ;

    /**
     * sets professor collection
     * @param professors
     */
    void setProfessors(List<IProfessor> professors);
}
