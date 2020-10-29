package com.softeng306;

/**
 * Main class
 * Initial state waiting for the user input
 */
public class Main {

    /**
     * The main function of the system.
     * Command line interface.
     * @param args The command line parameters.
     */
    public static void main(String[] args) {

        //to ensure we are talking to the interface rather than the concrete class
        ICourseMgr courseMgr = CourseMgr.getInstance();
        ICourseRegistrationMgr courseRegistrationMgr = CourseRegistrationMgr.getInstance();
        IMarkMgr markMgr = MarkMgr.getInstance();
        IStudentMgr studentMgr = StudentMgr.getInstance();
        IPrinter printer = Printer.getInstance();
        IScanner scanner = Scanner.getInstance();

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