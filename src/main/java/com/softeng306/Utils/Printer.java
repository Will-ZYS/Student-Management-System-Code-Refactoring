package com.softeng306.Utils;

import com.softeng306.Database.CourseRegistrationFileMgr;
import com.softeng306.Database.Database;
import com.softeng306.Entity.*;
import com.softeng306.Enum.CourseType;
import com.softeng306.Enum.Department;
import com.softeng306.Enum.Gender;
import com.softeng306.Enum.GroupType;
import com.softeng306.Interfaces.Database.ICourseRegistrationFileMgr;
import com.softeng306.Interfaces.Database.IDatabase;
import com.softeng306.Interfaces.Entity.*;
import com.softeng306.Interfaces.Managers.IMarkMgr;
import com.softeng306.Interfaces.Managers.IHelperMgr;
import com.softeng306.Interfaces.Managers.Validation.ICourseValidationMgr;
import com.softeng306.Interfaces.Managers.Validation.IStudentValidationMgr;
import com.softeng306.Interfaces.Utils.IPrinter;
import com.softeng306.Managers.*;
import com.softeng306.Managers.Validation.CourseValidationMgr;
import com.softeng306.Managers.Validation.StudentValidationMgr;

import java.util.*;
import java.util.stream.Collectors;

import static com.softeng306.Entity.CourseRegistration.*;

public class Printer implements IPrinter {


    private ScannerSingleton scanner = ScannerSingleton.getInstance();
    private static Printer instance = null;


    public void print(String content) {
        System.out.println(content);
    }

    /**
     * Displays the welcome message.
     */
    public void printWelcome() {
        System.out.println();
        System.out.println("****************** Hello! Welcome to SOFTENG 306 Project 2! ******************");
        System.out.println("Please note this application is not developed in The University of Auckland. All rights reserved for the original developers.");
        System.out.println("Permission has been granted by the original developers to anonymize the code and use for education purposes.");
        System.out.println("******************************************************************************************************************************");
        System.out.println();
    }

    /**
     * Displays the exiting message.
     */
    public void printExitMessage() {
        System.out.println("********* Bye! Thank you for using Main! *********");
        System.out.println();
        System.out.println("                 ######    #      #   #######                   ");
        System.out.println("                 #    ##    #    #    #                         ");
        System.out.println("                 #    ##     #  #     #                         ");
        System.out.println("                 ######       ##      #######                   ");
        System.out.println("                 #    ##      ##      #                         ");
        System.out.println("                 #    ##      ##      #                         ");
        System.out.println("                 ######       ##      #######                   ");
        System.out.println();
    }

    /**
     * Displays all the options of the system.
     */
    public void printOptions() {
        System.out.println("************ I can help you with these functions: *************");
        System.out.println(" 0. Print Options");
        System.out.println(" 1. Add a student");
        System.out.println(" 2. Add a course");
        System.out.println(" 3. Register student for a course including tutorial/lab classes");
        System.out.println(" 4. Check available slots in a class (vacancy in a class)");
        System.out.println(" 5. Print student list by lecture, tutorial or laboratory session for a course");
        System.out.println(" 6. Enter course assessment components weightage");
        System.out.println(" 7. Enter coursework mark – inclusive of its components");
        System.out.println(" 8. Enter exam mark");
        System.out.println(" 9. Print course statistics");
        System.out.println("10. Print student transcript");
        System.out.println("11. Quit Main System");
        System.out.println();
    }

    /**
     * Displays all the professors in the inputted department.
     *
     * @param department The inputted department.
     * @param printOut Represents whether print out the professor information or not
     * @return A list of all the names of professors in the inputted department or else null.
     */
    public List<String> printProfInDepartment(String department, boolean printOut) {
        IHelperMgr helperMgr = HelperMgr.getInstance();
        IDatabase database = Database.getInstance();
        if (helperMgr.checkDepartmentValidation(department)) {
            List<String> validProfString = database.getProfessors().stream().filter(p -> String.valueOf(department).equals(p.getProfDepartment())).map(p -> p.getProfID()).collect(Collectors.toList());
            if (printOut) {
                validProfString.forEach(System.out::println);
            }
            return validProfString;
        }
        System.out.println("None.");
        return null;
    }

