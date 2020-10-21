package com.softeng306.Interfaces.Managers;

import com.softeng306.Entity.Group;

import java.util.ArrayList;
import java.util.List;

public interface IHelpInfoMgr {

    /**
     * Displays all the professors in the inputted department.
     *
     * @param department The inputted department.
     * @param printOut Represents whether print out the professor information or not
     * @return A list of all the names of professors in the inputted department or else null.
     */
    public List<String> printProfInDepartment(String department, boolean printOut);

    /**
     * Displays a list of IDs of all the students.
     */
    public void printAllStudents();

    /**
     * Displays a list of IDs of all the courses.
     */
    public void printAllCourses();

    /**
     * Displays a list of all the departments.
     */
    public void printAllDepartment();

    /**
     * Displays a list of all the genders.
     */
    public void printAllGender();

    /**
     * Displays a list of all the course types.
     */
    public void printAllCourseType();

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

    /**
     * Displays a list of all the courses in the inputted department.
     *
     * @param department The inputted department.
     * @return a list of all the department values.
     */
    public List<String> printCourseInDepartment(String department);

    /**
     * Checks whether the inputted department is valid.
     *
     * @param groupType The type of this group.
     * @param groups    An array list of a certain type of groups in a course.
     * @return the name of the group chosen by the user.
     */
    public String printGroupWithVacancyInfo(String groupType, ArrayList<Group> groups);
}
