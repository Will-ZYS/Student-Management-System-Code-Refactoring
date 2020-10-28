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
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CourseMgr implements ICourseMgr {
    public static ScannerSingleton scanner = ScannerSingleton.getInstance();

    private static CourseMgr instance = null;
    private static IPrinter printer = Printer.getInstance();

    private static final String LECTURE_GROUP = "lecture";
    private static final String TUTORIAL_GROUP = "tutorial";
    private static final String LAB_GROUP = "lab";

    /**
     * Creates a new course and stores it in the file.
     */
    public void addCourse() {
        String courseID;
        String courseName;
        String profID;
        IDatabase database = Database.getInstance();
        IProfessorMgr professorMgr = ProfessorMgr.getInstance();
        ICourseFileMgr courseFileMgr = CourseFileMgr.getInstance();
        // Can make the sameCourseID as boolean, set to false.

        courseID = obtainValidCourseId();

        System.out.println("Enter course Name: ");
        courseName = scanner.nextLine();

        int totalSeats = obtainValidTotalSeats();

        int academicUnit = obtainValidAcademicUnit();

        String courseDepartment = obtainValidCourseDepartment();

        String courseType = obtainValidCourseType();

        // Add Lecture Groups
        int noOfLectureGroups = obtainValidNumberOfGroups(LECTURE_GROUP, totalSeats, 0);
        int seatsLeft = totalSeats;
        int lecWeeklyHour = obtainValidWeeklyHour(LECTURE_GROUP, academicUnit, noOfLectureGroups);
        ArrayList<IGroup> lectureGroups = addGroups(LECTURE_GROUP, totalSeats, seatsLeft, noOfLectureGroups);

        // Add Tutorial Groups
        int noOfTutorialGroups = obtainValidNumberOfGroups(TUTORIAL_GROUP, totalSeats, noOfLectureGroups);
        int totalTutorialSeats = 0;
        int tutWeeklyHour = obtainValidWeeklyHour(TUTORIAL_GROUP, academicUnit, noOfTutorialGroups);
        ArrayList<IGroup> tutorialGroups = addGroups(TUTORIAL_GROUP, totalSeats, totalTutorialSeats, noOfLectureGroups);

        // Add Lab Groups
        int noOfLabGroups = obtainValidNumberOfGroups(LAB_GROUP, totalSeats, noOfLectureGroups);
        int totalLabSeats = 0;
        int labWeeklyHour = obtainValidWeeklyHour(LAB_GROUP, academicUnit, noOfLabGroups);
        ArrayList<IGroup> labGroups = addGroups(LAB_GROUP, totalSeats, totalLabSeats, noOfLectureGroups);


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

        ICourse course = new Course(courseID, courseName, profInCharge, totalSeats, totalSeats, lectureGroups, tutorialGroups, labGroups, academicUnit, courseDepartment, courseType, lecWeeklyHour, tutWeeklyHour, labWeeklyHour);

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

    private ArrayList<IGroup> addGroups(String groupType, int totalSeats, int totalGroupSeats, int noOfGroups) {
        IGroupMgr groupMgr = GroupMgr.getInstance();
        ArrayList<IGroup> groups = new ArrayList<>();
        String groupName;
        int groupCapacity;
        for (int i = 0; i < noOfGroups; i++) {
            System.out.println("Give a name to this " + groupType + " group");
            boolean groupNameExists;
            do {
                groupNameExists = false;
                System.out.println("Enter a group Name: ");
                groupName = scanner.nextLine();
                if (!groupMgr.checkValidGroupNameInput(groupName)) {
                    groupNameExists = true;
                    continue;
                }
                if (groups.size() == 0) {
                    break;
                }
                for (IGroup group :groups) {
                    if (group.getGroupName().equals(groupName)) {
                        groupNameExists = true;
                        System.out.println("This " + groupType + " group already exist for this course.");
                        break;
                    }
                }
            } while (groupNameExists);

            do {
                System.out.println("Enter this " + groupType + " group's capacity: ");
                if (groupType.equals(LECTURE_GROUP)) {
                    do {
                        if (scanner.hasNextInt()) {
                            groupCapacity = scanner.nextInt();
                            scanner.nextLine();
                            if (groupCapacity > 0) {
                                break;
                            }
                            System.out.println("Capacity must be positive. Please re-enter.");
                        } else {
                            System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
                        }
                    } while (true);
                    totalGroupSeats -= groupCapacity;
                    boolean isValidLectureGroupSeatNumber = (totalGroupSeats > 0 && i != (noOfGroups - 1)) || (totalGroupSeats == 0 && i == noOfGroups - 1);
                    if (isValidLectureGroupSeatNumber) {
                        IGroup group = new Group(groupName, groupCapacity, groupCapacity);
                        groups.add(group);
                        break;
                    } else {
                        System.out.println("Sorry, the total capacity you allocated for all the lecture groups exceeds or does not add up to the total seats for this course.");
                        System.out.println("Please re-enter the capacity for the last lecture group " + groupName + " you have entered.");
                        totalGroupSeats += groupCapacity;
                    }
                }
                else {
                    if (scanner.hasNextInt()) {
                        groupCapacity = scanner.nextInt();
                        scanner.nextLine();
                        totalGroupSeats += groupCapacity;
                        boolean isValidGroupSeatNumber = (i != noOfGroups - 1) || (totalGroupSeats >= totalSeats);
                        if (isValidGroupSeatNumber) {
                            IGroup labGroup = new Group(groupName, groupCapacity, groupCapacity);
                            groups.add(labGroup);
                            break;
                        } else {
                            System.out.println("Sorry, the total capacity you allocated for all the " + groupType + " groups is not enough for this course.");
                            System.out.println("Please re-enter the capacity for the last " + groupType + " group " + groupName + " you have entered.");
                            totalGroupSeats -= groupCapacity;
                        }
                    } else {
                        System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
                    }
                }
            } while (true);
        }
        return groups;
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

            do {
                int totalWeightage = 100 - examWeight;
                for (int i = 0; i < numberOfMain; i++) {
                    totalWeightage = assignMainComponentWeightage(totalWeightage, mainComponents, i);
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

            //set main component to course
            currentCourse.setMainComponents(mainComponents);

        } else {
            System.out.println("Course Assessment has been settled already!");
        }
        System.out.println(currentCourse.getCourseID() + " " + currentCourse.getCourseName() + " components: ");
        for (ICourseworkComponent component : currentCourse.getMainComponents()) {
            System.out.println("    " + component.getComponentName() + " : " + component.getComponentWeight() + "%");
            for (ICourseworkComponent subComponent : component.getSubComponents()) {
                System.out.println("        " + subComponent.getComponentName() + " : " + subComponent.getComponentWeight() + "%");
            }
        }
    }

    private int assignMainComponentWeightage(int totWeight, List<ICourseworkComponent> mainComponents, int componentIndex) {
        String mainComponentName = obtainValidComponentName(true, totWeight, mainComponents, componentIndex);

        int weight = obtainValidComponentWeight(true, totWeight, componentIndex);

        totWeight -= weight;

        ArrayList<ICourseworkComponent> subComponents = new ArrayList<>(0);
        int noOfSub;
        do {
            System.out.println("Enter number of sub component under main component " + (componentIndex + 1) + ":");
            while (!scanner.hasNextInt()) {
                String input = scanner.next();
                System.out.println("Sorry. " + input + " is not an integer.");
                System.out.println("Enter number of sub component under main component " + (componentIndex + 1) + ":");
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

            int subTotWeight = 100;
            for (int j = 0; j < noOfSub; j++) {

                subTotWeight = assignSubComponentWeightage(subTotWeight, subComponents, j);

            }
            if (subTotWeight != 0 && noOfSub != 0) {
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
        return totWeight;

    }

    private int assignSubComponentWeightage(int subTotWeight, List<ICourseworkComponent> subComponents, int componentIndex) {

        String subComponentName = obtainValidComponentName(false, subTotWeight, subComponents, componentIndex);

        int subWeight = obtainValidComponentWeight(false, subTotWeight, componentIndex);

        //Create Subcomponent
        ICourseworkComponent sub = new SubComponent(subComponentName, subWeight);
        subComponents.add(sub);
        return subTotWeight - subWeight;
    }

    private int obtainValidComponentWeight(boolean isMainComponent, int totWeight, int componentIndex) {
        int subWeight;
        String componentType = isMainComponent ? "main" : "sub";
        do {
            System.out.println("Enter " + componentType + " component " + (componentIndex + 1) + " weightage: ");
            while (!scanner.hasNextInt()) {
                String input = scanner.next();
                System.out.println("Sorry. " + input + " is not an integer.");
                if (isMainComponent) {
                    System.out.println("Enter main component " + (componentIndex + 1) + ": ");
                } else {
                    System.out.println("Enter sub component " + (componentIndex + 1) + " weightage (out of the main component): ");
                }
            }
            subWeight = scanner.nextInt();
            if (subWeight < 0 || subWeight > totWeight) {
                System.out.println("Please enter a weight between 0 ~ " + totWeight + ":");
                continue;
            }
            break;
        } while (true);
        scanner.nextLine();
        return subWeight;
    }

    private String obtainValidComponentName(boolean isMainComponent, int totWeight, List<ICourseworkComponent> components, int componentIndex) {
        boolean componentExist;
        String componentName;
        String componentType = isMainComponent ? "main" : "sub";
        do {
            componentExist = false;
            if (isMainComponent) {
                System.out.println("Total weightage left to assign: " + totWeight);
            }
            else {
                System.out.println("Total weightage left to assign to sub component: " + totWeight);
            }
            System.out.println("Enter " + componentType + " component " + (componentIndex + 1) + " name: ");
            componentName = scanner.nextLine();

            if (components.size() == 0) {
                break;
            }
            if (componentName.equals("Exam")) {
                System.out.println("Exam is a reserved assessment.");
                componentExist = true;
                continue;
            }
            for (ICourseworkComponent subComponent : components) {
                if (subComponent.getComponentName().equals(componentName)) {
                    componentExist = true;
                    System.out.println("This " + componentType + " component already exist. Please enter.");
                    break;
                }
            }
        } while (componentExist);
        return componentName;
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
     * Helper method which queries the user for valid Academic Unit number
     * @return Valid academic unit number
     */
    private int obtainValidAcademicUnit() {
        int academicUnit;
        while (true) {
            System.out.println("Enter number of academic unit(s): ");
            if (scanner.hasNextInt()) {
                academicUnit = scanner.nextInt();
                scanner.nextLine();
                if (academicUnit < 0 || academicUnit > 10) {
                    System.out.println("AU out of bound. Please re-enter.");
                } else {
                    break;
                }
            } else {
                System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
            }
        }
        return academicUnit;
    }

    /**
     * Helper method which queries the user for valid course department
     * @return Valid course department
     */
    private String obtainValidCourseDepartment() {
        String courseDepartment;
        IHelperMgr helperMgr = HelperMgr.getInstance();
        do {
            System.out.println("Enter course's department (uppercase): ");
            System.out.println("Enter -h to print all the departments.");
            courseDepartment = scanner.nextLine();
            while ("-h".equals(courseDepartment)) {
                printer.printAllDepartment();
                courseDepartment = scanner.nextLine();
            }
        } while (!helperMgr.checkDepartmentValidation(courseDepartment));
        return courseDepartment;
    }

    /**
     * Helper method which queries the user for valid course type* @return Valid course type
     */
    private String obtainValidCourseType() {
        String courseType;
        IHelperMgr helperMgr = HelperMgr.getInstance();
        do {
            System.out.println("Enter course type (uppercase): ");
            System.out.println("Enter -h to print all the course types.");
            courseType = scanner.nextLine();
            while (courseType.equals("-h")) {
                printer.printAllCourseType();
                courseType = scanner.nextLine();
            }
        } while (!helperMgr.checkCourseTypeValidation(courseType));
        return courseType;
    }

    /**
     * Helper method which queries the user for valid lecture hour
     * @param academicUnit
     * @return Valid lecture hour
     */
    private int obtainValidWeeklyHour(String groupType, int academicUnit, int noOfGroups) {

        int weeklyHour = 0;
        if (noOfGroups != 0) {
            while (true) {
                System.out.println("Enter the weekly " + groupType + " hour for this course: ");
                if (scanner.hasNextInt()) {
                    weeklyHour = scanner.nextInt();
                    scanner.nextLine();
                    if (weeklyHour < 0 || weeklyHour > academicUnit) {
                        System.out.println("Weekly " + groupType + " hour out of bound. Please re-enter.");
                    } else {
                        break;
                    }
                } else {
                    System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
                }
            }
        }
        return weeklyHour;
    }

    /**
     * Helper method which queries the user for valid number of lab groups
     * @param totalSeats, noOfLectureGroups
     * @return Valid number of lab groups
     */
    private int obtainValidNumberOfGroups(String groupType, int totalSeats, int noOfLectureGroups) {
        int noOfGroups;
        do {
            System.out.println("Enter the number of " + groupType + " groups: ");
            if (scanner.hasNextInt()) {
                noOfGroups = scanner.nextInt();
                if (groupType.equals(LECTURE_GROUP)) {
                    noOfLectureGroups = noOfGroups;
                }
                scanner.nextLine();
                if (noOfGroups >= 0 && noOfLectureGroups <= totalSeats) {
                    break;
                }
                System.out.println("Invalid input.");
                System.out.println("Number of " + groupType + " group must be non-negative.");
                System.out.println("Please re-enter");
            } else {
                System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
            }
        } while (true);
        return noOfGroups;
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