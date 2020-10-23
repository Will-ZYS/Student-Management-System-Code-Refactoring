package com.softeng306.Interfaces.Managers;

import com.softeng306.Interfaces.Entity.ICourse;

public interface ICourseMgr {

    /**
     * Creates a new course and stores it in the file.
     */
    void addCourse();

    /**
     * Checks whether a course (with all of its groups) have available slots and displays the result.
     */
    void checkAvailableSlots();

    /**
     * Sets the course work component weightage of a course.
     *
     * @param currentCourse The course which course work component is to be set.
     */
    void enterCourseWorkComponentWeightage(ICourse currentCourse);
}
