package com.softeng306.Managers;

import com.softeng306.Database.Database;
import com.softeng306.Enum.CourseType;
import com.softeng306.Enum.Department;
import com.softeng306.Enum.Gender;
import com.softeng306.Interfaces.Database.IDatabase;
import com.softeng306.Interfaces.Managers.IHelperMgr;
import com.softeng306.Interfaces.Utils.IPrinter;
import com.softeng306.Utils.Printer;

import java.util.*;
import java.util.regex.*;

/**
 * Manages all the validation check in this system.
 */

public class HelperMgr implements IHelperMgr {

    private static Scanner scanner = new Scanner(System.in);
    private static HelperMgr instance = null;
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
                validCourseString = printer.printCourseInDepartment(courseDepartment);
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
     * HELPER METHODS
     */


    /**
     * Gets all the departments as an array list.
     *
     * @return an array list of all the departments.
     */
    private ArrayList<String> getAllDepartment() {
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
    private ArrayList<String> getAllGender() {
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
    private ArrayList<String> getAllCourseType() {
        Set<CourseType> courseTypeEnumSet = EnumSet.allOf(CourseType.class);
        ArrayList<String> courseTypeStringSet = new ArrayList<String>(0);
        Iterator iter = courseTypeEnumSet.iterator();
        while (iter.hasNext()) {
            courseTypeStringSet.add(iter.next().toString());
        }
        return courseTypeStringSet;
    }


    /**
     * Get the instance of the HelperMgr class.
     * @return the singleton instance
     */
    public static HelperMgr getInstance() {
        if (instance == null) {
            instance = new HelperMgr();
        }
        return instance;
    }
}
