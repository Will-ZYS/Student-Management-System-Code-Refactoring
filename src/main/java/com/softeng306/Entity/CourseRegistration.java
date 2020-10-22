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

    public CourseRegistration(IStudent student, ICourse course, String lectureGroup, String tutorialGroup, String labGroup) {
        this.student = student;
        this.course = course;
        this.lectureGroup = lectureGroup;
        this.tutorialGroup = tutorialGroup;
        this.labGroup = labGroup;
    }

    public IStudent getStudent() {
        return student;
    }

    public ICourse getCourse() {
        return course;
    }

    public String getLectureGroup() {
        return lectureGroup;
    }

    public String getTutorialGroup() {
        return tutorialGroup;
    }

    public String getLabGroup() {
        return labGroup;
    }

    public static Comparator<ICourseRegistration> LecComparator = new Comparator<ICourseRegistration>() {
        @Override
        public int compare(ICourseRegistration o1, ICourseRegistration o2) {
            String group1 = o1.getLectureGroup().toUpperCase();
            String group2 = o2.getLectureGroup().toUpperCase();

            //ascending order
            return group1.compareTo(group2);

        }
    };
    public static Comparator<ICourseRegistration> TutComparator = new Comparator<ICourseRegistration>() {
        @Override
        public int compare(ICourseRegistration s1, ICourseRegistration s2) {
            String group1 = s1.getTutorialGroup().toUpperCase();
            String group2 = s2.getTutorialGroup().toUpperCase();

            //ascending order
            return group1.compareTo(group2);

        }
    };
    public static Comparator<ICourseRegistration> LabComparator = new Comparator<ICourseRegistration>() {

        @Override
        public int compare(ICourseRegistration o1, ICourseRegistration o2) {
            String group1 = o1.getLabGroup().toUpperCase();
            String group2 = o2.getLabGroup().toUpperCase();

            //ascending order
            return group1.compareTo(group2);
        }

    };
}
