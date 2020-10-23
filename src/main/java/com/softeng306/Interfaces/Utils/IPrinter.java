package com.softeng306.Interfaces.Utils;

import com.softeng306.Entity.Group;

import java.util.ArrayList;
import java.util.List;

public interface IPrinter {

    void print(String content);

    /**
     * Displays the welcome message.
     */
    void printWelcome();

    /**
     * Displays the exiting message.
     */
    void printExitMessage();

    /**
     * Displays all the options of the system.
     */
    void printOptions();

    /**
     * Displays all the professors in the inputted department.
     *
     * @param department The inputted department.
     * @param printOut Represents whether print out the professor information or not
     * @return A list of all the names of professors in the inputted department or else null.
     */
    List<String> printProfInDepartment(String department, boolean printOut);

    /**
     * Displays a list of IDs of all the students.
     */
    void printAllStudents();

    /**
     * Displays a list of IDs of all the courses.
     */
    void printAllCourses();

    /**
     * Displays a list of all the departments.
     */
    void printAllDepartment();

    /**
     * Displays a list of all the genders.
     */
    void printAllGender();

    /**
     * Displays a list of all the course types.
     */
    void printAllCourseType();

    /**
     * Displays a list of all the courses in the inputted department.
     *
     * @param department The inputted department.
     * @return a list of all the department values.
     */
    List<String> printCourseInDepartment(String department);

    /**
     * Checks whether the inputted department is valid.
     *
     * @param groupType The type of this group.
     * @param groups    An array list of a certain type of groups in a course.
     * @return the name of the group chosen by the user.
     */
    String printGroupWithVacancyInfo(String groupType, ArrayList<Group> groups);

    /**
     * Prints the list of courses
     */
    void printCourses();

    /**
     * Prints the students in a course according to their lecture group, tutorial group or lab group.
     */
    void printStudents();

    /**
     * Prints the course statics including enrollment rate, average result for every assessment component and the average overall performance of this course.
     */
    void printCourseStatistics();

    /**
     * Prints transcript (Results of course taken) for a particular student
     */
    void  printStudentTranscript();
}
