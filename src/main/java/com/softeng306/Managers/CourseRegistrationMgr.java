package com.softeng306.Managers;

import com.softeng306.Database.CourseRegistrationFileMgr;
import com.softeng306.Database.Database;
import com.softeng306.Entity.*;
import com.softeng306.Enum.GroupType;
import com.softeng306.Interfaces.Database.ICourseRegistrationFileMgr;
import com.softeng306.Interfaces.Database.IDatabase;
import com.softeng306.Interfaces.Managers.*;
import com.softeng306.Interfaces.Managers.Validation.ICourseRegistrationValidationMgr;
import com.softeng306.Interfaces.Managers.Validation.ICourseValidationMgr;
import com.softeng306.Interfaces.Managers.Validation.IStudentValidationMgr;
import com.softeng306.Interfaces.Utils.IPrinter;
import com.softeng306.Managers.Validation.CourseRegistrationValidationMgr;
import com.softeng306.Managers.Validation.CourseValidationMgr;
import com.softeng306.Managers.Validation.StudentValidationMgr;
import com.softeng306.Utils.Printer;
import com.softeng306.Interfaces.Entity.ICourse;
import com.softeng306.Interfaces.Entity.ICourseRegistration;
import com.softeng306.Interfaces.Entity.IGroup;
import com.softeng306.Interfaces.Entity.IStudent;

import java.util.*;

public class CourseRegistrationMgr implements ICourseRegistrationMgr {
    private static CourseRegistrationMgr instance = null;


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
        ICourseValidationMgr courseValidationMgr = CourseValidationMgr.getInstance();
        ICourseRegistrationValidationMgr courseRegistrationValidationMgr = CourseRegistrationValidationMgr.getInstance();
        IStudentValidationMgr studentValidationMgr = StudentValidationMgr.getInstance();
        ICourseRegistrationFileMgr courseRegistrationFileMgr = CourseRegistrationFileMgr.getInstance();
        IDatabase database = Database.getInstance();
        IPrinter printer = Printer.getInstance();

        IStudent currentStudent = studentValidationMgr.checkStudentExists();

        String studentID = currentStudent.getStudentID();

        helperMgr.checkCourseDepartmentExists();

        ICourse currentCourse = courseValidationMgr.checkCourseExists();

        String courseID = currentCourse.getCourseID();

        if (courseRegistrationValidationMgr.checkCourseRegistrationExists(studentID, courseID) != null) {
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

        currentCourse.decrementCourseVacancy();

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
