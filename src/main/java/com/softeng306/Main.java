package com.softeng306;

import com.softeng306.Database.CourseFileMgr;
import com.softeng306.Database.Database;
import com.softeng306.Database.MarkFileMgr;
import com.softeng306.Interfaces.Database.ICourseFileMgr;
import com.softeng306.Interfaces.Database.IDatabase;
import com.softeng306.Interfaces.Database.IMarkFileMgr;
import com.softeng306.Interfaces.Managers.ICourseMgr;
import com.softeng306.Interfaces.Managers.ICourseRegistrationMgr;
import com.softeng306.Interfaces.Managers.IMarkMgr;
import com.softeng306.Interfaces.Managers.IStudentMgr;
import com.softeng306.Interfaces.Utils.IPrinter;
import com.softeng306.Interfaces.Utils.IScannerSingleton;
import com.softeng306.Managers.CourseMgr;
import com.softeng306.Managers.CourseRegistrationMgr;
import com.softeng306.Managers.MarkMgr;
import com.softeng306.Managers.StudentMgr;
import com.softeng306.Utils.Printer;
import com.softeng306.Utils.ScannerSingleton;

/**
 * Main class
 * Initial state waiting for the user input
 */
public class Main {

//    public static ScannerSingleton scanner = ScannerSingleton.getInstance();
//    private static IDatabase database = Database.getInstance();
//    private static IPrinter printer = Printer.getInstance();
//    private static ICourseFileMgr courseFileMgr = CourseFileMgr.getInstance();
//    private static IMarkFileMgr markFileMgr = MarkFileMgr.getInstance();


    /**
     * The main function of the system.
     * Command line interface.
     * @param args The command line parameters.
     */
    public static void main(String[] args) {

        //TODO maybe use getInstance again so we dont need static?
        ICourseMgr courseMgr = CourseMgr.getInstance();
        ICourseRegistrationMgr courseRegistrationMgr = CourseRegistrationMgr.getInstance();
        IMarkMgr markMgr = MarkMgr.getInstance();
        IStudentMgr studentMgr = StudentMgr.getInstance();
        IPrinter printer = Printer.getInstance();
        IScannerSingleton scanner = ScannerSingleton.getInstance();

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
        IPrinter printer = Printer.getInstance();
        ICourseFileMgr courseFileMgr = CourseFileMgr.getInstance();
        IMarkFileMgr markFileMgr = MarkFileMgr.getInstance();
        IDatabase database = Database.getInstance();

        printer.print("Backing up data before exiting...");
        courseFileMgr.backUpCourse(database.getCourses());
        markFileMgr.backUpMarks(database.getMarks());
        printer.printExitMessage();
    }
}