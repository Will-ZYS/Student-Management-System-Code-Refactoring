package com.softeng306.Interfaces.Database;

import com.softeng306.Interfaces.Entity.ICourseRegistration;

import java.util.ArrayList;

public interface ICourseRegistrationFileMgr {

    /**
     * Writes a new course registration record into the file.
     *
     * @param courseRegistration courseRegistration to be added into file
     */
    void writeCourseRegistrationIntoFile(ICourseRegistration courseRegistration);

    /**
     * Load all the course registration records from file into the system.
     *
     * @return an array list of all the course registration records.
     */
     ArrayList<ICourseRegistration> loadCourseRegistration();
}
