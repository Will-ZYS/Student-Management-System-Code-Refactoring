package com.softeng306.Interfaces.Entity;

import java.util.Map;

public interface IMark {

    /**
     * Gets the student of this student mark record.
     * @return the student of this student mark record.
     */
    IStudent getStudent();

    /**
     * Gets the course of this student mark record.
     * @return the course of this student mark record.
     */
    ICourse getCourse();

    /**
     * Gets the course work marks of this student mark record.
     * @return a map contains the course work marks of this student mark record.
     */
    Map<ICourseworkComponent, Double> getCourseWorkMarks();
    /**
     * Gets the total mark of this student mark record.
     * @return the total mark of this student mark record.
     */
    double getTotalMark();

    /**
     * Sets the main course work marks of this student mark record.
     * @param courseWorkName The name of this main course work.
     * @param result The mark obtained in this main course work.
     */
    void setMainCourseWorkMarks(String courseWorkName, double result);


    /**
     * Sets the sub course work marks of this student mark record.
     * @param courseWorkName The name of this sub course work.
     * @param result The mark obtained in this sub course work.
     */
    void setSubCourseWorkMarks(String courseWorkName, double result);
}
