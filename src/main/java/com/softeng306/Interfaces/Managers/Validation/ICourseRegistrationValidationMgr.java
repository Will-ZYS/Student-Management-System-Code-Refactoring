package com.softeng306.Interfaces.Managers.Validation;

import com.softeng306.Interfaces.Entity.ICourseRegistration;

public interface ICourseRegistrationValidationMgr {
    /**
     * Checks whether this course registration record exists.
     * @param studentID The inputted student ID.
     * @param courseID The inputted course ID.
     * @return the existing course registration record or else null.
     */
    ICourseRegistration checkCourseRegistrationExists(String studentID, String courseID);
}
