package com.softeng306.Managers;

import com.softeng306.*;
import com.softeng306.Database.Database;
import com.softeng306.Database.FILEMgr;
import com.softeng306.Entity.*;
import com.softeng306.Interfaces.Database.IDatabase;
import com.softeng306.Interfaces.Managers.ICourseRegistrationMgr;
import com.softeng306.Interfaces.Managers.IHelpInfoMgr;
import com.softeng306.Interfaces.Managers.IMarkMgr;
import com.softeng306.Interfaces.Managers.IValidationMgr;
import com.softeng306.Interfaces.Utils.IPrinter;
import com.softeng306.Utils.Printer;

import java.util.*;

import static com.softeng306.Entity.CourseRegistration.LabComparator;
import static com.softeng306.Entity.CourseRegistration.LecComparator;
import static com.softeng306.Entity.CourseRegistration.TutComparator;


public class CourseRegistrationMgr implements ICourseRegistrationMgr {

    private static CourseRegistrationMgr instance = null;
    private static IPrinter printer = Printer.getInstance();
    private IValidationMgr validationMgr = ValidationMgr.getInstance();
    private IHelpInfoMgr helpInfoMgr = HelpInfoMgr.getInstance();
    private IMarkMgr markMgr = MarkMgr.getInstance();

    private IDatabase database = Database.getInstance();


    private static Scanner scanner = new Scanner(System.in);

    /**
     * Registers a course for a student
     */
    public void registerCourse() {
        System.out.println("registerCourse is called");
        String selectedLectureGroupName = null;
        String selectedTutorialGroupName = null;
        String selectedLabGroupName = null;

        Student currentStudent = validationMgr.checkStudentExists();
        String studentID = currentStudent.getStudentID();

        validationMgr.checkCourseDepartmentExists();

        Course currentCourse = validationMgr.checkCourseExists();
        String courseID = currentCourse.getCourseID();


        if (validationMgr.checkCourseRegistrationExists(studentID, courseID) != null) {
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

        ArrayList<Group> lecGroups = new ArrayList<>(0);
        lecGroups.addAll(currentCourse.getLectureGroups());

        selectedLectureGroupName = printer.printGroupWithVacancyInfo("lecture", lecGroups);

        ArrayList<Group> tutGroups = new ArrayList<>(0);
        tutGroups.addAll(currentCourse.getTutorialGroups());

        selectedTutorialGroupName = printer.printGroupWithVacancyInfo("tutorial", tutGroups);

        ArrayList<Group> labGroups = new ArrayList<>(0);
        labGroups.addAll(currentCourse.getLabGroups());

        selectedLabGroupName = printer.printGroupWithVacancyInfo("lab", labGroups);

        currentCourse.enrolledIn();
        CourseRegistration courseRegistration = new CourseRegistration(currentStudent, currentCourse, selectedLectureGroupName, selectedTutorialGroupName, selectedLabGroupName);
        // TOOD FILEMGR AGAIN
        FILEMgr.writeCourseRegistrationIntoFile(courseRegistration);

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
