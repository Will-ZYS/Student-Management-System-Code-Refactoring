package com.softeng306.Database;

public abstract class FILEMgrAbstract {

    /**
     * The string of {@code COMMA_DELIMITER}.
     */
    private static final String COMMA_DELIMITER = ",";

    /**
     * The string of {@code NEW_LINE_SEPARATOR}.
     */
    private static final String NEW_LINE_SEPARATOR = "\n";

    /**
     * The string of {@code LINE_DELIMITER}.
     */
    private static final String LINE_DELIMITER = "|";

    /**
     * The string of {@code EQUAL_SIGN}.
     */
    private static final String EQUAL_SIGN = "=";

    /**
     * The string of {@code HYPHEN}.
     */
    private static final String HYPHEN = "-";

    /**
     * The string of {@code SLASH}.
     */
    private static final String SLASH = "/";

    /**
     * The file name of studentFile.csv.
     */
    private static final String studentFileName = "data/studentFile.csv";

    /**
     * The file name of courseFile.csv.
     */
    private static final String courseFileName = "data/courseFile.csv";

    /**
     * The file name of professorFile.csv.
     */
    private static final String professorFileName = "data/professorFile.csv";

    /**
     * The file name of courseRegistrationFile.csv.
     */
    private static final String courseRegistrationFileName = "data/courseRegistrationFile.csv";

    /**
     * The file name of markFile.csv.
     */
    private static final String markFileName = "data/markFile.csv";

    /**
     * The header of studentFile.csv.
     */
    private static final String student_HEADER = "studentID,studentName,studentSchool,studentGender,studentGPA,studentYear";

    /**
     * The header of courseFile.csv.
     */
    private static final String course_HEADER = "courseID,courseName,profInCharge,vacancies,totalSeats,lectureGroups,TutorialGroups,LabGroups,MainComponents,AU,courseDepartment,courseType,lecHr,tutHr,labHr";

    /**
     * The header of professorFile.csv.
     */
    private static final String professor_HEADER = "professorID,professorName,profDepartment";

    /**
     * The header of courseRegistrationFile.csv.
     */
    private static final String courseRegistration_HEADER = "studentID,courseID,lectureGroup,tutorialGroup,labGroup";

    /**
     * The header of markFile.csv.
     */
    private static final String mark_HEADER = "studentID,courseID,courseWorkMarks,totalMark";

    /**
     * The index of the student ID in studentFile.csv.
     */
    private static final int studentIdIndex = 0;

    /**
     * The index of the student name in studentFile.csv.
     */
    private static final int studentNameIndex = 1;

    /**
     * The index of the student school in studentFile.csv.
     */
    private static final int studentSchoolIndex = 2;

    /**
     * The index of the student gender in studentFile.csv.
     */
    private static final int studentGenderIndex = 3;

    /**
     * The index of the student GPA in studentFile.csv.
     */
    private static final int studentGPAIndex = 4;

    /**
     * The index of the student year in studentFile.csv.
     */
    private static final int studentYearIndex = 5;

    /**
     * The index of the course ID in courseFile.csv.
     */
    private static final int courseIdIndex = 0;

    /**
     * The index of the course name in courseFile.csv.
     */
    private static final int courseNameIndex = 1;

    /**
     * The index of the professor in charge of this course in courseFile.csv.
     */
    private static final int profInChargeIndex = 2;
    /**
     * The index of course vacancies in courseFile.csv.
     */
    private static final int vacanciesIndex = 3;

    /**
     * The index of course total seats in courseFile.csv.
     */
    private static final int totalSeatsIndex = 4;

    /**
     * The index of course lecture groups in courseFile.csv.
     */
    private static final int lectureGroupsIndex = 5;

    /**
     * The index of course tutorial groups in courseFile.csv.
     */
    private static final int tutorialGroupIndex = 6;

    /**
     * The index of course lab group in courseFile.csv.
     */
    private static final int labGroupIndex = 7;

    /**
     * The index of course main components in courseFile.csv.
     */
    private static final int mainComponentsIndex = 8;

    /**
     * The index of course AU in courseFile.csv.
     */
    private static final int AUIndex = 9;

    /**
     * The index of course department in courseFile.csv.
     */
    private static final int courseDepartmentIndex = 10;

    /**
     * The index of course type in courseFile.csv.
     */
    private static final int courseTypeIndex = 11;

    /**
     * The index of course weekly lecture hour in courseFile.csv.
     */
    private static final int lecHrIndex = 12;

    /**
     * The index of course weekly tutorial hour in courseFile.csv.
     */
    private static final int tutHrIndex = 13;

    /**
     * The index of course weekly lab hour in courseFile.csv.
     */
    private static final int labHrIndex = 14;

    /**
     * The index of professor ID in professorFile.csv.
     */
    private static final int professorIdIndex = 0;

    /**
     * The index of professor name in professorFile.csv.
     */
    private static final int professorNameIndex = 1;

    /**
     * The index of professor department in professorFile.csv.
     */
    private static final int professorDepartmentIndex = 2;

    /**
     * The index of studentID in courseRegistrationFile.csv.
     */
    private static final int studentIdInRegistrationIndex = 0;

    /**
     * The index of courseID in courseRegistrationFile.csv.
     */
    private static final int courseIdInRegistrationIndex = 1;

    /**
     * The index of lectureGroup in courseRegistrationFile.csv.
     */
    private static final int lectureGroupInRegistrationIndex = 2;

    /**
     * The index of tutorialGroup in courseRegistrationFile.csv.
     */
    private static final int tutorialGroupInRegistrationIndex = 3;

    /**
     * The index of labGroup in courseRegistrationFile.csv.
     */
    private static final int labGroupInRegistrationIndex = 4;


    /**
     * The index of studentID in markFile.csv.
     */
    private static final int studentIdIndexInMarks = 0;

    /**
     * The index of courseID in markFile.csv.
     */
    private static final int courseIdIndexInMarks = 1;

    /**
     * The index of courseWorkMark in markFile.csv..
     */
    private static final int courseWorkMarksIndex = 2;

    /**
     * The index of totalMark in markFile.csv.
     */
    private static final int totalMarkIndex = 3;
}
