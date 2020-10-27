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

    String generateStudentID();

    // TODO sort this out
//    void setIdNumber(int idNumber);
}