    /**
     * Displays a list of IDs of all the students.
     */
    public void printAllStudents() {
        IDatabase database = Database.getInstance();
        database.getStudents().stream().map(s -> s.getStudentID()).forEach(System.out::println);
    }

    /**
     * Displays a list of IDs of all the courses.
     */
    public void printAllCourses() {
        IDatabase database = Database.getInstance();
        database.getCourses().stream().map(c -> c.getCourseID()).forEach(System.out::println);
    }

    /**
     * Displays a list of all the departments.
     */
    public void printAllDepartment() {
        int index = 1;
        for (Department department : Department.values()) {
            System.out.println(index + ": " + department);
            index++;
        }

    }

    /**
     * Displays a list of all the genders.
     */
    public void printAllGender() {
        int index = 1;
        for (Gender gender : Gender.values()) {
            System.out.println(index + ": " + gender);
            index++;
        }

    }

    /**
     * Displays a list of all the course types.
     */
    public void printAllCourseType() {
        int index = 1;
        for (CourseType courseType : CourseType.values()) {
            System.out.println(index + ": " + courseType);
            index++;
        }
    }

    /**
     * Displays a list of all the courses in the inputted department.
     *
     * @param department The inputted department.
     * @return a list of all the department values.
     */
    public List<String> printCourseInDepartment(String department) {
        IDatabase database = Database.getInstance();
        List<ICourse> validCourses = database.getCourses().stream().filter(c -> department.equals(c.getCourseDepartment())).collect(Collectors.toList());
        List<String> validCourseString = validCourses.stream().map(c -> c.getCourseID()).collect(Collectors.toList());
        /**
        validCourseString.forEach(System.out::println);
        if (validCourseString.size() == 0) {
            System.out.println("None.");
        }
         **/
        return validCourseString;
    }

    /**
     * Checks whether the inputted department is valid.
     *
     * @param groupType The type of this group.
     * @param groups    A list of a certain type of groups in a course.
     * @return the name of the group chosen by the user.
     */
    public String printGroupWithVacancyInfo(GroupType groupType, List<IGroup> groups) {
        int index;
        Map<String, Integer> groupAssign = new HashMap<>(0);
        int selectedGroupNum;
        String selectedGroupName = null;

        if (groups.size() != 0) {
            System.out.println("Here is a list of all the " + groupType + " groups with available slots:");
            do {
                index = 0;
                for (IGroup group : groups) {
                    if (group.getAvailableVacancies() == 0) {
                        continue;
                    }
                    index++;
                    System.out.println(index + ": " + group.getGroupName() + " (" + group.getAvailableVacancies() + " vacancies)");
                    groupAssign.put(group.getGroupName(), index);
                }
                System.out.println("Please enter an integer for your choice:");
                selectedGroupNum = scanner.nextInt();
                scanner.nextLine();
                if (selectedGroupNum < 1 || selectedGroupNum > index) {
                    System.out.println("Invalid choice. Please re-enter.");
                } else {
                    break;
                }
            } while (true);

            for (Map.Entry<String, Integer> entry : groupAssign.entrySet()) {
                String groupName = entry.getKey();
                int num = entry.getValue();
                if (num == selectedGroupNum) {
                    selectedGroupName = groupName;
                    break;
                }
            }

            for (IGroup group : groups) {
                if (group.getGroupName().equals(selectedGroupName)) {
                    group.decrementGroupVacancy();
                    break;
                }
            }
        }
        return selectedGroupName;
    }

    /**
     * Prints the list of courses
     */
    public void printCourses() {
        IDatabase database = Database.getInstance();
        System.out.println("Course List: ");
        System.out.println("| Course ID | Course Name | Professor in Charge |");
        for (ICourse course : database.getCourses()) {
            System.out.println("| " + course.getCourseID() + " | " + course.getCourseName() + " | " + course.getProfInCharge().getProfName() + " |");
        }
        System.out.println();
    }

