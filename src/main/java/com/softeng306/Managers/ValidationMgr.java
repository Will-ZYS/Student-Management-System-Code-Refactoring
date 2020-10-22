package com.softeng306.Managers;

import com.softeng306.*;
import com.softeng306.Entity.Course;
import com.softeng306.Entity.CourseRegistration;
import com.softeng306.Entity.Professor;
import com.softeng306.Entity.Student;
import com.softeng306.Interfaces.Managers.IHelpInfoMgr;
import com.softeng306.Interfaces.Managers.IValidationMgr;
import com.softeng306.Interfaces.Utils.IPrinter;
import com.softeng306.Utils.Printer;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.regex.*;

/**
 * Manages all the validation check in this system.
 */

public class ValidationMgr implements IValidationMgr {

    private static Scanner scanner = new Scanner(System.in);
    private static PrintStream originalStream = System.out;
    private static PrintStream dummyStream = new PrintStream(new OutputStream(){
        public void write(int b) {
            // NO-OP
        }
    });
    private static ValidationMgr instance = null;
    private static IPrinter printer = Printer.getInstance();
    private IHelpInfoMgr helpInfoMgr = HelpInfoMgr.getInstance();
    private IValidationMgr validationMgr = ValidationMgr.getInstance();


    /**
     * Checks whether the inputted department is valid.
     * @param department The inputted department.
     * @return boolean indicates whether the inputted department is valid.
     */
    public boolean checkDepartmentValidation(String department) {
        if(helpInfoMgr.getAllDepartment().contains(department)){
            return true;
        }
        System.out.println("The department is invalid. Please re-enter.");
        return false;
    }

    /**
     * Checks whether the inputted gender is valid.
     * @param gender The inputted gender.
     * @return boolean indicates whether the inputted gender is valid.
     */
    public boolean checkGenderValidation(String gender) {
        if(helpInfoMgr.getAllGender().contains(gender)){
            return true;
        }
        System.out.println("The gender is invalid. Please re-enter.");
        return false;
    }

