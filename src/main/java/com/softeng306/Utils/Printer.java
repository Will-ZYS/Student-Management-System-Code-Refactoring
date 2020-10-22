package com.softeng306.Utils;

import com.softeng306.Database.FILEMgr;
import com.softeng306.Entity.*;
import com.softeng306.Enum.CourseType;
import com.softeng306.Enum.Department;
import com.softeng306.Enum.Gender;
import com.softeng306.Interfaces.Managers.IMarkMgr;
import com.softeng306.Interfaces.Managers.IValidationMgr;
import com.softeng306.Interfaces.Utils.IPrinter;
import com.softeng306.Main;
import com.softeng306.Managers.MarkMgr;
import com.softeng306.Managers.ValidationMgr;

import java.util.*;
import java.util.stream.Collectors;

import static com.softeng306.Entity.CourseRegistration.*;

public class Printer implements IPrinter {

    public static Scanner scanner = new Scanner(System.in);
    private static Printer instance = null;
    private static IValidationMgr validationMgr = ValidationMgr.getInstance();
    private static IMarkMgr markMgr = MarkMgr.getInstance();

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
        if (validationMgr.checkDepartmentValidation(department)) {
            List<String> validProfString = Main.professors.stream().filter(p -> String.valueOf(department).equals(p.getProfDepartment())).map(p -> p.getProfID()).collect(Collectors.toList());
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
        Main.students.stream().map(s -> s.getStudentID()).forEach(System.out::println);
    }

    /**
     * Displays a list of IDs of all the courses.
     */
    public void printAllCourses() {
        Main.courses.stream().map(c -> c.getCourseID()).forEach(System.out::println);

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
        List<Course> validCourses = Main.courses.stream().filter(c -> department.equals(c.getCourseDepartment())).collect(Collectors.toList());
        List<String> validCourseString = validCourses.stream().map(c -> c.getCourseID()).collect(Collectors.toList());
        validCourseString.forEach(System.out::println);
        if (validCourseString.size() == 0) {
            System.out.println("None.");
        }
        return validCourseString;
    }

