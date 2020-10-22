package com.softeng306.Interfaces.Entity;

import com.softeng306.Entity.*;

import java.util.HashMap;

public interface IMark {

    /**
     * Gets the student of this student mark record.
     * @return the student of this student mark record.
     */
    public IStudent getStudent();

    /**
     * Gets the course of this student mark record.
     * @return the course of this student mark record.
     */
    public ICourse getCourse();

    /**
     * Gets the course work marks of this student mark record.
     * @return a hashmap contains the course work marks of this student mark record.
     */
    public HashMap<CourseworkComponent, Double> getCourseWorkMarks();
    /**
     * Gets the total mark of this student mark record.
     * @return the total mark of this student mark record.
     */
    public double getTotalMark();

    /**
     * Sets the main course work marks of this student mark record.
     * @param courseWorkName The name of this main course work.
     * @param result The mark obtained in this main course work.
     */
    public void setMainCourseWorkMarks(String courseWorkName, double result);


    /**
     * Sets the sub course work marks of this student mark record.
     * @param courseWorkName The name of this sub course work.
     * @param result The mark obtained in this sub course work.
     */
    public void setSubCourseWorkMarks(String courseWorkName, double result);
}
