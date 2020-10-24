package com.softeng306.Managers;

import com.softeng306.Database.Database;
import com.softeng306.Enum.CourseType;
import com.softeng306.Enum.Department;
import com.softeng306.Enum.Gender;
import com.softeng306.Interfaces.Database.IDatabase;
import com.softeng306.Interfaces.Managers.IValidationMgr;
import com.softeng306.Interfaces.Utils.IPrinter;
import com.softeng306.Utils.Printer;
import com.softeng306.Interfaces.Entity.ICourse;
import com.softeng306.Interfaces.Entity.ICourseRegistration;
import com.softeng306.Interfaces.Entity.IProfessor;
import com.softeng306.Interfaces.Entity.IStudent;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.*;
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

    private IDatabase database = Database.getInstance();


    /**
     * Checks whether the inputted department is valid.
     * @param department The inputted department.
     * @return boolean indicates whether the inputted department is valid.
     */
    public boolean checkDepartmentValidation(String department) {
        if(getAllDepartment().contains(department)){
            return true;
        }
        System.out.println("The department is invalid. Please re-enter.");
        return false;
    }

//    /**
//     * Prompts the user to input an existing course.
//     * @return the inputted course.
//     */
//    public ICourse checkCourseExists() {
//        String courseID;
//        ICourse currentCourse;
//        while(true){
//            System.out.println("Enter course ID (-h to print all the course ID):");
//            courseID = scanner.nextLine();
//            while("-h".equals(courseID)){
//                printer.printAllCourses();
//                courseID = scanner.nextLine();
//            }
//
//            System.setOut(dummyStream);
//            currentCourse = checkCourseExists(courseID);
//            if (currentCourse == null) {
//                System.setOut(originalStream);
//                System.out.println("Invalid Course ID. Please re-enter.");
//            }else{
//                break;
//            }
//        }
//        System.setOut(originalStream);
//        return currentCourse;
//    }

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
            if(checkDepartmentValidation(courseDepartment)){
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
     * Checks whether the inputted course type is valid.
     * @param courseType The inputted course type.
     * @return boolean indicates whether the inputted course type is valid.
     */
    public boolean checkCourseTypeValidation(String courseType) {
        if(getAllCourseType().contains(courseType)){
            return true;
        }
        System.out.println("The course type is invalid. Please re-enter.");
        return false;
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
     * Checks whether this course registration record exists.
     * @param studentID The inputted student ID.
     * @param courseID The inputted course ID.
     * @return the existing course registration record or else null.
     */
    public ICourseRegistration checkCourseRegistrationExists(String studentID, String courseID) {
        List<ICourseRegistration> courseRegistrations = database.getCourseRegistrations().stream().filter(cr->studentID.equals(cr.getStudent().getStudentID())).filter(cr->courseID.equals(cr.getCourse().getCourseID())).collect(Collectors.toList());

        if(courseRegistrations.size() == 0){
            return null;
        }
        System.out.println("Sorry. This student already registers this course.");
        return courseRegistrations.get(0);

    }




    /**
     * Checks whether the inputted gender is valid.
     * @param gender The inputted gender.
     * @return boolean indicates whether the inputted gender is valid.
     */
    public boolean checkGenderValidation(String gender) {
        if(getAllGender().contains(gender)){
            return true;
        }
        System.out.println("The gender is invalid. Please re-enter.");
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
     * Checks whether this student ID is used by other students.
     * @param studentID This student's ID.
     * @return the existing student or else null.
     */
    public IStudent checkStudentExists(String studentID) {
        List<IStudent> anyStudent = database.getStudents().stream().filter(s->studentID.equals(s.getStudentID())).collect(Collectors.toList());
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
     * Checks whether this professor ID is used by other professors.
     * @param profID The inputted professor ID.
     * @return the existing professor or else null.
     */
    public IProfessor checkProfExists(String profID) {
        List<IProfessor> anyProf = database.getProfessors().stream().filter(p->profID.equals(p.getProfID())).collect(Collectors.toList());
      
        if(anyProf.size() == 0){
            return null;
        }
        System.out.println("Sorry. The professor ID is used. This professor already exists.");
        return anyProf.get(0);

    }





    /**
     * HELPERS
     */


    /**
     * Gets all the departments as an array list.
     *
     * @return an array list of all the departments.
     */
    public ArrayList<String> getAllDepartment() {
        Set<Department> departmentEnumSet = EnumSet.allOf(Department.class);
        ArrayList<String> departmentStringList = new ArrayList<String>(0);
        Iterator iter = departmentEnumSet.iterator();
        while (iter.hasNext()) {
            departmentStringList.add(iter.next().toString());
        }
        return departmentStringList;

    }

    /**
     * Gets all the genders as an array list.
     *
     * @return an array list of all the genders.
     */
    public ArrayList<String> getAllGender() {
        Set<Gender> genderEnumSet = EnumSet.allOf(Gender.class);
        ArrayList<String> genderStringList = new ArrayList<String>(0);
        Iterator iter = genderEnumSet.iterator();
        while (iter.hasNext()) {
            genderStringList.add(iter.next().toString());
        }
        return genderStringList;
    }

    /**
     * Gets all the course types as an array list.
     *
     * @return an array list of all the course types.
     */
    public ArrayList<String> getAllCourseType() {
        Set<CourseType> courseTypeEnumSet = EnumSet.allOf(CourseType.class);
        ArrayList<String> courseTypeStringSet = new ArrayList<String>(0);
        Iterator iter = courseTypeEnumSet.iterator();
        while (iter.hasNext()) {
            courseTypeStringSet.add(iter.next().toString());
        }
        return courseTypeStringSet;
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
