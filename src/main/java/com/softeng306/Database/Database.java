package com.softeng306.Database;

import com.softeng306.Interfaces.Database.IDatabase;
import com.softeng306.Interfaces.Entity.*;

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
    private static List<IStudent> students = new ArrayList<>(0);
    /**
     * An list of all the courses in this school.
     */
    private static List<ICourse> courses = new ArrayList<>(0);
    /**
     * An list of all the course registration records in this school.
     */
    private static List<ICourseRegistration> courseRegistrations = new ArrayList<>(0);
    /**
     * An list of all the student mark records in this school.
     */
    private static List<IMark> marks;
    /**
     * An list of all the professors in this school.
     */
    private static List<IProfessor> professors = new ArrayList<>(0);

    /**
     * default constructor for database
     * instantiates the collections required for this system
     */
    private Database() {
        //TODO change FILEMgr to something else
        students = StudentFileMgr.getInstance().loadStudents();
        courses = CourseFileMgr.getInstance().loadCourses();
        courseRegistrations = CourseRegistrationFileMgr.getInstance().loadCourseRegistration();
        marks = MarkFileMgr.getInstance().loadStudentMarks();
        professors = ProfessorFileMgr.getInstance().loadProfessors();
    }

    /**
     * returns a list of Students in the system
     * @return list of students
     */
    public List<IStudent> getStudents() {
        return students;
    }

    /**
     * sets student collection
     * @param students
     */
    public void setStudents(List<IStudent> students) {
        this.students = students;
    }

    /**
     * returns a list of courses in the system
     * @return list of courses
     */
    public List<ICourse> getCourses() {
        return courses;
    }

    /**
     * sets course collection
     * @param courses
     */
    public void setCourses(List<ICourse> courses) {
        this.courses = courses;
    }

    /**
     * returns a list of course registrations in the system
     * @return list of course registrations
     */
    public List<ICourseRegistration> getCourseRegistrations() {
        return courseRegistrations;
    }

    /**
     * sets courseRegistrations collection
     * @param courseRegistrations
     */
    public void setCourseRegistrations(List<ICourseRegistration> courseRegistrations) {
        this.courseRegistrations = courseRegistrations;
    }

    /**
     * returns a list of marks in the system
     * @return list of marks
     */
    public List<IMark> getMarks() {
        return marks;
    }

    /**
     * sets marks collection
     * @param marks
     */
    public void setMarks(List<IMark> marks) {
        this.marks = marks;
    }

    /**
     * returns a list of professors in the system
     * @return list of marks
     */
    public List<IProfessor> getProfessors() {
        return professors;
    }

    /**
     * sets professor collection
     * @param professors
     */
    public void setProfessors(List<IProfessor> professors) {
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
