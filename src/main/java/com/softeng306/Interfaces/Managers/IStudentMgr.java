package com.softeng306.Interfaces.Managers;

import com.softeng306.Interfaces.Entity.IStudent;

public interface IStudentMgr {

    /**
     * Adds a student and put the student into file
     */
    void addStudent();

    /**
     * returns the ID number used to generate random ID's
     * @return
     */
    String generateStudentID();

    /**
     * Sets the idNumber variable of this student class.
     * @param idNumber static variable idNumber of this class.
     */
    void setIdNumber(int idNumber);
}
