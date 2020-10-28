package com.softeng306.Managers;

import com.softeng306.Database.Database;
import com.softeng306.Database.StudentFileMgr;
import com.softeng306.Interfaces.Database.IDatabase;
import com.softeng306.Interfaces.Database.IStudentFileMgr;
import com.softeng306.Interfaces.Managers.IStudentMgr;
import com.softeng306.Interfaces.Managers.IHelperMgr;
import com.softeng306.Interfaces.Managers.Validation.IStudentValidationMgr;
import com.softeng306.Interfaces.Utils.IPrinter;
import com.softeng306.Interfaces.Entity.IStudent;

import com.softeng306.Entity.Student;
import com.softeng306.Utils.Printer;
import com.softeng306.Utils.ScannerSingleton;

/**
 * Manages the student related operations.
 * Contains addStudent.

 */
public class StudentMgr implements IStudentMgr {

    public static ScannerSingleton scanner = ScannerSingleton.getInstance();

    private static StudentMgr instance = null;

    private static IPrinter printer = Printer.getInstance();

    /**
     * Uses idNumber to generate student ID.
     */
    private int idNumber = 1800000;



    /**
     * Adds a student and put the student into file
     */
    public void addStudent() {
        String studentName, studentSchool;
        String studentID = null;
        int choice, studentYear;
        boolean studentExists;
        String GPA = "not available";
        IStudent currentStudent = null;
        IDatabase database = Database.getInstance();
        IStudentFileMgr studentFileMgr = StudentFileMgr.getInstance();
        IStudentValidationMgr studentValidationMgr = StudentValidationMgr.getInstance();
        System.out.println("addStudent is called");
        System.out.println("Choose the way you want to add a student:");
        System.out.println("1. Manually input the student ID.");
        System.out.println("2. Let the system self-generate the student ID.");
        do {
            System.out.println("Please input your choice:");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice < 1 || choice > 2) {
                    System.out.println("Invalid input. Please re-enter.");
                } else {
                    break;
                }
            } else {
                System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
            }
        } while (true);
        if (choice == 1) {
            while (true) {
                System.out.println("The student ID should follow:");
                System.out.println("Length is exactly 9");
                System.out.println("Start with U (Undergraduate)");
                System.out.println("End with a uppercase letter between A and L");
                System.out.println("Seven digits in the middle");
                System.out.println();
                System.out.println("Give this student an ID: ");
                studentID = scanner.nextLine();
                if (studentValidationMgr.checkValidStudentIDInput(studentID)) {
                    if (studentValidationMgr.checkStudentExists(studentID) == null) {
                        break;
                    } else {
                        System.out.println("Sorry. The student ID is used. This student already exists.");
                    }
                }
            }
        }

        studentName = obtainValidStudentName();

        currentStudent = new Student(studentName);
        if (choice == 1) {
            currentStudent.setStudentID(studentID);
        }

        //student department
        setSchool(currentStudent);

        //gender
        setGender(currentStudent);

        //student year
        setYear(currentStudent);

        studentFileMgr.writeStudentsIntoFile(currentStudent);

        database.getStudents().add(currentStudent);
        System.out.println("Student named: " + studentName + " is added, with ID: " + currentStudent.getStudentID());

        System.out.println("Student List: ");
        System.out.println("| Student ID | Student Name | Student School | Gender | Year | GPA |");

        for (IStudent student : database.getStudents()) {

            if (Double.compare(student.getGPA(), 0.0) != 0) {
                GPA = String.valueOf(student.getGPA());
            }
            System.out.println(" " + student.getStudentID() + " | " + student.getStudentName() + " | " + student.getStudentSchool() + " | " + student.getGender() + " | " + student.getStudentYear() + " | " + GPA);
        }
    }

    /**
     * get the instance of the StudentMgr class
     * @return the singleton instance
     */
    public static StudentMgr getInstance() {
        if (instance == null) {
            instance = new StudentMgr();
        }
        return instance;
    }
  
     /**
     * Generates the ID of a new student.
     * @return the generated student ID.
     */
    public String generateStudentID() {
        String generateStudentID;
        boolean studentIDUsed;
        IDatabase database = Database.getInstance();
        do{
            int rand = (int)(Math.random() * ((76 - 65) + 1)) + 65;
            String lastPlace = Character.toString ((char) rand);
            idNumber += 1;
            generateStudentID = "U" + String.valueOf(idNumber) + lastPlace;
            studentIDUsed = false;
            for(IStudent student: database.getStudents()){
                if(generateStudentID.equals(student.getStudentID())){
                    studentIDUsed = true;
                    break;
                }
            }
            if(!studentIDUsed){
                break;
            }
        }while(true);
        return generateStudentID;
    }



    /**
     * Helper method which queries the user for a valid student name
     * @return student name
     */
    private String obtainValidStudentName() {
        String studentName;
        IHelperMgr helperMgr = HelperMgr.getInstance();
        while (true) {
            System.out.println("Enter student Name: ");
            studentName = scanner.nextLine();
            if (helperMgr.checkValidPersonNameInput(studentName)) {
                break;
            }
        }
        return studentName;
    }

    /**
     * Helper method which sets the school of the student
     * @param currentStudent
     * @return void
     */
    private void setSchool(IStudent currentStudent) {
        String studentSchool;
        IHelperMgr helperMgr = HelperMgr.getInstance();
        while (true) {
            System.out.println("Enter student's school (uppercase): ");
            System.out.println("Enter -h to print all the schools.");
            studentSchool = scanner.nextLine();
            while ("-h".equals(studentSchool)) {
                printer.printAllDepartment();
                studentSchool = scanner.nextLine();
            }

            if (helperMgr.checkDepartmentValidation(studentSchool)) {
                currentStudent.setStudentSchool(studentSchool);
                break;
            }
        }
    }

    /**
     * Helper method which sets the gender of the student
     * @param currentStudent
     * @return void
     */
    private void setGender(IStudent currentStudent) {
        String studentGender;
        IHelperMgr helperMgr = HelperMgr.getInstance();
        while (true) {
            System.out.println("Enter student gender (uppercase): ");
            System.out.println("Enter -h to print all the genders.");
            studentGender = scanner.nextLine();
            while ("-h".equals(studentGender)) {
                printer.printAllGender();
                studentGender = scanner.nextLine();
            }

            if (helperMgr.checkGenderValidation(studentGender)) {
                currentStudent.setGender(studentGender);
                break;
            }
        }
    }

    /**
     * Helper method which sets the year of the student
     * @param currentStudent
     * @return void
     */
    private void setYear(IStudent currentStudent) {
        int studentYear;
        do {
            System.out.println("Enter student's school year (1-4) : ");
            if (scanner.hasNextInt()) {
                studentYear = scanner.nextInt();
                scanner.nextLine();
                if (studentYear < 1 || studentYear > 4) {
                    System.out.println("Your input is out of bound.");
                    System.out.println("Please re-enter an integer between 1 and 4");
                } else {
                    currentStudent.setStudentYear(studentYear);
                    break;
                }
            } else {
                System.out.println("Your input " + scanner.nextLine() + " is not an integer");
                System.out.println("Please re-enter.");
            }
        } while (true);
    }

    /**
     * Sets the idNumber variable of this student class.
     * @param idNumber static variable idNumber of this class.
     */
    public void setIdNumber(int idNumber) {
        idNumber = idNumber;
    }
}
