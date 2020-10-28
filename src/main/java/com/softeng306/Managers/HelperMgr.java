package com.softeng306.Managers;

import com.softeng306.Database.Database;
import com.softeng306.Enum.CourseType;
import com.softeng306.Enum.Department;
import com.softeng306.Enum.Gender;
import com.softeng306.Interfaces.Database.IDatabase;
import com.softeng306.Interfaces.Entity.ICourse;
import com.softeng306.Interfaces.Managers.IHelperMgr;
import com.softeng306.Interfaces.Utils.IPrinter;
import com.softeng306.Utils.Printer;
import com.softeng306.Utils.ScannerSingleton;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * Manages all the validation check in this system.
 */

public class HelperMgr implements IHelperMgr {

    private ScannerSingleton scanner = ScannerSingleton.getInstance();

    private static HelperMgr instance = null;

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
        IPrinter printer = Printer.getInstance();
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
                validCourseString = getCourseInDepartment(courseDepartment);
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
        boolean valid = Pattern.compile(REGEX).matcher(personName).matches();
        if(!valid){
            System.out.println("Wrong format of name.");
        }
        return valid;
    }

    // HELPER METHODS

    /**
     * Displays a list of all the courses in the inputted department.
     *
     * @param department The inputted department.
     * @return a list of all the department values.
     */
    public List<String> getCourseInDepartment(String department) {
        IDatabase database = Database.getInstance();
        List<ICourse> validCourses = database.getCourses().stream().filter(c -> department.equals(c.getCourseDepartment())).collect(Collectors.toList());
        return validCourses.stream().map(ICourse::getCourseID).collect(Collectors.toList());
    }

    /**
     * Gets all the departments a list.
     *
     * @return a list of all the departments.
     */
    private List<String> getAllDepartment() {
        Set<Department> departmentEnumSet = EnumSet.allOf(Department.class);
        List<String> departmentStringList = new ArrayList<>(0);
        for (Department department : departmentEnumSet) {
            departmentStringList.add(department.toString());
        }
        return departmentStringList;

    }

    /**
     * Gets all the genders a list.
     *
     * @return a list of all the genders.
     */
    private List<String> getAllGender() {
        Set<Gender> genderEnumSet = EnumSet.allOf(Gender.class);
        List<String> genderStringList = new ArrayList<>(0);
        for (Gender gender : genderEnumSet) {
            genderStringList.add(gender.toString());
        }
        return genderStringList;
    }

    /**
     * Gets all the course types a list.
     *
     * @return a list of all the course types.
     */
    private List<String> getAllCourseType() {
        Set<CourseType> courseTypeEnumSet = EnumSet.allOf(CourseType.class);
        List<String> courseTypeStringSet = new ArrayList<>(0);
        for (CourseType courseType : courseTypeEnumSet) {
            courseTypeStringSet.add(courseType.toString());
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
