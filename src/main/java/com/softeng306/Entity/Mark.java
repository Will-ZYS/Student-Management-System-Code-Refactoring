package com.softeng306.Entity;

import com.softeng306.Interfaces.Entity.ICourse;
import com.softeng306.Interfaces.Entity.ICourseworkComponent;
import com.softeng306.Interfaces.Entity.IMark;
import com.softeng306.Interfaces.Entity.IStudent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a student mark record associated with one student and a course.
 * Both students and courses can have multiple student mark record, but cannot be duplicate.

 */

public class Mark implements IMark {
    /**
     * The student of this student mark record.
     */
    private IStudent student;
    /**
     * The course of this student mark record.
     */
    private ICourse course;
    /**
     * The course work marks of this student mark record.
     */
    private Map<ICourseworkComponent, Double> courseWorkMarks;
    /**
     * The total mark of this student mark record.
     */
    private double totalMark;

    /**
     * Creates a new student mark record with the student of this student mark record, the course of this student mark record.
     *  the course work marks of this student mark record, the total mark of this student mark record.
     * @param student The student of this student mark record.
     * @param course The course of this student mark record.
     */
    public Mark(IStudent student, ICourse course) {
        Map<ICourseworkComponent, Double> courseWorkMarks = new HashMap<>();
        List<ICourseworkComponent> mainComponents = course.getMainComponents();
        for (ICourseworkComponent mainComponent : mainComponents) {
            courseWorkMarks.put(mainComponent, 0d);
            if (mainComponent.getSubComponents().size() > 0) {
                for (ICourseworkComponent subComponent : mainComponent.getSubComponents()) {
                    courseWorkMarks.put(subComponent, 0d);
                }
            }
        }
        this.student = student;
        this.course = course;
        this.courseWorkMarks = courseWorkMarks;
        this.totalMark = 0d;
    }

    /**
     * Creates a new student mark record with the student of this student mark record, the course of this student mark record.
     *  the course work marks of this student mark record, the total mark of this student mark record.
     * @param student The student of this student mark record.
     * @param course The course of this student mark record.
     * @param courseWorkMarks The course work marks of this student mark record.
     * @param totalMark The total mark of this student mark record.
     */
    public Mark(IStudent student, ICourse course, Map<ICourseworkComponent, Double> courseWorkMarks, double totalMark) {
        this.student = student;
        this.course = course;
        this.courseWorkMarks = courseWorkMarks;
        this.totalMark = totalMark;
    }

    /**
     * Gets the student of this student mark record.
     * @return the student of this student mark record.
     */
    public IStudent getStudent() {
        return student;
    }

    /**
     * Gets the course of this student mark record.
     * @return the course of this student mark record.
     */
    public ICourse getCourse() {
        return course;
    }

    /**
     * Gets the course work marks of this student mark record.
     * @return a map contains the course work marks of this student mark record.
     */
    public Map<ICourseworkComponent, Double> getCourseWorkMarks() {
        return courseWorkMarks;
    }

    /**
     * Gets the total mark of this student mark record.
     * @return the total mark of this student mark record.
     */
    public double getTotalMark() {
        return totalMark;
    }

    /**
     * Sets the main course work marks of this student mark record.
     * @param courseWorkName The name of this main course work.
     * @param result The mark obtained in this main course work.
     */
    public void setMainCourseWorkMarks(String courseWorkName, double result) {

        for (Map.Entry<ICourseworkComponent, Double> entry : courseWorkMarks.entrySet()) {
            ICourseworkComponent courseworkComponent = entry.getKey();
            double previousResult = entry.getValue();
            if (!(courseworkComponent instanceof MainComponent)) {
                continue;
            }
            if (courseworkComponent.getComponentName().equals(courseWorkName)) {
                if (courseworkComponent.getSubComponents().size() != 0) {
                    System.out.println("This main assessment is not stand alone");
                    return;
                }
                this.totalMark += (result - previousResult) * courseworkComponent.getComponentWeight() / 100d;
                entry.setValue(result);

                System.out.println("The course work component is successfully set to: " + result);
                System.out.println("The course total mark is updated to: " + this.totalMark);
                return;
            }
        }
        System.out.println("This main assessment component does not exist...");

    }


    /**
     * Sets the sub course work marks of this student mark record.
     * @param courseWorkName The name of this sub course work.
     * @param result The mark obtained in this sub course work.
     */
    public void setSubCourseWorkMarks(String courseWorkName, double result) {
        double markIncInMain = 0d;
        for (Map.Entry<ICourseworkComponent, Double> entry : courseWorkMarks.entrySet()) {
            ICourseworkComponent courseworkComponent = entry.getKey();
            double previousResult = entry.getValue();
            if (!(courseworkComponent instanceof SubComponent)) {
                continue;
            }
            if (courseworkComponent.getComponentName().equals(courseWorkName)) {
                // Set the subComponent mark, calculate the main component increment
                markIncInMain = (result - previousResult) * courseworkComponent.getComponentWeight() / 100d;
                entry.setValue(result);

                System.out.println("The sub course work component is successfully set to: " + result);
                System.out.println("The main course work component increase by: " + markIncInMain);
            }
        }
        // Find its main component and update
        for (Map.Entry<ICourseworkComponent, Double> entry : courseWorkMarks.entrySet()) {
            ICourseworkComponent courseworkComponent = entry.getKey();
            double previousResult = entry.getValue();
            if ((courseworkComponent instanceof MainComponent) && courseworkComponent.getSubComponents().size() != 0) {
                for (ICourseworkComponent subComponent : courseworkComponent.getSubComponents()) {
                    if (subComponent.getComponentName().equals(courseWorkName)) {
                        // We find the main component it is in
                        this.totalMark += markIncInMain * courseworkComponent.getComponentWeight() / 100d;
                        entry.setValue(previousResult + markIncInMain);
                        System.out.println("The course total mark is updated to: " + this.totalMark);
                        return;
                    }
                }

            }
        }

    }
}
