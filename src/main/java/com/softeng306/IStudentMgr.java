package com.softeng306;

public interface IStudentMgr {

    /**
     * Adds a student and put the student into file
     */
    void addStudent();

    /**
     * returns the ID number used to generate random ID's
     * @return the generated student id
     */
    String generateStudentID();

    /**
     * Sets the idNumber variable of this student class.
     * @param idNumber static variable idNumber of this class.
     */
    void setIdNumber(int idNumber);
}
