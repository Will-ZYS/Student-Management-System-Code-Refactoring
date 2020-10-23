package com.softeng306.Interfaces.Managers;

import com.softeng306.Entity.Group;

import java.util.ArrayList;
import java.util.List;

public interface IHelpInfoMgr {

    /**
     * Gets all the departments as an array list.
     *
     * @return an array list of all the departments.
     */
    public ArrayList<String> getAllDepartment();

    /**
     * Gets all the genders as an array list.
     *
     * @return an array list of all the genders.
     */
    public ArrayList<String> getAllGender();

    /**
     * Gets all the course types as an array list.
     *
     * @return an array list of all the course types.
     */
    public ArrayList<String> getAllCourseType();
}
