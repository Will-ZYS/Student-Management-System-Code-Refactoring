package com.softeng306;

import java.util.List;

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
     * @return a list of all the course registration records.
     */
     List<ICourseRegistration> loadCourseRegistration();
}
