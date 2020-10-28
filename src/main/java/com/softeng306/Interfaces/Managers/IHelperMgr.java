package com.softeng306.Interfaces.Managers;

import java.util.List;

public interface IHelperMgr {

    /**
     * Checks whether the inputted department is valid.
     * @param department The inputted department.
     * @return boolean indicates whether the inputted department is valid.
     */
    boolean checkDepartmentValidation(String department);

    /**
     * Checks whether the inputted gender is valid.
     * @param gender The inputted gender.
     * @return boolean indicates whether the inputted gender is valid.
     */
    boolean checkGenderValidation(String gender);

    /**
     * Checks whether the inputted course type is valid.
     * @param courseType The inputted course type.
     * @return boolean indicates whether the inputted course type is valid.
     */
    boolean checkCourseTypeValidation(String courseType);

    /**
     * Checks whether the inputted person name is in the correct format.
     * This person can be professor or student.
     * @param personName The inputted person name.
     * @return boolean indicates whether the inputted person name is valid.
     */
    boolean checkValidPersonNameInput(String personName);

    /**
     * Prompts the user to input an existing department.
     * @return the inputted department.
     */
    String checkCourseDepartmentExists();
}
