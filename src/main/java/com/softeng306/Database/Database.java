package com.softeng306.Database;

import com.softeng306.Entity.*;
import com.softeng306.Interfaces.Database.IDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds data retrieved the database for reference
 */
public class Database implements IDatabase {
    private static Database instance = null;

    /**
     * An list of all the students in this school.
     */
    public List<Student> students = new ArrayList<Student>(0);
    /**
     * An list of all the courses in this school.
     */
    public List<Course> courses = new ArrayList<Course>(0);
    /**
     * An list of all the course registration records in this school.
     */
    public List<CourseRegistration> courseRegistrations = new ArrayList<CourseRegistration>(0);
    /**
     * An list of all the student mark records in this school.
     */
    public List<Mark> marks = new ArrayList<Mark>(0);
    /**
     * An list of all the professors in this school.
     */
    public List<Professor> professors = new ArrayList<Professor>(0);

    /**
     * default constructor for database
     * instantiates the collections required for this system
     */
    public Database() {
        //TODO change FILEMgr to something else
        students = FILEMgr.loadStudents();
        courses = FILEMgr.loadCourses();
        courseRegistrations = FILEMgr.loadCourseRegistration();
        marks = FILEMgr.loadStudentMarks();
        professors = FILEMgr.loadProfessors();
    }

    /**
     * returns a list of Students in the system
     * @return list of students
     */
    public List<Student> getStudents() {
        return students;
    }

    /**
     * sets student collection
     * @param students
     */
    public void setStudents(List<Student> students) {
        this.students = students;
    }

    /**
     * returns a list of courses in the system
     * @return list of courses
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * sets course collection
     * @param courses
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * returns a list of course registrations in the system
     * @return list of course registrations
     */
    public List<CourseRegistration> getCourseRegistrations() {
        return courseRegistrations;
    }

    /**
     * sets courseRegistrations collection
     * @param courseRegistrations
     */
    public void setCourseRegistrations(List<CourseRegistration> courseRegistrations) {
        this.courseRegistrations = courseRegistrations;
    }

    /**
     * returns a list of marks in the system
     * @return list of marks
     */
    public List<Mark> getMarks() {
        return marks;
    }

    /**
     * sets marks collection
     * @param marks
     */
    public void setMarks(List<Mark> marks) {
        this.marks = marks;
    }

    /**
     * returns a list of professors in the system
     * @return list of marks
     */
    public List<Professor> getProfessors() {
        return professors;
    }

    /**
     * sets professor collection
     * @param professors
     */
    public void setProfessors(List<Professor> professors) {
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
