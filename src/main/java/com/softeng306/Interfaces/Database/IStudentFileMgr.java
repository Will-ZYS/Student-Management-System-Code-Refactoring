package com.softeng306.Interfaces.Database;

import com.softeng306.Interfaces.Entity.IStudent;

import java.util.ArrayList;

public interface IStudentFileMgr {

    /**
     * Write a new student information into the file.
     *
     * @param student a student to be added into the file
     */
     void writeStudentsIntoFile(IStudent student);

    /**
     * Load all the students' information from file into the system.
     *
     * @return an array list of all the students.
     */
     ArrayList<IStudent> loadStudents();

    /**
     * get the instance of the StudentFileMgr class
     * @return the singleton instance
     */
    StudentFileMgr getInstance();
}
