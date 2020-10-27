package com.softeng306.Managers;

import com.softeng306.Database.Database;
import com.softeng306.Database.StudentFileMgr;
import com.softeng306.Interfaces.Database.IDatabase;
import com.softeng306.Interfaces.Database.IStudentFileMgr;
import com.softeng306.Interfaces.Managers.IStudentMgr;
import com.softeng306.Interfaces.Managers.IHelperMgr;
import com.softeng306.Interfaces.Utils.IPrinter;
import com.softeng306.Interfaces.Entity.IStudent;

import com.softeng306.Entity.Student;
import com.softeng306.Main;
import com.softeng306.Utils.Printer;
import com.softeng306.Utils.ScannerSingleton;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Manages the student related operations.
 * Contains addStudent.

 */
public class StudentMgr implements IStudentMgr {

    public static ScannerSingleton scanner = ScannerSingleton.getInstance();

    private static StudentMgr instance = null;

    //TODO STATIC TO STOP LOOPING CALLS
    private static IPrinter printer = Printer.getInstance();
    private static IHelperMgr helperMgr = HelperMgr.getInstance();
    private static IDatabase database = Database.getInstance();
    private static IStudentFileMgr studentFileMgr = StudentFileMgr.getInstance();
  
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
                if (checkValidStudentIDInput(studentID)) {
                    if (checkStudentExists(studentID) == null) {
                        break;
                    } else {
                        System.out.println("Sorry. The student ID is used. This student already exists.");
                    }
                }
            }
        }

        while (true) {
            System.out.println("Enter student Name: ");
            studentName = scanner.nextLine();
            if (helperMgr.checkValidPersonNameInput(studentName)) {
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

            if (helperMgr.checkDepartmentValidation(studentSchool)) {
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

            if (helperMgr.checkGenderValidation(studentGender)) {
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
     * Checks whether this student ID is used by other students.
     * @param studentID This student's ID.
     * @return the existing student or else null.
     */
    public IStudent checkStudentExists(String studentID) {
        List<IStudent> anyStudent = database.getStudents().stream().filter(s->studentID.equals(s.getStudentID())).collect(Collectors.toList());
        if(anyStudent.size() == 0){
            return null;
        }
        return anyStudent.get(0);

    }

    /**
     * Prompts the user to input an existing student.
     * @return the inputted student.
     */
    public IStudent checkStudentExists() {
        String studentID;
        IStudent currentStudent = null;
        while (true) {
            System.out.println("Enter Student ID (-h to print all the student ID):");
            studentID = scanner.nextLine();
            while("-h".equals(studentID)){
                printer.printAllStudents();
                studentID = scanner.nextLine();
            }

            currentStudent = checkStudentExists(studentID);
            if (currentStudent == null) {
                System.out.println("Invalid Student ID. Please re-enter.");
            }else {
                break;
            }

        }
        return currentStudent;
    }

    /**
     * Checks whether the inputted student ID is in the correct format.
     * @param studentID The inputted student ID.
     * @return boolean indicates whether the inputted student ID is valid.
     */
    public boolean checkValidStudentIDInput(String studentID) {
        String REGEX = "^U[0-9]{7}[A-Z]$";
        boolean valid = Pattern.compile(REGEX).matcher(studentID).matches();
        if(!valid){
            System.out.println("Wrong format of student ID.");
        }
        return valid;

    }


    /**
     * Sets the idNumber variable of this student class.
     * @param idNumber static variable idNumber of this class.
     */
    public void setIdNumber(int idNumber) {
        idNumber = idNumber;
    }
}
