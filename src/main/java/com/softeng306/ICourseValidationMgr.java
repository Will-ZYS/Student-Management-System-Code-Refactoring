package com.softeng306;

import com.softeng306.ICourse;

public interface ICourseValidationMgr {
    /**
     * Prompts the user to input an existing course.
     * @return the inputted course.
     */
    ICourse checkCourseExists();

    /**
     * Checks whether this course ID is used by other courses.
     * @param courseID The inputted course ID.
     * @return the existing course or else null.
     */
    ICourse checkCourseExists(String courseID);

    /**
     * Checks whether the inputted course ID is in the correct format.
     * @param courseID The inputted course ID.
     * @return boolean indicates whether the inputted course ID is valid.
     */
    boolean checkValidCourseIDInput(String courseID);
}
