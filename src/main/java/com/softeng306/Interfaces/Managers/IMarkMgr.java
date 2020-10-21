package com.softeng306.Interfaces.Managers;

import com.softeng306.Entity.Course;
import com.softeng306.Entity.Mark;
import com.softeng306.Entity.Student;

import java.util.ArrayList;

public interface IMarkMgr {

    /**
     * Initializes marks for a student when he/she just registered a course.
     * @param student the student this mark record belongs to.
     * @param course the course this mark record about.
     * @return the new added mark.
     */
    Mark initializeMark(Student student, Course course);

    /**
     * Sets the coursework mark for the mark record.
     * @param isExam whether this coursework component refers to "Exam"
     */
    void setCourseWorkMark(boolean isExam);

    /**
     * Computes the sum of marks for a particular component of a particular course
     * @param thisCourseMark the array list of mark records belong to a particular course
     * @param thisComponentName the component name interested.
     * @return the sum of component marks
     */
    double computeMark(ArrayList<Mark> thisCourseMark, String thisComponentName);

    /**
     * Computes the gpa gained for this course from the result of this course.
     * @param result result of this course
     * @return the grade (in A, B ... )
     */
    double gpaCalculator(double result);
}
