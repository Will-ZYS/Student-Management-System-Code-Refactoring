package com.softeng306.Managers.Validation;

import com.softeng306.Database.Database;
import com.softeng306.Interfaces.Database.IDatabase;
import com.softeng306.Interfaces.Entity.IStudent;
import com.softeng306.Interfaces.Managers.Validation.IStudentValidationMgr;
import com.softeng306.Interfaces.Utils.IPrinter;
import com.softeng306.Utils.Printer;
import com.softeng306.Utils.ScannerSingleton;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StudentValidationMgr implements IStudentValidationMgr {
    private static StudentValidationMgr instance = null;
    private ScannerSingleton scanner = ScannerSingleton.getInstance();

    /**
     * Checks whether this student ID is used by other students.
     * @param studentID This student's ID.
     * @return the existing student or else null.
     */
    public IStudent checkStudentExists(String studentID) {
        IDatabase database = Database.getInstance();
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
        IPrinter printer = Printer.getInstance();
        String studentID;
        IStudent currentStudent;
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
     * get the instance of the StudentValidationMgr class
     * @return the singleton instance
     */
    public static StudentValidationMgr getInstance() {
        if (instance == null) {
            instance = new StudentValidationMgr();
        }
        return instance;
    }
}