    /**
     * Checks whether the inputted department is valid.
     *
     * @param groupType The type of this group.
     * @param groups    An array list of a certain type of groups in a course.
     * @return the name of the group chosen by the user.
     */
    public String printGroupWithVacancyInfo(String groupType, ArrayList<Group> groups) {
        int index;
        HashMap<String, Integer> groupAssign = new HashMap<String, Integer>(0);
        int selectedGroupNum;
        String selectedGroupName = null;

        if (groups.size() != 0) {
            System.out.println("Here is a list of all the " + groupType + " groups with available slots:");
            do {
                index = 0;
                for (Group group : groups) {
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

            for (HashMap.Entry<String, Integer> entry : groupAssign.entrySet()) {
                String groupName = entry.getKey();
                int num = entry.getValue();
                if (num == selectedGroupNum) {
                    selectedGroupName = groupName;
                    break;
                }
            }

            for (Group group : groups) {
                if (group.getGroupName().equals(selectedGroupName)) {
                    group.enrolledIn();
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
        System.out.println("Course List: ");
        System.out.println("| Course ID | Course Name | Professor in Charge |");
        for (Course course : Main.courses) {
            System.out.println("| " + course.getCourseID() + " | " + course.getCourseName() + " | " + course.getProfInCharge().getProfName() + " |");
        }
        System.out.println();
    }

    /**
     * Prints the students in a course according to their lecture group, tutorial group or lab group.
     */
    public void printStudents() {
        System.out.println("printStudent is called");
        Course currentCourse = validationMgr.checkCourseExists();

        System.out.println("Print student by: ");
        System.out.println("(1) Lecture group");
        System.out.println("(2) Tutorial group");
        System.out.println("(3) Lab group");
        // READ courseRegistrationFILE
        // return ArrayList of Object(student,course,lecture,tut,lab)
        //TODO FILEMGR AGAIN
        ArrayList<CourseRegistration> allStuArray = FILEMgr.loadCourseRegistration();


        ArrayList<CourseRegistration> stuArray = new ArrayList<CourseRegistration>(0);
        for (CourseRegistration courseRegistration : allStuArray) {
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
     * Prints the course statics including enrollment rate, average result for every assessment component and the average overall performance of this course.
     */
    public void printCourseStatistics() {
        System.out.println("printCourseStatistics is called");

        //TODO VALIDATIONMGR NEEDS TO BE INSTANTIATED WHEN REFACTORED INTO PRINTING CLASS
        Course currentCourse = validationMgr.checkCourseExists();
        String courseID = currentCourse.getCourseID();

        ArrayList<Mark> thisCourseMark = new ArrayList<Mark>(0);
        for(Mark mark : Main.marks) {
            if (mark.getCourse().getCourseID().equals(courseID)) {
                thisCourseMark.add(mark);
            }
        }

        System.out.println("*************** Course Statistic ***************");
        System.out.println("Course ID: " + currentCourse.getCourseID() + "\tCourse Name: " + currentCourse.getCourseName());
        System.out.println("Course AU: " + currentCourse.getAU());
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
        for (CourseworkComponent courseworkComponent : currentCourse.getMainComponents()) {
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

                // TODO SIMILAR TO ABOVE, NEED TO INSTANTIATE
                averageMark += markMgr.computeMark(thisCourseMark, thisComponentName);

                averageMark = averageMark / thisCourseMark.size();
                System.out.println("\t Average: " + averageMark);

                ArrayList<SubComponent> thisSubComponents = ((MainComponent)courseworkComponent).getSubComponents();
                if (thisSubComponents.size() == 0) { continue; }
                for (SubComponent subComponent : thisSubComponents) {
                    averageMark = 0;
                    System.out.print("Sub Component: " + subComponent.getComponentName());
                    System.out.print("\tWeight: " + subComponent.getComponentWeight() + "% (in main component)");
                    String thisSubComponentName = subComponent.getComponentName();

                    //TODO SAME AS ABOVE
                    averageMark += MarkMgr.getInstance().computeMark(thisCourseMark, thisSubComponentName);

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
            for (Mark mark : thisCourseMark) {
                HashMap<CourseworkComponent, Double> thisComponentMarks = mark.getCourseWorkMarks();
                for (HashMap.Entry<CourseworkComponent, Double> entry : thisComponentMarks.entrySet()) {
                    CourseworkComponent key = entry.getKey();
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
        for (Mark mark : thisCourseMark) {
            averageMark += mark.getTotalMark();
        }
        averageMark = averageMark / thisCourseMark.size();
        System.out.printf("%4.2f \n", averageMark);

        System.out.println();
        System.out.println("***********************************************");
        System.out.println();


    }


    /**
     * Prints transcript (Results of course taken) for a particular student
     */
    public void  printStudentTranscript() {
        //TODO SIMILAR TO PRINTCOURSESTATISITCS
        String studentID = validationMgr.checkStudentExists().getStudentID();

        double studentGPA = 0d;
        int thisStudentAU = 0;
        ArrayList<Mark> thisStudentMark = new ArrayList<Mark>(0);
        for(Mark mark : Main.marks) {
            if (mark.getStudent().getStudentID().equals(studentID)) {
                thisStudentMark.add(mark);
                thisStudentAU += mark.getCourse().getAU();
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


        for (Mark mark : thisStudentMark) {
            System.out.print("Course ID: " + mark.getCourse().getCourseID());
            System.out.println("\tCourse Name: " + mark.getCourse().getCourseName());

            for (HashMap.Entry<CourseworkComponent, Double> entry : mark.getCourseWorkMarks().entrySet()) {
                CourseworkComponent assessment = entry.getKey();
                Double result = entry.getValue();
                if(assessment instanceof MainComponent) {
                    System.out.println("Main Assessment: " + assessment.getComponentName() + " ----- (" + assessment.getComponentWeight() + "%)");
                    int mainAssessmentWeight = assessment.getComponentWeight();
                    ArrayList<SubComponent> subAssessments = ((MainComponent) assessment).getSubComponents();
                    for (SubComponent subAssessment : subAssessments) {
                        System.out.print("Sub Assessment: " + subAssessment.getComponentName() + " -- (" + subAssessment.getComponentWeight() + "% * " + mainAssessmentWeight + "%) --- ");
                        String subAssessmentName = subAssessment.getComponentName();
                        for (HashMap.Entry<CourseworkComponent, Double> subEntry : mark.getCourseWorkMarks().entrySet()) {
                            CourseworkComponent subKey = subEntry.getKey();
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
            // TODO SAME
            studentGPA += markMgr.gpaCalculator(mark.getTotalMark()) * mark.getCourse().getAU();
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
     * Get the instance of the ValidationMgr class.
     * @return the singleton instance
     */
    public static Printer getInstance() {
        if (instance == null) {
            instance = new Printer();
        }
        return instance;
    }
}
