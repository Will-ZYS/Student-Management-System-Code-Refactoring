package com.softeng306.Interfaces.Managers;

import com.softeng306.Entity.Course;

public interface ICourseMgr {

    /**
     * Creates a new course and stores it in the file.
     */
    void addCourse();

    /**
     * Checks whether a course (with all of its groups) have available slots and displays the result.
     */
    public void checkAvailableSlots();

    /**
     * Sets the course work component weightage of a course.
     *
     * @param currentCourse The course which course work component is to be set.
     */
    public void enterCourseWorkComponentWeightage(Course currentCourse);
}
