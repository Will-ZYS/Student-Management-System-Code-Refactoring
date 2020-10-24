package com.softeng306.Managers;

import com.softeng306.Database.Database;
import com.softeng306.Database.StudentFileMgr;
import com.softeng306.Interfaces.Database.IDatabase;
import com.softeng306.Interfaces.Database.IStudentFileMgr;
import com.softeng306.Interfaces.Managers.IHelpInfoMgr;
import com.softeng306.Interfaces.Managers.IStudentMgr;
import com.softeng306.Interfaces.Managers.IValidationMgr;
import com.softeng306.Interfaces.Utils.IPrinter;
import com.softeng306.Interfaces.Entity.IStudent;

import com.softeng306.Entity.Student;
import com.softeng306.Utils.Printer;

import java.util.Scanner;

/**
 * Manages the student related operations.
 * Contains addStudent.

 */

public class StudentMgr implements IStudentMgr {
    private static Scanner scanner = new Scanner(System.in);

    private static StudentMgr instance = null;
    private static IPrinter printer = Printer.getInstance();
    private IValidationMgr validationMgr = ValidationMgr.getInstance();
    private IHelpInfoMgr helpInfoMgr = HelpInfoMgr.getInstance();

    private IDatabase database = Database.getInstance();
    private IStudentFileMgr studentFileMgr = StudentFileMgr.getInstance();
  
    /**
     * Uses idNumber to generate student ID.
     */
    private static int idNumber = 1800000;



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
                if (validationMgr.checkValidStudentIDInput(studentID)) {
                    if (validationMgr.checkStudentExists(studentID) == null) {
                        break;
                    }
                }
            }
        }

        while (true) {
            System.out.println("Enter student Name: ");
            studentName = scanner.nextLine();
            if (validationMgr.checkValidPersonNameInput(studentName)) {
                break;
            }
        }

        currentStudent = new Student(studentName);
        if (choice == 1) {
            currentStudent.setStudentID(studentID);
        }


        //student department
        while (true) {
            System.out.println("Enter student's school (uppercase): ");
            System.out.println("Enter -h to print all the schools.");
            studentSchool = scanner.nextLine();
            while ("-h".equals(studentSchool)) {
                printer.printAllDepartment();
                studentSchool = scanner.nextLine();
            }

            if (validationMgr.checkDepartmentValidation(studentSchool)) {
                currentStudent.setStudentSchool(studentSchool);
                break;
            }
        }


        //gender
        String studentGender;
        while (true) {
            System.out.println("Enter student gender (uppercase): ");
            System.out.println("Enter -h to print all the genders.");
            studentGender = scanner.nextLine();
            while ("-h".equals(studentGender)) {
                printer.printAllGender();
                studentGender = scanner.nextLine();
            }

            if (validationMgr.checkGenderValidation(studentGender)) {
                currentStudent.setGender(studentGender);
                break;
            }
        }


        //student year
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
     * Sets the idNumber variable of this student class.
     * @param idNumber static variable idNumber of this class.
     */
    public static void setIdNumber(int idNumber) {
        idNumber = idNumber;
    }
}