    /**
     * Checks whether the inputted course type is valid.
     * @param courseType The inputted course type.
     * @return boolean indicates whether the inputted course type is valid.
     */
    public boolean checkCourseTypeValidation(String courseType) {
        if(helpInfoMgr.getAllCourseType().contains(courseType)){
            return true;
        }
        System.out.println("The course type is invalid. Please re-enter.");
        return false;
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
     * Checks whether the inputted course ID is in the correct format.
     * @param courseID The inputted course ID.
     * @return boolean indicates whether the inputted course ID is valid.
     */
    public boolean checkValidCourseIDInput(String courseID) {
        String REGEX = "^[A-Z]{2}[0-9]{3,4}$";
        boolean valid = Pattern.compile(REGEX).matcher(courseID).matches();
        if(!valid){
            System.out.println("Wrong format of course ID.");
        }
        return valid;

    }

    /**
     * Checks whether the inputted professor ID is in the correct format.
     * @param profID The inputted professor ID.
     * @return boolean indicates whether the inputted professor ID is valid.
     */
    public boolean checkValidProfIDInput(String profID) {
        String REGEX = "^P[0-9]{7}[A-Z]$";
        boolean valid =  Pattern.compile(REGEX).matcher(profID).matches();
        if(!valid){
            System.out.println("Wrong format of prof ID.");
        }
        return valid;

    }

    /**
     * Checks whether the inputted person name is in the correct format.
     * This person can be professor or student.
     * @param personName The inputted person name.
     * @return boolean indicates whether the inputted person name is valid.
     */
    public boolean checkValidPersonNameInput(String personName) {
        String REGEX = "^[ a-zA-Z]+$";
        boolean valid =  Pattern.compile(REGEX).matcher(personName).matches();
        if(!valid){
            System.out.println("Wrong format of name.");
        }
        return valid;
    }

    /**
     * Checks whether the inputted group name is in the correct format.
     * @param groupName The inputted group name.
     * @return boolean indicates whether the inputted group name is valid.
     */
    public boolean checkValidGroupNameInput(String groupName) {
        String REGEX = "^[a-zA-Z0-9]+$";
        boolean valid =  Pattern.compile(REGEX).matcher(groupName).matches();
        if(!valid){
            System.out.println("Wrong format of group name.");
        }
        return valid;
    }

    /**
     * Checks whether this student ID is used by other students.
     * @param studentID This student's ID.
     * @return the existing student or else null.
     */
    public Student checkStudentExists(String studentID) {
        List<Student> anyStudent = Main.students.stream().filter(s->studentID.equals(s.getStudentID())).collect(Collectors.toList());
        if(anyStudent.size() == 0){
            return null;
        }
        System.out.println("Sorry. The student ID is used. This student already exists.");
        return anyStudent.get(0);

    }

    /**
     * Prompts the user to input an existing student.
     * @return the inputted student.
     */
    public Student checkStudentExists() {
        String studentID;
        Student currentStudent = null;
        while (true) {
            System.out.println("Enter Student ID (-h to print all the student ID):");
            studentID = scanner.nextLine();
            while("-h".equals(studentID)){
                printer.printAllStudents();
                studentID = scanner.nextLine();
            }

            System.setOut(dummyStream);
            currentStudent = checkStudentExists(studentID);
            System.setOut(originalStream);
            if (currentStudent == null) {
                System.out.println("Invalid Student ID. Please re-enter.");
            }else {
                break;
            }

        }
        return currentStudent;
    }

    /**
     * Prompts the user to input an existing course.
     * @return the inputted course.
     */
    public Course checkCourseExists() {
        String courseID;
        Course currentCourse;
        while(true){
            System.out.println("Enter course ID (-h to print all the course ID):");
            courseID = scanner.nextLine();
            while("-h".equals(courseID)){
                printer.printAllCourses();
                courseID = scanner.nextLine();
            }

            System.setOut(dummyStream);
            currentCourse = validationMgr.checkCourseExists(courseID);
            if (currentCourse == null) {
                System.setOut(originalStream);
                System.out.println("Invalid Course ID. Please re-enter.");
            }else{
                break;
            }
        }
        System.setOut(originalStream);
        return currentCourse;
    }

    /**
     * Prompts the user to input an existing department.
     * @return the inputted department.
     */
    public String checkCourseDepartmentExists() {
        String courseDepartment;
        while(true){
            System.out.println("Which department's courses are you interested? (-h to print all the departments)");
            courseDepartment = scanner.nextLine();
            while("-h".equals(courseDepartment)){
                printer.printAllDepartment();
                courseDepartment = scanner.nextLine();
            }
            if(validationMgr.checkDepartmentValidation(courseDepartment)){
                List<String> validCourseString;
                System.setOut(dummyStream);
                validCourseString = printer.printCourseInDepartment(courseDepartment);
                System.setOut(originalStream);
                if(validCourseString.size() == 0){
                    System.out.println("Invalid choice of department.");
                }else{
                    break;
                }
            }
        }
        return courseDepartment;
    }

    /**
     * Checks whether this course ID is used by other courses.
     * @param courseID The inputted course ID.
     * @return the existing course or else null.
     */
    public Course checkCourseExists(String courseID) {
        List<Course> anyCourse = Main.courses.stream().filter(c->courseID.equals(c.getCourseID())).collect(Collectors.toList());
        if(anyCourse.size() == 0){
            return null;
        }
        System.out.println("Sorry. The course ID is used. This course already exists.");
        return anyCourse.get(0);

    }

    /**
     * Checks whether this professor ID is used by other professors.
     * @param profID The inputted professor ID.
     * @return the existing professor or else null.
     */
    public Professor checkProfExists(String profID) {
        List<Professor> anyProf = Main.professors.stream().filter(p->profID.equals(p.getProfID())).collect(Collectors.toList());
        if(anyProf.size() == 0){
            return null;
        }
        System.out.println("Sorry. The professor ID is used. This professor already exists.");
        return anyProf.get(0);

    }

    /**
     * Checks whether this course registration record exists.
     * @param studentID The inputted student ID.
     * @param courseID The inputted course ID.
     * @return the existing course registration record or else null.
     */
    public CourseRegistration checkCourseRegistrationExists(String studentID, String courseID) {
        List<CourseRegistration> courseRegistrations = Main.courseRegistrations.stream().filter(cr->studentID.equals(cr.getStudent().getStudentID())).filter(cr->courseID.equals(cr.getCourse().getCourseID())).collect(Collectors.toList());
        if(courseRegistrations.size() == 0){
            return null;
        }
        System.out.println("Sorry. This student already registers this course.");
        return courseRegistrations.get(0);

    }

    /**
     * Get the instance of the ValidationMgr class.
     * @return the singleton instance
     */
    public static ValidationMgr getInstance() {
        if (instance == null) {
            instance = new ValidationMgr();
        }
        return instance;
    }
}
