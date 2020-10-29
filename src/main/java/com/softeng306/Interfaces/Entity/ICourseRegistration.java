package com.softeng306.Interfaces.Entity;

public interface ICourseRegistration {

    /**
     * get the student which has been registered
     * @return a Student object
     */
    IStudent getStudent();

    /**
     * get the course the student has been enrolled in
     * @return a Course object
     */
    ICourse getCourse();

    /**
     * get the lecture group the student has enrolled int
     * @return String name of the lecture group
     */
    String getLectureGroup();

    /**
     * get the tutorial group the student has enrolled int
     * @return String name of the tutorial group
     */
    String getTutorialGroup();

    /**
     * get the lab group the student has enrolled int
     * @return String name of the lab group
     */
    String getLabGroup();
}
