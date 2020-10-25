package com.softeng306.Interfaces.Database;

import com.softeng306.Database.CourseFileMgr;
import com.softeng306.Interfaces.Entity.ICourse;

import java.util.ArrayList;
import java.util.List;

public interface ICourseFileMgr {

    /**
     * Write a new course information into the file.
     *
     * @param course a course to be added into file
     */
    void writeCourseIntoFile(ICourse course);

    /**
     * Load all the courses' information from file into the system.
     *
     * @return an array list of all the courses.
     */
     ArrayList<ICourse> loadCourses();

    /**
     * Backs up all the changes of courses made into the file.
     *
     * @param courses courses to be backed up
     */
     void backUpCourse(List<ICourse> courses);
}
