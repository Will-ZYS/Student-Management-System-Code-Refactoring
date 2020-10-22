package com.softeng306;

import com.softeng306.Database.FILEMgr;
import com.softeng306.Entity.*;
import com.softeng306.Interfaces.Managers.*;
import com.softeng306.Interfaces.Utils.IPrinter;
import com.softeng306.Managers.*;
import com.softeng306.Utils.Printer;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    private static IPrinter printer = Printer.getInstance();
    /**
     * An array list of all the students in this school.
     */
    public static ArrayList<Student> students = new ArrayList<Student>(0);
    /**
     * An array list of all the courses in this school.
     */
    public static ArrayList<Course> courses = new ArrayList<Course>(0);
    /**
     * An array list of all the course registration records in this school.
     */
    public static ArrayList<CourseRegistration> courseRegistrations = new ArrayList<CourseRegistration>(0);
    /**
     * An array list of all the student mark records in this school.
     */
    public static ArrayList<Mark> marks = new ArrayList<Mark>(0);
    /**
     * An array list of all the professors in this school.
     */
    public static ArrayList<Professor> professors = new ArrayList<Professor>(0);


    /**
     * The main function of the system.
     * Command line interface.
     * @param args The command line parameters.
     */
    public static void main(String[] args) {

        students = FILEMgr.loadStudents();
        courses = FILEMgr.loadCourses();
        courseRegistrations = FILEMgr.loadCourseRegistration();
        marks = FILEMgr.loadStudentMarks();
        professors = FILEMgr.loadProfessors();

        // I personally dont like having all managers just in fields
        // it feels cleaning to have them in some collection
        ICourseMgr courseMgr = CourseMgr.getInstance();
        ICourseRegistrationMgr courseRegistrationMgr = CourseRegistrationMgr.getInstance();

        // TODO maybe remove these fields as they are not used
        IHelpInfoMgr helpInfoMgr = HelpInfoMgr.getInstance();
        IMarkMgr markMgr = MarkMgr.getInstance();
        IProfessorMgr professorMgr = ProfessorMgr.getInstance();
        IStudentMgr studentMgr = StudentMgr.getInstance();
        IValidationMgr validationMgr = ValidationMgr.getInstance();


        printer.printWelcome();

        int choice;
        do {
            printer.printOptions();
            do {
                System.out.println("Enter your choice, let me help you:");
                while (!scanner.hasNextInt()) {
                    System.out.println("Sorry. " + scanner.nextLine() + " is not an integer.");
                    System.out.println("Enter your choice, let me help you:");
                }
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice < 0 || choice > 11) {
                    System.out.println("Please enter 0 ~ 11 for your choice:");
                    continue;
                }
                break;
            } while (true);

            switch (choice) {
                case 0:
                    break;
                case 1:
                    studentMgr.addStudent();
                    break;
                case 2:
                    courseMgr.addCourse();
                    break;
                case 3:
                    courseRegistrationMgr.registerCourse();
                    break;
                case 4:
                    courseMgr.checkAvailableSlots();
                    break;
                case 5:
                    printer.printStudents();
                    break;
                case 6:
                    courseMgr.enterCourseWorkComponentWeightage(null);
                    break;
                case 7:
                    markMgr.setCourseWorkMark(false);
                    break;
                case 8:
                    markMgr.setCourseWorkMark(true);
                    break;
                case 9:
                    printer.printCourseStatistics();
                    break;
                case 10:
                    printer.printStudentTranscript();
                    break;
                case 11:
                    exitApplication();
                    break;

            }

        } while (choice != 11);
    }

    /**
     * Displays the exiting message.
     */
    public static void exitApplication() {

        printer.print("Backing up data before exiting...");
        FILEMgr.backUpCourse(courses);
        FILEMgr.backUpMarks(marks);
        printer.printExitMessage();

    }

}