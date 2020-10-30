package com.softeng306.Entity;

import com.softeng306.Interfaces.Entity.ICourse;
import com.softeng306.Interfaces.Entity.ICourseRegistration;
import com.softeng306.Interfaces.Entity.IStudent;

import java.util.Comparator;

public class CourseRegistration implements ICourseRegistration {
    private IStudent student;
    private ICourse course;
    private String lectureGroup;
    private String tutorialGroup;
    private String labGroup;

    /**
     * register a new student into a course
     * @param student Student being registered
     * @param course Course student is trying ot register into
     * @param lectureGroup Lecture group the student is trying to enrol in
     * @param tutorialGroup Tutorial group the student is trying to enrol in
     * @param labGroup Lab group the student is trying to enrol in
     */
    public CourseRegistration(IStudent student, ICourse course, String lectureGroup, String tutorialGroup, String labGroup) {
        this.student = student;
        this.course = course;
        this.lectureGroup = lectureGroup;
        this.tutorialGroup = tutorialGroup;
        this.labGroup = labGroup;
    }

    /**
     * get the student which has been registered
     * @return a Student object
     */
    public IStudent getStudent() {
        return student;
    }

    /**
     * get the course the student has been enrolled in
     * @return a Course object
     */
    public ICourse getCourse() {
        return course;
    }

    /**
     * get the lecture group the student has enrolled int
     * @return String name of the lecture group
     */
    public String getLectureGroup() {
        return lectureGroup;
    }

    /**
     * get the tutorial group the student has enrolled int
     * @return String name of the tutorial group
     */
    public String getTutorialGroup() {
        return tutorialGroup;
    }

    /**
     * get the lab group the student has enrolled int
     * @return String name of the lab group
     */
    public String getLabGroup() {
        return labGroup;
    }

    /**
     * methods for sorting the collections of lectures
     */
    public static Comparator<ICourseRegistration> LecComparator = (o1, o2) -> {
        String group1 = o1.getLectureGroup().toUpperCase();
        String group2 = o2.getLectureGroup().toUpperCase();

        //ascending order
        return group1.compareTo(group2);

    };

    /**
     * methods for sorting the collections of tutorials
     */
    public static Comparator<ICourseRegistration> TutComparator = (s1, s2) -> {
        String group1 = s1.getTutorialGroup().toUpperCase();
        String group2 = s2.getTutorialGroup().toUpperCase();

        //ascending order
        return group1.compareTo(group2);

    };

    /**
     * methods for sorting the collections of labs
     */
    public static Comparator<ICourseRegistration> LabComparator = (o1, o2) -> {
        String group1 = o1.getLabGroup().toUpperCase();
        String group2 = o2.getLabGroup().toUpperCase();

        //ascending order
        return group1.compareTo(group2);
    };
}
