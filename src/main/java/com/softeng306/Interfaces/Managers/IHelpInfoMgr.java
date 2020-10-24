package com.softeng306.Interfaces.Managers;

import java.util.ArrayList;

public interface IHelpInfoMgr {

    /**
     * Gets all the departments as an array list.
     *
     * @return an array list of all the departments.
     */
    ArrayList<String> getAllDepartment();

    /**
     * Gets all the genders as an array list.
     *
     * @return an array list of all the genders.
     */
    ArrayList<String> getAllGender();

    /**
     * Gets all the course types as an array list.
     *
     * @return an array list of all the course types.
     */
    ArrayList<String> getAllCourseType();
}