    /**
     * Prints the students in a course according to their lecture group, tutorial group or lab group.
     */
    public void printStudents() {
        ICourseRegistrationFileMgr courseRegistrationFileMgr = CourseRegistrationFileMgr.getInstance();
        ICourseValidationMgr courseValidationMgr = CourseValidationMgr.getInstance();
        System.out.println("printStudent is called");
        ICourse currentCourse = courseValidationMgr.checkCourseExists();

        System.out.println("Print student by: ");
        System.out.println("(1) Lecture group");
        System.out.println("(2) Tutorial group");
        System.out.println("(3) Lab group");

        List<ICourseRegistration> allStuArray = courseRegistrationFileMgr.loadCourseRegistration();

        List<ICourseRegistration> stuArray = new ArrayList<>(0);
        for (ICourseRegistration courseRegistration : allStuArray) {
            if (courseRegistration.getCourse().getCourseID().equals(currentCourse.getCourseID())) {
                stuArray.add(courseRegistration);
            }
        }


        int opt;
        do {
            opt = scanner.nextInt();
            scanner.nextLine();

            System.out.println("------------------------------------------------------");

            if (stuArray.size() == 0) {
                System.out.println("No one has registered this course yet.");
            }

            if (opt == 1) { // print by LECTURE
                String newLec = "";
                Collections.sort(stuArray, LecComparator);   // Sort by Lecture group
                if (stuArray.size() > 0) {
                    for (int i = 0; i < stuArray.size(); i++) {  // loop through all of CourseRegistration Obj
                        if (!newLec.equals(stuArray.get(i).getLectureGroup())) {  // if new lecture group print out group name
                            newLec = stuArray.get(i).getLectureGroup();
                            System.out.println("Lecture group : " + newLec);
                        }
                        System.out.print("Student Name: " + stuArray.get(i).getStudent().getStudentName());
                        System.out.println(" Student ID: " + stuArray.get(i).getStudent().getStudentID());
                    }
                    System.out.println();
                }


            } else if (opt == 2) { // print by TUTORIAL
                String newTut = "";
                Collections.sort(stuArray, TutComparator);
                if (stuArray.size() > 0 && stuArray.get(0).getCourse().getTutorialGroups().size() == 0) {
                    System.out.println("This course does not contain any tutorial group.");
                } else if (stuArray.size() > 0) {
                    for (int i = 0; i < stuArray.size(); i++) {
                        if (!newTut.equals(stuArray.get(i).getTutorialGroup())) {
                            newTut = stuArray.get(i).getTutorialGroup();
                            System.out.println("Tutorial group : " + newTut);
                        }
                        System.out.print("Student Name: " + stuArray.get(i).getStudent().getStudentName());
                        System.out.println(" Student ID: " + stuArray.get(i).getStudent().getStudentID());
                    }
                    System.out.println();
                }

            } else if (opt == 3) { // print by LAB
                String newLab = "";
                Collections.sort(stuArray, LabComparator);
                if (stuArray.size() > 0 && stuArray.get(0).getCourse().getLabGroups().size() == 0) {
                    System.out.println("This course does not contain any lab group.");
                } else if (stuArray.size() > 0) {
                    for (int i = 0; i < stuArray.size(); i++) {
                        if (!newLab.equals(stuArray.get(i).getLabGroup())) {
                            newLab = stuArray.get(i).getLabGroup();
                            System.out.println("Lab group : " + newLab);
                        }
                        System.out.print("Student Name: " + stuArray.get(i).getStudent().getStudentName());
                        System.out.println(" Student ID: " + stuArray.get(i).getStudent().getStudentID());
                    }
                    System.out.println();
                }

            } else {
                System.out.println("Invalid input. Please re-enter.");
            }
            System.out.println("------------------------------------------------------");
        } while (opt < 1 || opt > 3);

    }

