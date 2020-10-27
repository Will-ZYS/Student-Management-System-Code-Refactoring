package com.softeng306.Managers;

import com.softeng306.Database.CourseFileMgr;
import com.softeng306.Database.Database;
import com.softeng306.Entity.*;
import com.softeng306.Interfaces.Database.ICourseFileMgr;
import com.softeng306.Interfaces.Database.IDatabase;
import com.softeng306.Interfaces.Managers.ICourseMgr;
import com.softeng306.Interfaces.Managers.IGroupMgr;
import com.softeng306.Interfaces.Managers.IProfessorMgr;
import com.softeng306.Interfaces.Managers.IHelperMgr;
import com.softeng306.Interfaces.Utils.IPrinter;
import com.softeng306.Utils.Printer;
import com.softeng306.Interfaces.Entity.ICourse;
import com.softeng306.Interfaces.Entity.ICourseworkComponent;
import com.softeng306.Interfaces.Entity.IProfessor;
import com.softeng306.Interfaces.Entity.IGroup;
import com.softeng306.Utils.ScannerSingleton;

import java.util.*;
import java.io.PrintStream;
import java.io.OutputStream;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CourseMgr implements ICourseMgr {
    public static ScannerSingleton scanner = ScannerSingleton.getInstance();

    private static CourseMgr instance = null;
    private static IPrinter printer = Printer.getInstance();
    private IHelperMgr helperMgr = HelperMgr.getInstance();
    private IDatabase database = Database.getInstance();

    private IProfessorMgr professorMgr = ProfessorMgr.getInstance();
    private IGroupMgr groupMgr = GroupMgr.getInstance();
    private ICourseFileMgr courseFileMgr = CourseFileMgr.getInstance();

    /**
     * Creates a new course and stores it in the file.
     */
    public void addCourse() {
        String courseID;
        String courseName;
        String profID;
        boolean groupNameExists;
        int seatsLeft;
        // Can make the sameCourseID as boolean, set to false.

        courseID = obtainValidCourseId();

        System.out.println("Enter course Name: ");
        courseName = scanner.nextLine();

        int totalSeats = obtainValidTotalSeats();

        int AU = obtainValidAU();

        String courseDepartment = obtainValidCourseDepartment();

        String courseType = obtainValidCourseType();


        int noOfLectureGroups = obtainValidNumberOfLectureGroups(totalSeats);

        int lecWeeklyHour = obtainValidLectureWeeklyHour(AU);


        ArrayList<IGroup> lectureGroups = new ArrayList<>();
        String lectureGroupName;
        int lectureGroupCapacity;
        seatsLeft = totalSeats;
        for (int i = 0; i < noOfLectureGroups; i++) {
            System.out.println("Give a name to the lecture group");
            do {
                groupNameExists = false;
                System.out.println("Enter a group Name: ");
                lectureGroupName = scanner.nextLine();
                if (!groupMgr.checkValidGroupNameInput(lectureGroupName)) {
                    groupNameExists = true;
                    continue;
                }
                if (lectureGroups.size() == 0) {
                    break;
                }
                for (IGroup lectureGroup : lectureGroups) {
                    if (lectureGroup.getGroupName().equals(lectureGroupName)) {
                        groupNameExists = true;
                        System.out.println("This lecture group already exist for this course.");
                        break;
                    }
                }
            } while (groupNameExists);


            do {
                System.out.println("Enter this lecture group's capacity: ");
                do {
                    if (scanner.hasNextInt()) {
                        lectureGroupCapacity = scanner.nextInt();
                        scanner.nextLine();
                        if (lectureGroupCapacity > 0) {
                            break;
                        }
                        System.out.println("Capacity must be positive. Please re-enter.");
                    } else {
                        System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
                    }
                } while (true);
                seatsLeft -= lectureGroupCapacity;
                if ((seatsLeft > 0 && i != (noOfLectureGroups - 1)) || (seatsLeft == 0 && i == noOfLectureGroups - 1)) {
                    IGroup lectureGroup = new Group(lectureGroupName, lectureGroupCapacity, lectureGroupCapacity);

                    lectureGroups.add(lectureGroup);
                    break;
                } else {
                    System.out.println("Sorry, the total capacity you allocated for all the lecture groups exceeds or does not add up to the total seats for this course.");
                    System.out.println("Please re-enter the capacity for the last lecture group " + lectureGroupName + " you have entered.");
                    seatsLeft += lectureGroupCapacity;
                }
            } while (true);
        }

        int noOfTutorialGroups = obtainValidNumberOfTutorialGroups(totalSeats, noOfLectureGroups);
        int totalTutorialSeats = 0;

        int tutWeeklyHour = 0;
        if (noOfTutorialGroups != 0) {
            while (true) {
                System.out.println("Enter the weekly tutorial hour for this course: ");
                if (scanner.hasNextInt()) {
                    tutWeeklyHour = scanner.nextInt();
                    scanner.nextLine();
                    if (tutWeeklyHour < 0 || tutWeeklyHour > AU) {
                        System.out.println("Weekly tutorial hour out of bound. Please re-enter.");
                    } else {
                        break;
                    }
                } else {
                    System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
                }
            }
        }

        ArrayList<IGroup> tutorialGroups = new ArrayList<>();
        String tutorialGroupName;
        int tutorialGroupCapacity;
        for (int i = 0; i < noOfTutorialGroups; i++) {
            System.out.println("Give a name to the tutorial group");
            do {
                groupNameExists = false;
                System.out.println("Enter a group Name: ");
                tutorialGroupName = scanner.nextLine();
                if (!groupMgr.checkValidGroupNameInput(tutorialGroupName)) {
                    groupNameExists = true;
                    continue;
                }
                if (tutorialGroups.size() == 0) {
                    break;
                }
                for (IGroup tutorialGroup : tutorialGroups) {
                    if (tutorialGroup.getGroupName().equals(tutorialGroupName)) {
                        groupNameExists = true;
                        System.out.println("This tutorial group already exist for this course.");
                        break;
                    }
                }
            } while (groupNameExists);

            do {
                System.out.println("Enter this tutorial group's capacity: ");
                if (scanner.hasNextInt()) {
                    tutorialGroupCapacity = scanner.nextInt();
                    scanner.nextLine();
                    totalTutorialSeats += tutorialGroupCapacity;
                    if ((i != noOfTutorialGroups - 1) || (totalTutorialSeats >= totalSeats)) {
                        IGroup tutorialGroup = new Group(tutorialGroupName, tutorialGroupCapacity, tutorialGroupCapacity);
                        tutorialGroups.add(tutorialGroup);
                        break;
                    } else {
                        System.out.println("Sorry, the total capacity you allocated for all the tutorial groups is not enough for this course.");
                        System.out.println("Please re-enter the capacity for the last tutorial group " + tutorialGroupName + " you have entered.");
                        totalTutorialSeats -= tutorialGroupCapacity;
                    }
                } else {
                    System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
                }
            } while (true);
        }

        int noOfLabGroups = obtainValidNumberOfLabGroups(totalSeats, noOfLectureGroups);
        int totalLabSeats = 0;

        int labWeeklyHour = 0;
        if (noOfLabGroups != 0) {
            while (true) {
                System.out.println("Enter the weekly lab hour for this course: ");
                if (scanner.hasNextInt()) {
                    labWeeklyHour = scanner.nextInt();
                    scanner.nextLine();
                    if (labWeeklyHour < 0 || labWeeklyHour > AU) {
                        System.out.println("Weekly lab hour out of bound. Please re-enter.");
                    } else {
                        break;
                    }
                } else {
                    System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
                }
            }
        }

        ArrayList<IGroup> labGroups = new ArrayList<>();
        String labGroupName;
        int labGroupCapacity;
        for (int i = 0; i < noOfLabGroups; i++) {
            System.out.println("Give a name to this lab group");
            do {
                groupNameExists = false;
                System.out.println("Enter a group Name: ");
                labGroupName = scanner.nextLine();
                if (!groupMgr.checkValidGroupNameInput(labGroupName)) {
                    groupNameExists = true;
                    continue;
                }
                if (labGroups.size() == 0) {
                    break;
                }
                for (IGroup labGroup : labGroups) {
                    if (labGroup.getGroupName().equals(labGroupName)) {
                        groupNameExists = true;
                        System.out.println("This lab group already exist for this course.");
                        break;
                    }
                }
            } while (groupNameExists);

            do {
                System.out.println("Enter this lab group's capacity: ");
                labGroupCapacity = scanner.nextInt();
                scanner.nextLine();
                totalLabSeats += labGroupCapacity;
                if ((i != noOfLabGroups - 1) || (totalLabSeats >= totalSeats)) {
                    IGroup labGroup = new Group(labGroupName, labGroupCapacity, labGroupCapacity);
                    labGroups.add(labGroup);
                    break;
                } else {
                    System.out.println("Sorry, the total capacity you allocated for all the lab groups is not enough for this course.");
                    System.out.println("Please re-enter the capacity for the last lab group " + labGroupName + " you have entered.");
                    totalLabSeats -= labGroupCapacity;
                }
            } while (true);
        }

        IProfessor profInCharge;
        List<String> professorsInDepartment = new ArrayList<String>(0);
        professorsInDepartment = printer.printProfInDepartment(courseDepartment, false);
        while (true) {
            System.out.println("Enter the ID for the professor in charge please:");
            System.out.println("Enter -h to print all the professors in " + courseDepartment + ".");
            profID = scanner.nextLine();
            while ("-h".equals(profID)) {
                professorsInDepartment = printer.printProfInDepartment(courseDepartment, true);
                profID = scanner.nextLine();
            }

            profInCharge = professorMgr.checkProfExists(profID);
            if (profInCharge != null) {
                if (professorsInDepartment.contains(profID)) {
                    break;
                } else {
                    System.out.println("This prof is not in " + courseDepartment + ".");
                    System.out.println("Thus he/she cannot teach this course.");
                }
            } else {
                System.out.println("Invalid input. Please re-enter.");
            }
        }


        ICourse course = new Course(courseID, courseName, profInCharge, totalSeats, totalSeats, lectureGroups, tutorialGroups, labGroups, AU, courseDepartment, courseType, lecWeeklyHour, tutWeeklyHour, labWeeklyHour);


        System.out.println("Create course components and set component weightage now?");
        System.out.println("1. Yes");
        System.out.println("2. Not yet");
        int addCourseComponentChoice;
        addCourseComponentChoice = scanner.nextInt();
        scanner.nextLine();

        while (addCourseComponentChoice > 2 || addCourseComponentChoice < 0) {
            System.out.println("Invalid choice, please choose again.");
            System.out.println("1. Yes");
            System.out.println("2. Not yet");
            addCourseComponentChoice = scanner.nextInt();
            scanner.nextLine();
        }
        if (addCourseComponentChoice == 2) {
            //add course into file
            courseFileMgr.writeCourseIntoFile(course);
            database.getCourses().add(course);
            System.out.println("Course " + courseID + " is added, but assessment components are not initialized.");
            printer.printCourses();
            return;
        }

        enterCourseWorkComponentWeightage(course);

        courseFileMgr.writeCourseIntoFile(course);
        database.getCourses().add(course);
        System.out.println("Course " + courseID + " is added");
        printer.printCourses();
    }

    /**
     * Checks whether a course (with all of its groups) have available slots and displays the result.
     */
    public void checkAvailableSlots() {
        //printout the result directly
        System.out.println("checkAvailableSlots is called");
        ICourse currentCourse;

        do {
            currentCourse = checkCourseExists();
            if (currentCourse != null) {
                System.out.println(currentCourse.getCourseID() + " " + currentCourse.getCourseName() + " (Available/Total): " + currentCourse.getVacancies() + "/" + currentCourse.getTotalSeats());
                System.out.println("--------------------------------------------");
                for (IGroup lectureGroup : currentCourse.getLectureGroups()) {
                    System.out.println("Lecture group " + lectureGroup.getGroupName() + " (Available/Total): " + lectureGroup.getAvailableVacancies() + "/" + lectureGroup.getTotalSeats());
                }
                if (currentCourse.getTutorialGroups() != null) {
                    System.out.println();
                    for (IGroup tutorialGroup : currentCourse.getTutorialGroups()) {
                        System.out.println("Tutorial group " + tutorialGroup.getGroupName() + " (Available/Total):  " + tutorialGroup.getAvailableVacancies() + "/" + tutorialGroup.getTotalSeats());
                    }
                }
                if (currentCourse.getLabGroups() != null) {
                    System.out.println();
                    for (IGroup labGroup : currentCourse.getLabGroups()) {
                        System.out.println("Lab group " + labGroup.getGroupName() + " (Available/Total): " + labGroup.getAvailableVacancies() + "/" + labGroup.getTotalSeats());
                    }
                }
                System.out.println();
                break;
            } else {
                System.out.println("This course does not exist. Please check again.");
            }
        } while (true);

    }

    /**
     * Sets the course work component weightage of a course.
     *
     * @param currentCourse The course which course work component is to be set.
     */
    public void enterCourseWorkComponentWeightage(ICourse currentCourse) {

        // Assume when course is created, no components are added yet
        // Assume once components are created and set, cannot be changed.
        int numberOfMain;
        int weight;
        int noOfSub;
        int sub_weight;

        System.out.println("enterCourseWorkComponentWeightage is called");
        if (currentCourse == null) {
            currentCourse = checkCourseExists();
        }


        ArrayList<ICourseworkComponent> mainComponents = new ArrayList<>(0);
        // Check if mainComponent is empty
        if (currentCourse.getMainComponents().size() == 0) {
            // empty course
            System.out.println("Currently course " + currentCourse.getCourseID() + " " + currentCourse.getCourseName() + " does not have any assessment component.");

            int hasFinalExamChoice;
            int examWeight = 0;
            while (true) {
                System.out.println("Does this course have a final exam? Enter your choice:");
                System.out.println("1. Yes! ");
                System.out.println("2. No, all CAs.");
                hasFinalExamChoice = scanner.nextInt();
                scanner.nextLine();
                if (hasFinalExamChoice == 1) {
                    System.out.println("Please enter weight of the exam: ");
                    examWeight = scanner.nextInt();
                    scanner.nextLine();
                    while (examWeight > 80 || examWeight <= 0) {
                        if (examWeight > 80 && examWeight <= 100) {
                            System.out.println("According to the course assessment policy, final exam cannot take up more than 80%...");
                        }
                        System.out.println("Weight entered is invalid, please enter again: ");
                        examWeight = scanner.nextInt();
                        scanner.nextLine();
                    }
                    ICourseworkComponent exam = new MainComponent("Exam", examWeight, new ArrayList<>(0));
                    mainComponents.add(exam);
                    break;
                } else if (hasFinalExamChoice == 2) {
                    System.out.println("Okay, please enter some continuous assessments");
                    break;
                }
            }

            do {
                System.out.println("Enter number of main component(s) to add:");
                while (!scanner.hasNextInt()) {
                    String input = scanner.next();
                    System.out.println("Sorry. " + input + " is not an integer.");
                    System.out.println("Enter number of main component(s) to add:");
                }
                numberOfMain = scanner.nextInt();
                if (numberOfMain < 0) {
                    System.out.println("Please enter a valid positive integer:");
                    continue;
                }
                break;
            } while (true);
            scanner.nextLine();

            boolean componentExist;
            String mainComponentName;
            String subComponentName;
            do {
                int totalWeightage = 100 - examWeight;
                for (int i = 0; i < numberOfMain; i++) {
                    ArrayList<ICourseworkComponent> subComponents = new ArrayList<>(0);
                    do {
                        componentExist = false;
                        System.out.println("Total weightage left to assign: " + totalWeightage);
                        System.out.println("Enter main component " + (i + 1) + " name: ");
                        mainComponentName = scanner.nextLine();

                        if (mainComponents.size() == 0) {
                            break;
                        }
                        if (mainComponentName.equals("Exam")) {
                            System.out.println("Exam is a reserved assessment.");
                            componentExist = true;
                            continue;
                        }
                        for (ICourseworkComponent mainComponent : mainComponents) {
                            if (mainComponent.getComponentName().equals(mainComponentName)) {
                                componentExist = true;
                                System.out.println("This sub component already exist. Please enter.");
                                break;
                            }
                        }
                    } while (componentExist);

                    do {
                        System.out.println("Enter main component " + (i + 1) + " weightage: ");
                        while (!scanner.hasNextInt()) {
                            String input = scanner.next();
                            System.out.println("Sorry. " + input + " is not an integer.");
                            System.out.println("Enter main component " + (i + 1) + " weightage:");
                        }
                        weight = scanner.nextInt();
                        if (weight < 0 || weight > totalWeightage) {
                            System.out.println("Please enter a weight between 0 ~ " + totalWeightage + ":");
                            continue;
                        }
                        break;
                    } while (true);
                    scanner.nextLine();
                    totalWeightage -= weight;
                    do {
                        System.out.println("Enter number of sub component under main component " + (i + 1) + ":");
                        while (!scanner.hasNextInt()) {
                            String input = scanner.next();
                            System.out.println("Sorry. " + input + " is not an integer.");
                            System.out.println("Enter number of sub component under main component " + (i + 1) + ":");
                        }
                        noOfSub = scanner.nextInt();
                        if (noOfSub < 0) {
                            System.out.println("Please enter a valid integer:");
                            continue;
                        }
                        break;
                    } while (true);
                    scanner.nextLine();
                    boolean flagSub = true;
                    while (flagSub) {

                        int sub_totWeight = 100;
                        for (int j = 0; j < noOfSub; j++) {


                            do {
                                componentExist = false;
                                System.out.println("Total weightage left to assign to sub component: " + sub_totWeight);
                                System.out.println("Enter sub component " + (j + 1) + " name: ");
                                subComponentName = scanner.nextLine();

                                if (subComponents.size() == 0) {
                                    break;
                                }
                                if (subComponentName.equals("Exam")) {
                                    System.out.println("Exam is a reserved assessment.");
                                    componentExist = true;
                                    continue;
                                }
                                for (ICourseworkComponent subComponent : subComponents) {
                                    if (subComponent.getComponentName().equals(subComponentName)) {
                                        componentExist = true;
                                        System.out.println("This sub component already exist. Please enter.");
                                        break;
                                    }
                                }
                            } while (componentExist);


                            do {
                                System.out.println("Enter sub component " + (j + 1) + " weightage: ");
                                while (!scanner.hasNextInt()) {
                                    String input = scanner.next();
                                    System.out.println("Sorry. " + input + " is not an integer.");
                                    System.out.println("Enter sub component " + (j + 1) + " weightage (out of the main component): ");
                                }
                                sub_weight = scanner.nextInt();
                                if (sub_weight < 0 || sub_weight > sub_totWeight) {
                                    System.out.println("Please enter a weight between 0 ~ " + sub_totWeight + ":");
                                    continue;
                                }
                                break;
                            } while (true);
                            scanner.nextLine();

                            //Create Subcomponent

                            ICourseworkComponent sub = new SubComponent(subComponentName, sub_weight);
                            subComponents.add(sub);
                            sub_totWeight -= sub_weight;
                        }
                        if (sub_totWeight != 0 && noOfSub != 0) {
                            System.out.println("ERROR! sub component weightage does not tally to 100");
                            System.out.println("You have to reassign!");
                            subComponents.clear();
                            flagSub = true;
                        } else {
                            flagSub = false;
                        }
                        //exit if weight is fully allocated
                    }
                    //Create main component
                    ICourseworkComponent main = new MainComponent(mainComponentName, weight, subComponents);
                    mainComponents.add(main);
                }

                if (totalWeightage != 0) {
                    // weightage assign is not tallied
                    System.out.println("Weightage assigned does not tally to 100!");
                    System.out.println("You have to reassign!");
                    mainComponents.clear();
                } else {
                    break;
                }
            } while (true);


            //set maincomponent to course
            currentCourse.setMainComponents(mainComponents);

        } else {
            System.out.println("Course Assessment has been settled already!");
        }
        System.out.println(currentCourse.getCourseID() + " " + currentCourse.getCourseName() + " components: ");
        for (ICourseworkComponent each_comp : currentCourse.getMainComponents()) {
            System.out.println("    " + each_comp.getComponentName() + " : " + each_comp.getComponentWeight() + "%");
            for (ICourseworkComponent each_sub : each_comp.getSubComponents()) {
                System.out.println("        " + each_sub.getComponentName() + " : " + each_sub.getComponentWeight() + "%");
            }
        }
        // Update course into course.csv
    }

    /**
     * Prompts the user to input an existing course.
     * @return the inputted course.
     */
    public ICourse checkCourseExists() {
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
     * Helper method which queries the user for a valid courseID
     * @return Valid CourseID
     */
    private String obtainValidCourseId() {
        String courseID;
        while (true) {
            System.out.println("Give this course an ID: ");
            courseID = scanner.nextLine();
            if (checkValidCourseIDInput(courseID)) {
                if (checkCourseExists(courseID) == null) {
                    break;
                } else {
                    System.out.println("Sorry. The course ID is used. This course already exists.");
                }
            }
        }
        return courseID;
    }

    /**
     * Helper method which queries the user for valid total seat count
     * @return Valid total seats
     */
    private int obtainValidTotalSeats() {
        int totalSeats;
        while (true) {
            System.out.println("Enter the total vacancy of this course: ");
            if (scanner.hasNextInt()) {
                totalSeats = scanner.nextInt();
                if (totalSeats <= 0) {
                    System.out.println("Please enter a valid vacancy (greater than 0)");
                } else {
                    break;
                }
            } else {
                System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
                System.out.println("Please re-enter");
            }
        }
        return totalSeats;
    }

    /**
     * Helper method which queries the user for valid AU number
     * @return Valid AU number
     */
    private int obtainValidAU() {
        int AU;
        while (true) {
            System.out.println("Enter number of academic unit(s): ");
            if (scanner.hasNextInt()) {
                AU = scanner.nextInt();
                scanner.nextLine();
                if (AU < 0 || AU > 10) {
                    System.out.println("AU out of bound. Please re-enter.");
                } else {
                    break;
                }
            } else {
                System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
            }
        }
        return AU;
    }

    /**
     * Helper method which queries the user for valid course department
     * @return Valid course department
     */
    private String obtainValidCourseDepartment() {
        String courseDepartment;
        while (true) {
            System.out.println("Enter course's department (uppercase): ");
            System.out.println("Enter -h to print all the departments.");
            courseDepartment = scanner.nextLine();
            while ("-h".equals(courseDepartment)) {
                printer.printAllDepartment();
                courseDepartment = scanner.nextLine();
            }
            if (helperMgr.checkDepartmentValidation(courseDepartment)) {
                break;
            }
        }
        return courseDepartment;
    }

    /**
     * Helper method which queries the user for valid course type
     * @return Valid course type
     */
    private String obtainValidCourseType() {
        String courseType;
        while (true) {
            System.out.println("Enter course type (uppercase): ");
            System.out.println("Enter -h to print all the course types.");
            courseType = scanner.nextLine();
            while (courseType.equals("-h")) {
                printer.printAllCourseType();
                courseType = scanner.nextLine();
            }
            if (helperMgr.checkCourseTypeValidation(courseType)) {
                break;
            }
        }
        return courseType;
    }

    /**
     * Helper method which queries the user for valid number of lecture groups
     * @param totalSeats
     * @return Valid number of lecture groups
     */
    private int obtainValidNumberOfLectureGroups(int totalSeats) {
        int noOfLectureGroups;
        do {
            System.out.println("Enter the number of lecture groups: ");
            // lecture group number cannot be 0 and also cannot be larger than totalSeats
            if (scanner.hasNextInt()) {
                noOfLectureGroups = scanner.nextInt();
                scanner.nextLine();
                if (noOfLectureGroups > 0 && noOfLectureGroups <= totalSeats) {
                    break;
                }
                System.out.println("Invalid input.");
                System.out.println("Number of lecture group must be positive but less than total seats in this course.");
                System.out.println("Please re-enter");
            } else {
                System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
            }
        } while (true);
        return noOfLectureGroups;
    }

    /**
     * Helper method which queries the user for valid lecture hour
     * @param AU
     * @return Valid lecture hour
     */
    private int obtainValidLectureWeeklyHour(int AU) {
        int lecWeeklyHour = 0;
        while (true) {
            System.out.println("Enter the weekly lecture hour for this course: ");
            if (scanner.hasNextInt()) {
                lecWeeklyHour = scanner.nextInt();
                scanner.nextLine();
                if (lecWeeklyHour < 0 || lecWeeklyHour > AU) {
                    System.out.println("Weekly lecture hour out of bound. Please re-enter.");
                } else {
                    break;
                }
            } else {
                System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
            }
        }
        return lecWeeklyHour;
    }

    /**
     * Helper method which queries the user for valid number of tutorial groups
     * @param totalSeats, noOfLectureGroups
     * @return Valid number of tutorial groups
     */
    private int obtainValidNumberOfTutorialGroups(int totalSeats, int noOfLectureGroups) {
        int noOfTutorialGroups;
        do {
            System.out.println("Enter the number of tutorial groups:");
            if (scanner.hasNextInt()) {
                noOfTutorialGroups = scanner.nextInt();
                scanner.nextLine();
                if (noOfTutorialGroups >= 0 && noOfLectureGroups <= totalSeats) {
                    break;
                }
                System.out.println("Invalid input.");
                System.out.println("Number of tutorial group must be non-negative.");
                System.out.println("Please re-enter");
            } else {
                System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
            }
        } while (true);
        return noOfTutorialGroups;
    }

    /**
     * Helper method which queries the user for valid number of lab groups
     * @param totalSeats, noOfLectureGroups
     * @return Valid number of lab groups
     */
    private int obtainValidNumberOfLabGroups(int totalSeats, int noOfLectureGroups) {
        int noOfLabGroups;
        do {
            System.out.println("Enter the number of lab groups: ");
            if (scanner.hasNextInt()) {
                noOfLabGroups = scanner.nextInt();
                scanner.nextLine();
                if (noOfLabGroups >= 0 && noOfLectureGroups <= totalSeats) {
                    break;
                }
                System.out.println("Invalid input.");
                System.out.println("Number of lab group must be non-negative.");
                System.out.println("Please re-enter");
            } else {
                System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
            }
        } while (true);
        return noOfLabGroups;
    }

    /**
     * Get the instance of the CourseMgr class.
     * @return the singleton instance.
     */
    public static CourseMgr getInstance() {
        if (instance == null) {
            instance = new CourseMgr();
        }
        return instance;
    }
}