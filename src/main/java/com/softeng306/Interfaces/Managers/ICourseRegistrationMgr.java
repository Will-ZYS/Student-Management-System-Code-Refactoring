package com.softeng306.Interfaces.Managers;

import com.softeng306.Interfaces.Entity.ICourseRegistration;

public interface ICourseRegistrationMgr {

    /**
     * Registers a course for a student
     */
    void registerCourse();

    /**
     * Checks whether this course registration record exists.
     * @param studentID The inputted student ID.
     * @param courseID The inputted course ID.
     * @return the existing course registration record or else null.
     */
    ICourseRegistration checkCourseRegistrationExists(String studentID, String courseID);
}