    /**
     * Prints transcript (Results of course taken) for a particular student
     */
    public void  printStudentTranscript() {
        IMarkMgr markMgr = MarkMgr.getInstance();
        IStudentValidationMgr studentValidationMgr = StudentValidationMgr.getInstance();
        IDatabase database = Database.getInstance();
        String studentID = studentValidationMgr.checkStudentExists().getStudentID();

        double studentGPA = 0d;
        int thisStudentAU = 0;
        List<IMark> thisStudentMark = new ArrayList<>(0);
        for(IMark mark : database.getMarks()) {
            if (mark.getStudent().getStudentID().equals(studentID)) {
                thisStudentMark.add(mark);
                thisStudentAU += mark.getCourse().getAcademicUnit();
            }
        }

        if (thisStudentMark.size() == 0) {
            System.out.println("------ No transcript ready for this student yet ------");
            return;
        }
        System.out.println("----------------- Official Transcript ------------------");
        System.out.print("Student Name: " + thisStudentMark.get(0).getStudent().getStudentName());
        System.out.println("\tStudent ID: " + thisStudentMark.get(0).getStudent().getStudentID());
        System.out.println("AU for this semester: " + thisStudentAU);
        System.out.println();


        for (IMark mark : thisStudentMark) {
            System.out.print("Course ID: " + mark.getCourse().getCourseID());
            System.out.println("\tCourse Name: " + mark.getCourse().getCourseName());

            for (Map.Entry<ICourseworkComponent, Double> entry : mark.getCourseWorkMarks().entrySet()) {
                ICourseworkComponent assessment = entry.getKey();
                Double result = entry.getValue();
                if(assessment instanceof MainComponent) {
                    System.out.println("Main Assessment: " + assessment.getComponentName() + " ----- (" + assessment.getComponentWeight() + "%)");
                    int mainAssessmentWeight = assessment.getComponentWeight();
                    List<ICourseworkComponent> subAssessments = ((MainComponent) assessment).getSubComponents();
                    for (ICourseworkComponent subAssessment : subAssessments) {
                        System.out.print("Sub Assessment: " + subAssessment.getComponentName() + " -- (" + subAssessment.getComponentWeight() + "% * " + mainAssessmentWeight + "%) --- ");
                        String subAssessmentName = subAssessment.getComponentName();
                        for (Map.Entry<ICourseworkComponent, Double> subEntry : mark.getCourseWorkMarks().entrySet()) {
                            ICourseworkComponent subKey = subEntry.getKey();
                            Double subValue = subEntry.getValue();
                            if (subKey instanceof SubComponent && subKey.getComponentName().equals(subAssessmentName)) {
                                System.out.println("Mark: " + String.valueOf(subValue));
                                break;
                            }
                        }
                    }
                    System.out.println("Main Assessment Total: " + result);
                    System.out.println();
                }
            }

            System.out.println("Course Total: " + mark.getTotalMark());
            studentGPA += markMgr.gpaCalculator(mark.getTotalMark()) * mark.getCourse().getAcademicUnit();
            System.out.println();
        }
        studentGPA /= thisStudentAU;
        System.out.println("GPA for this semester: " + studentGPA);
        if (studentGPA >= 4.50) {
            System.out.println("On track of First Class Honor!");
        } else if (studentGPA >= 4.0) {
            System.out.println("On track of Second Upper Class Honor!");
        } else if (studentGPA >= 3.5) {
            System.out.println("On track of Second Lower Class Honor!");
        } else if (studentGPA >= 3) {
            System.out.println("On track of Third Class Honor!");
        } else {
            System.out.println("Advice: Study hard");
        }
        System.out.println("------------------ End of Transcript -------------------");
    }

