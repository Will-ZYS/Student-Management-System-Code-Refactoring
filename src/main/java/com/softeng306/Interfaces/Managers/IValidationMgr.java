package com.softeng306.Interfaces.Managers;

import com.softeng306.Entity.Course;
import com.softeng306.Entity.CourseRegistration;
import com.softeng306.Entity.Professor;
import com.softeng306.Entity.Student;

public interface IValidationMgr {

    /**
     * Checks whether the inputted department is valid.
     * @param department The inputted department.
     * @return boolean indicates whether the inputted department is valid.
     */
    boolean checkDepartmentValidation(String department);

    /**
     * Checks whether the inputted gender is valid.
     * @param gender The inputted gender.
     * @return boolean indicates whether the inputted gender is valid.
     */
    boolean checkGenderValidation(String gender);

    /**
     * Checks whether the inputted course type is valid.
     * @param courseType The inputted course type.
     * @return boolean indicates whether the inputted course type is valid.
     */
    boolean checkCourseTypeValidation(String courseType);

    /**
     * Checks whether the inputted student ID is in the correct format.
     * @param studentID The inputted student ID.
     * @return boolean indicates whether the inputted student ID is valid.
     */
    boolean checkValidStudentIDInput(String studentID);

    /**
     * Checks whether the inputted course ID is in the correct format.
     * @param courseID The inputted course ID.
     * @return boolean indicates whether the inputted course ID is valid.
     */
    boolean checkValidCourseIDInput(String courseID);

    /**
     * Checks whether the inputted professor ID is in the correct format.
     * @param profID The inputted professor ID.
     * @return boolean indicates whether the inputted professor ID is valid.
     */
    boolean checkValidProfIDInput(String profID);

    /**
     * Checks whether the inputted person name is in the correct format.
     * This person can be professor or student.
     * @param personName The inputted person name.
     * @return boolean indicates whether the inputted person name is valid.
     */
    boolean checkValidPersonNameInput(String personName);

    /**
     * Checks whether the inputted group name is in the correct format.
     * @param groupName The inputted group name.
     * @return boolean indicates whether the inputted group name is valid.
     */
    boolean checkValidGroupNameInput(String groupName);

    /**
     * Checks whether this student ID is used by other students.
     * @param studentID This student's ID.
     * @return the existing student or else null.
     */
    Student checkStudentExists(String studentID);

    /**
     * Prompts the user to input an existing student.
     * @return the inputted student.
     */
    Student checkStudentExists();

    /**
     * Prompts the user to input an existing course.
     * @return the inputted course.
     */
    Course checkCourseExists();

    /**
     * Prompts the user to input an existing department.
     * @return the inputted department.
     */
    String checkCourseDepartmentExists();

    /**
     * Checks whether this course ID is used by other courses.
     * @param courseID The inputted course ID.
     * @return the existing course or else null.
     */
    Course checkCourseExists(String courseID);

    /**
     * Checks whether this professor ID is used by other professors.
     * @param profID The inputted professor ID.
     * @return the existing professor or else null.
     */
    Professor checkProfExists(String profID);

    /**
     * Checks whether this course registration record exists.
     * @param studentID The inputted student ID.
     * @param courseID The inputted course ID.
     * @return the existing course registration record or else null.
     */
    CourseRegistration checkCourseRegistrationExists(String studentID, String courseID);
}
