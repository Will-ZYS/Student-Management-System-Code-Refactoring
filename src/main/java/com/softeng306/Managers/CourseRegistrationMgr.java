package com.softeng306.Managers;

import com.softeng306.Database.CourseRegistrationFileMgr;
import com.softeng306.Database.Database;
import com.softeng306.Entity.*;
import com.softeng306.Enum.GroupType;
import com.softeng306.Interfaces.Database.ICourseRegistrationFileMgr;
import com.softeng306.Interfaces.Database.IDatabase;
import com.softeng306.Interfaces.Managers.*;
import com.softeng306.Interfaces.Utils.IPrinter;
import com.softeng306.Utils.Printer;
import com.softeng306.Interfaces.Entity.ICourse;
import com.softeng306.Interfaces.Entity.ICourseRegistration;
import com.softeng306.Interfaces.Entity.IGroup;
import com.softeng306.Interfaces.Entity.IStudent;
import com.softeng306.Utils.ScannerSingleton;

import java.util.*;
import java.util.stream.Collectors;

public class CourseRegistrationMgr implements ICourseRegistrationMgr {
    public static ScannerSingleton scanner = ScannerSingleton.getInstance();

    private static CourseRegistrationMgr instance = null;
    private static IPrinter printer = Printer.getInstance();


    /**
     * Registers a course for a student
     */
    public void registerCourse() {
        System.out.println("registerCourse is called");
        String selectedLectureGroupName = null;
        String selectedTutorialGroupName = null;
        String selectedLabGroupName = null;
        // Get all necessary manager classes
        IHelperMgr helperMgr = HelperMgr.getInstance();
        IMarkMgr markMgr = MarkMgr.getInstance();
        ICourseMgr courseMgr = CourseMgr.getInstance();
        IStudentMgr studentMgr = StudentMgr.getInstance();
        ICourseRegistrationFileMgr courseRegistrationFileMgr = CourseRegistrationFileMgr.getInstance();
        IDatabase database = Database.getInstance();

        IStudent currentStudent = studentMgr.checkStudentExists();

        String studentID = currentStudent.getStudentID();

        helperMgr.checkCourseDepartmentExists();

        ICourse currentCourse = courseMgr.checkCourseExists();

        String courseID = currentCourse.getCourseID();

        if (checkCourseRegistrationExists(studentID, courseID) != null) {
            return;
        }

        if (currentCourse.getMainComponents().size() == 0) {
            System.out.println("Professor " + currentCourse.getProfInCharge().getProfName() + " is preparing the assessment. Please try to register other courses.");
            return;
        }

        if (currentCourse.getVacancies() == 0) {
            System.out.println("Sorry, the course has no vacancies any more.");
            return;
        }

        System.out.println("Student " + currentStudent.getStudentName() + " with ID: " + currentStudent.getStudentID() +
                " wants to register " + currentCourse.getCourseID() + " " + currentCourse.getCourseName());

        ArrayList<IGroup> lecGroups = new ArrayList<>(0);
        lecGroups.addAll(currentCourse.getLectureGroups());

        selectedLectureGroupName = printer.printGroupWithVacancyInfo(GroupType.LECTURE, lecGroups);

        ArrayList<IGroup> tutGroups = new ArrayList<>(0);
        tutGroups.addAll(currentCourse.getTutorialGroups());

        selectedTutorialGroupName = printer.printGroupWithVacancyInfo(GroupType.TUTORIAL, tutGroups);

        ArrayList<IGroup> labGroups = new ArrayList<>(0);
        labGroups.addAll(currentCourse.getLabGroups());

        selectedLabGroupName = printer.printGroupWithVacancyInfo(GroupType.LAB, labGroups);

        currentCourse.enrolledIn();

        ICourseRegistration courseRegistration = new CourseRegistration(currentStudent, currentCourse, selectedLectureGroupName, selectedTutorialGroupName, selectedLabGroupName);

        courseRegistrationFileMgr.writeCourseRegistrationIntoFile(courseRegistration);

        database.getCourseRegistrations().add(courseRegistration);

        database.getMarks().add(markMgr.initializeMark(currentStudent, currentCourse));

        System.out.println("Course registration successful!");
        System.out.print("Student: " + currentStudent.getStudentName());
        System.out.print("\tLecture Group: " + selectedLectureGroupName);
        if (currentCourse.getTutorialGroups().size() != 0) {
            System.out.print("\tTutorial Group: " + selectedTutorialGroupName);
        }
        if (currentCourse.getLabGroups().size() != 0) {
            System.out.print("\tLab Group: " + selectedLabGroupName);
        }
        System.out.println();
    }

    /**
     * Checks whether this course registration record exists.
     * @param studentID The inputted student ID.
     * @param courseID The inputted course ID.
     * @return the existing course registration record or else null.
     */
    public ICourseRegistration checkCourseRegistrationExists(String studentID, String courseID) {
        IDatabase database = Database.getInstance();
        List<ICourseRegistration> courseRegistrations = database.getCourseRegistrations().stream().filter(cr->studentID.equals(cr.getStudent().getStudentID())).filter(cr->courseID.equals(cr.getCourse().getCourseID())).collect(Collectors.toList());

        if(courseRegistrations.size() == 0){
            return null;
        }
        System.out.println("Sorry. This student already registers this course.");
        return courseRegistrations.get(0);

    }


    /**
     * get the instance of the CourseRegistrationMgr class
     * @return the singleton instance
     */
    public static CourseRegistrationMgr getInstance() {
        if (instance == null) {
            instance = new CourseRegistrationMgr();
        }
        return instance;
    }
}