    /**
     * Prints the course statics including enrollment rate, average result for every assessment component and the average overall performance of this course.
     */
    public void printCourseStatistics() {
        IMarkMgr markMgr = MarkMgr.getInstance();
        ICourseValidationMgr courseValidationMgr = CourseValidationMgr.getInstance();
        IDatabase database = Database.getInstance();
        System.out.println("printCourseStatistics is called");

        ICourse currentCourse = courseValidationMgr.checkCourseExists();
        String courseID = currentCourse.getCourseID();

        List<IMark> thisCourseMark = new ArrayList<>(0);
        for(IMark mark : database.getMarks()) {
            if (mark.getCourse().getCourseID().equals(courseID)) {
                thisCourseMark.add(mark);
            }
        }

        System.out.println("*************** Course Statistic ***************");
        System.out.println("Course ID: " + currentCourse.getCourseID() + "\tCourse Name: " + currentCourse.getCourseName());
        System.out.println("Course AU: " + currentCourse.getAcademicUnit());
        System.out.println();
        System.out.print("Total Slots: " + currentCourse.getTotalSeats());
        int enrolledNumber = (currentCourse.getTotalSeats() - currentCourse.getVacancies());
        System.out.println("\tEnrolled Student: " + enrolledNumber);
        System.out.printf("Enrollment Rate: %4.2f %%\n", ((double)enrolledNumber / (double)currentCourse.getTotalSeats() * 100d));
        System.out.println();


        int examWeight = 0;
        boolean hasExam = false;
        double averageMark = 0;
        // Find marks for every assessment components
        for (ICourseworkComponent courseworkComponent : currentCourse.getMainComponents()) {
            String thisComponentName = courseworkComponent.getComponentName();

            if (thisComponentName.equals("Exam")) {
                examWeight = courseworkComponent.getComponentWeight();
//                Leave the exam report to the last
                hasExam = true;
            }

            else {
                averageMark = 0;
                System.out.print("Main Component: " + courseworkComponent.getComponentName());
                System.out.print("\tWeight: " + courseworkComponent.getComponentWeight() + "%");

                averageMark += markMgr.computeMark(thisCourseMark, thisComponentName);

                averageMark = averageMark / thisCourseMark.size();
                System.out.println("\t Average: " + averageMark);

                List<ICourseworkComponent> thisSubComponents = ((MainComponent)courseworkComponent).getSubComponents();
                if (thisSubComponents.size() == 0) { continue; }
                for (ICourseworkComponent subComponent : thisSubComponents) {
                    averageMark = 0;
                    System.out.print("Sub Component: " + subComponent.getComponentName());
                    System.out.print("\tWeight: " + subComponent.getComponentWeight() + "% (in main component)");
                    String thisSubComponentName = subComponent.getComponentName();

                    averageMark += markMgr.computeMark(thisCourseMark, thisSubComponentName);

                    averageMark = averageMark / thisCourseMark.size();
                    System.out.println("\t Average: " + averageMark);
                }
                System.out.println();
            }

        }

        if (hasExam) {
            averageMark = 0;
            System.out.print("Final Exam");
            System.out.print("\tWeight: " + examWeight + "%");
            for (IMark mark : thisCourseMark) {
                Map<ICourseworkComponent, Double> thisComponentMarks = mark.getCourseWorkMarks();
                for (Map.Entry<ICourseworkComponent, Double> entry : thisComponentMarks.entrySet()) {
                    ICourseworkComponent key = entry.getKey();
                    double value = entry.getValue();
                    if (key.getComponentName().equals("Exam")) {
                        averageMark += value;
                        break;
                    }
                }
            }
            averageMark = averageMark / thisCourseMark.size();
            System.out.println("\t Average: " + averageMark);
        } else {
            System.out.println("This course does not have final exam.");
        }


        System.out.println();

        System.out.print("Overall Performance: ");
        averageMark = 0;
        for (IMark mark : thisCourseMark) {
            averageMark += mark.getTotalMark();
        }
        averageMark = averageMark / thisCourseMark.size();
        System.out.printf("%4.2f \n", averageMark);

        System.out.println();
        System.out.println("***********************************************");
        System.out.println();

    }

    /**
     * Get the instance of the Printer class.
     * @return the singleton instance
     */
    public static Printer getInstance() {
        if (instance == null) {
            instance = new Printer();
        }
        return instance;
    }
}
