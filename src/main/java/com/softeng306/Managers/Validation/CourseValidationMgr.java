package com.softeng306.Managers.Validation;

import com.softeng306.Database.Database;
import com.softeng306.Interfaces.Database.IDatabase;
import com.softeng306.Interfaces.Entity.ICourse;
import com.softeng306.Interfaces.Managers.Validation.ICourseValidationMgr;
import com.softeng306.Interfaces.Utils.IPrinter;
import com.softeng306.Utils.Printer;
import com.softeng306.Utils.Scanner;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CourseValidationMgr implements ICourseValidationMgr {
    private static CourseValidationMgr instance = null;
    private Scanner scanner = Scanner.getInstance();

    /**
     * Prompts the user to input an existing course.
     * @return the inputted course.
     */
    public ICourse checkCourseExists() {
        IPrinter printer = Printer.getInstance();
        String courseID;
        ICourse currentCourse;
        while(true){
            System.out.println("Enter course ID (-h to print all the course ID):");
            courseID = scanner.nextLine();
            while("-h".equals(courseID)){
                printer.printAllCourses();
                courseID = scanner.nextLine();
            }

            currentCourse = checkCourseExists(courseID);
            if (currentCourse == null) {
                System.out.println("Invalid Course ID. Please re-enter.");
            }else{
                break;
            }
        }
        return currentCourse;
    }

    /**
     * Checks whether this course ID is used by other courses.
     * @param courseID The inputted course ID.
     * @return the existing course or else null.
     */
    public ICourse checkCourseExists(String courseID) {
        IDatabase database = Database.getInstance();
        List<ICourse> anyCourse = database.getCourses().stream().filter(c->courseID.equals(c.getCourseID())).collect(Collectors.toList());

        if(anyCourse.size() == 0){
            return null;
        }
        return anyCourse.get(0);
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
     * Get the instance of the CourseMgr class.
     * @return the singleton instance.
     */
    public static CourseValidationMgr getInstance() {
        if (instance == null) {
            instance = new CourseValidationMgr();
        }
        return instance;
    }
}
