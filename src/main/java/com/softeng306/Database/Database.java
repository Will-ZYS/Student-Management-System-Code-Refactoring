package com.softeng306.Database;

import com.softeng306.Entity.*;
import com.softeng306.Interfaces.Database.IDatabase;

import java.util.ArrayList;

/**
 * Holds data retrieved the database for reference
 */
public class Database implements IDatabase {
    private static Database instance = null;

    /**
     * An array list of all the students in this school.
     */
    public ArrayList<Student> students = new ArrayList<Student>(0);
    /**
     * An array list of all the courses in this school.
     */
    public ArrayList<Course> courses = new ArrayList<Course>(0);
    /**
     * An array list of all the course registration records in this school.
     */
    public ArrayList<CourseRegistration> courseRegistrations = new ArrayList<CourseRegistration>(0);
    /**
     * An array list of all the student mark records in this school.
     */
    public ArrayList<Mark> marks = new ArrayList<Mark>(0);
    /**
     * An array list of all the professors in this school.
     */
    public ArrayList<Professor> professors = new ArrayList<Professor>(0);

    public Database() {
        //TODO change FILEMgr to something else
        students = FILEMgr.loadStudents();
        courses = FILEMgr.loadCourses();
        courseRegistrations = FILEMgr.loadCourseRegistration();
        marks = FILEMgr.loadStudentMarks();
        professors = FILEMgr.loadProfessors();
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public ArrayList<CourseRegistration> getCourseRegistrations() {
        return courseRegistrations;
    }

    public void setCourseRegistrations(ArrayList<CourseRegistration> courseRegistrations) {
        this.courseRegistrations = courseRegistrations;
    }

    public ArrayList<Mark> getMarks() {
        return marks;
    }

    public void setMarks(ArrayList<Mark> marks) {
        this.marks = marks;
    }

    public ArrayList<Professor> getProfessors() {
        return professors;
    }

    public void setProfessors(ArrayList<Professor> professors) {
        this.professors = professors;
    }

    /**
     * Get the instance of the MarkMgr class.
     * @return the singleton instance.
     */
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }
}
