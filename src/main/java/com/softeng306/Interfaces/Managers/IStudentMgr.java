package com.softeng306.Interfaces.Managers;

import com.softeng306.Interfaces.Entity.IStudent;

public interface IStudentMgr {

    /**
     * Adds a student and put the student into file
     */
    void addStudent();

    /**
     * Checks whether the inputted student ID is in the correct format.
     * @param studentID The inputted student ID.
     * @return boolean indicates whether the inputted student ID is valid.
     */
    boolean checkValidStudentIDInput(String studentID);

    /**
     * Checks whether this student ID is used by other students.
     * @param studentID This student's ID.
     * @return the existing student or else null.
     */
    IStudent checkStudentExists(String studentID);

    /**
     * Prompts the user to input an existing student.
     * @return the inputted student.
     */
    IStudent checkStudentExists();

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
