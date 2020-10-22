package com.softeng306.Managers;


import com.softeng306.*;
import com.softeng306.Database.FILEMgr;
import com.softeng306.Entity.*;
import com.softeng306.Interfaces.Managers.IHelpInfoMgr;
import com.softeng306.Interfaces.Managers.IMarkMgr;
import com.softeng306.Interfaces.Managers.IValidationMgr;

import java.util.*;

/**
 * Manages all the mark related operations.

 */

public class MarkMgr implements IMarkMgr {

    private static MarkMgr instance = null;
    private IValidationMgr validationMgr = ValidationMgr.getInstance();
    private IMarkMgr markMgr = MarkMgr.getInstance();

    private static Scanner scanner = new Scanner(System.in);

    /**
     * Initializes marks for a student when he/she just registered a course.
     * @param student the student this mark record belongs to.
     * @param course the course this mark record about.
     * @return the new added mark.
     */
    public Mark initializeMark(Student student, Course course) {
        HashMap<CourseworkComponent, Double> courseWorkMarks = new HashMap<CourseworkComponent, Double>();
        double totalMark = 0d;
        ArrayList<MainComponent> mainComponents = course.getMainComponents();

        for (MainComponent mainComponent : mainComponents) {
            courseWorkMarks.put(mainComponent, 0d);
            if (mainComponent.getSubComponents().size() > 0) {
                for (SubComponent subComponent : mainComponent.getSubComponents()) {
                    courseWorkMarks.put(subComponent, 0d);
                }
            }
        }
        Mark mark = new Mark(student, course, courseWorkMarks, totalMark);
        //TODO FILEMGR AGAIN
        FILEMgr.updateStudentMarks(mark);
        return mark;
    }

    /**
     * Sets the coursework mark for the mark record.
     * @param isExam whether this coursework component refers to "Exam"
     */
    public void setCourseWorkMark(boolean isExam) {
        System.out.println("enterCourseWorkMark is called");

        String studentID = validationMgr.checkStudentExists().getStudentID();
        String courseID = validationMgr.checkCourseExists().getCourseID();

        for(Mark mark: Main.marks) {
            if (mark.getCourse().getCourseID().equals(courseID) && mark.getStudent().getStudentID().equals(studentID)) {
                //put the set mark function here
                if (!isExam) {
                    System.out.println("Here are the choices you can have: ");
                    ArrayList<String> availableChoices = new ArrayList<String>(0);
                    ArrayList<Double> weights = new ArrayList<Double>(0);
                    ArrayList<Boolean> isMainAss = new ArrayList<Boolean>(0);
                    for (HashMap.Entry<CourseworkComponent, Double> assessmentResult : mark.getCourseWorkMarks().entrySet()){
                        CourseworkComponent key = assessmentResult.getKey();
                        if (key instanceof MainComponent) {
                            if ((!key.getComponentName().equals("Exam")) && ((MainComponent) key).getSubComponents().size() == 0) {
                                availableChoices.add(key.getComponentName());
                                weights.add((double)key.getComponentWeight());
                                isMainAss.add(true);
                            } else {
                                for (SubComponent subComponent : ((MainComponent) key).getSubComponents()) {
                                    availableChoices.add(key.getComponentName() + "-" + subComponent.getComponentName());
                                    weights.add((double)key.getComponentWeight() * (double)subComponent.getComponentWeight() / 100d);
                                    isMainAss.add(false);
                                }
                            }
                        }
                    }

                    for (int i = 0; i < availableChoices.size(); i++) {
                        System.out.println((i + 1) + ". " + availableChoices.get(i) + " Weight in Total: " + weights.get(i) + "%");
                    }
                    System.out.println((availableChoices.size() + 1) + ". Quit");

                    int choice;
                    System.out.println("Enter your choice");
                    choice = scanner.nextInt();
                    scanner.nextLine();

                    while (choice > (availableChoices.size() + 1) || choice < 0) {
                        System.out.println("Please enter choice between " + 0 + "~" + (availableChoices.size() + 1));
                        System.out.println("Enter your choice");
                        choice = scanner.nextInt();
                        scanner.nextLine();
                    }

                    if (choice == (availableChoices.size() + 1)) {
                        return ;
                    }

                    double assessmentMark;
                    System.out.println("Enter the mark for this assessment:");
                    assessmentMark = scanner.nextDouble();
                    scanner.nextLine();
                    while (assessmentMark > 100 || assessmentMark < 0) {
                        System.out.println("Please enter mark in range 0 ~ 100.");
                        assessmentMark = scanner.nextDouble();
                        scanner.nextLine();
                    }

                    if (isMainAss.get(choice - 1)) {
                        // This is a stand alone main assessment
                        mark.setMainCourseWorkMarks(availableChoices.get(choice - 1), assessmentMark);
                    }
                    else {
                        mark.setSubCourseWorkMarks(availableChoices.get(choice - 1).split("-")[1], assessmentMark);
                    }

                }

                else {
                    // The user want to enter exam mark.
                    double examMark;
                    System.out.println("Enter exam mark:");
                    examMark = scanner.nextDouble();
                    scanner.nextLine();
                    while (examMark > 100 || examMark < 0) {
                        System.out.println("Please enter mark in range 0 ~ 100.");
                        examMark = scanner.nextDouble();
                        scanner.nextLine();
                    }
                    mark.setMainCourseWorkMarks("Exam", examMark);
                }

                return;
            }
        }

        System.out.println("This student haven't registered " + courseID);

    }

    /**
     * Computes the sum of marks for a particular component of a particular course
     * @param thisCourseMark the array list of mark records belong to a particular course
     * @param thisComponentName the component name interested.
     * @return the sum of component marks
     */
    public double computeMark(ArrayList<Mark> thisCourseMark, String thisComponentName){
        double averageMark = 0;
        for (Mark mark : thisCourseMark) {
            HashMap<CourseworkComponent, Double> thisComponentMarks = mark.getCourseWorkMarks();
            for (HashMap.Entry<CourseworkComponent, Double> entry : thisComponentMarks.entrySet()) {
                CourseworkComponent key = entry.getKey();
                double value = entry.getValue();
                if (key.getComponentName().equals(thisComponentName)) {
                    averageMark += value;
                    break;
                }
            }
        }
        return averageMark;
    }

    /**
     * Computes the gpa gained for this course from the result of this course.
     * @param result result of this course
     * @return the grade (in A, B ... )
     */
    public double gpaCalculator(double result) {
        if (result > 85) {
            // A+, A
            return 5d;
        } else if (result > 80) {
            // A-
            return 4.5;
        } else if (result > 75) {
            // B+
            return  4d;
        } else if (result > 70) {
            // B
            return 3.5;
        } else if (result > 65) {
            // B-
            return  3d;
        } else if (result > 60) {
            // C+
            return 2.5d;
        } else if (result > 55) {
            // C
            return 2d;
        } else if (result > 50) {
            // D+
            return 1.5d;
        } else if (result > 45) {
            // D
            return 1d;
        } else {
            // F
            return 0d;
        }

    }

    /**
     * Get the instance of the MarkMgr class.
     * @return the singleton instance.
     */
    public static MarkMgr getInstance() {
        if (instance == null) {
            instance = new MarkMgr();
        }
        return instance;
    }
}
