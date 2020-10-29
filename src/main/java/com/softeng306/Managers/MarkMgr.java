package com.softeng306.Managers;

import com.softeng306.Database.Database;

import com.softeng306.Database.MarkFileMgr;
import com.softeng306.Entity.MainComponent;
import com.softeng306.Entity.Mark;
import com.softeng306.Interfaces.Database.IDatabase;
import com.softeng306.Interfaces.Entity.ICourse;
import com.softeng306.Interfaces.Entity.IStudent;
import com.softeng306.Interfaces.Managers.IMarkMgr;
import com.softeng306.Interfaces.Entity.ICourseworkComponent;
import com.softeng306.Interfaces.Entity.IMark;
import com.softeng306.Interfaces.Managers.Validation.ICourseValidationMgr;
import com.softeng306.Interfaces.Managers.Validation.IStudentValidationMgr;
import com.softeng306.Managers.Validation.CourseValidationMgr;
import com.softeng306.Managers.Validation.StudentValidationMgr;
import com.softeng306.Utils.Scanner;

import java.util.*;

/**
 * Manages all the mark related operations.
 */

public class MarkMgr implements IMarkMgr {
    private Scanner scanner = Scanner.getInstance();
    private static MarkMgr instance = null;

    /**
     * Initializes marks for a student when he/she just registered a course.
     * @param student the student this mark record belongs to.
     * @param course the course this mark record about.
     * @return the new added mark.
     */
    public Mark initializeMark(IStudent student, ICourse course) {
        Mark mark = new Mark(student, course);
        MarkFileMgr.getInstance().updateStudentMarks(mark);
        return mark;
    }

    /**
     * Sets the coursework mark for the mark record.
     * @param isExam whether this coursework component refers to "Exam"
     */
    public void setCourseWorkMark(boolean isExam) {
        ICourseValidationMgr courseValidationMgr = CourseValidationMgr.getInstance();
        IStudentValidationMgr studentValidationMgr = StudentValidationMgr.getInstance();
        IDatabase database = Database.getInstance();

        System.out.println("enterCourseWorkMark is called");

        String studentID = studentValidationMgr.checkStudentExists().getStudentID();
        String courseID = courseValidationMgr.checkCourseExists().getCourseID();


        for(IMark mark: database.getMarks()) {
            if (mark.getCourse().getCourseID().equals(courseID) && mark.getStudent().getStudentID().equals(studentID)) {
                //put the set mark function here
                if (!isExam) {
                    System.out.println("Here are the choices you can have: ");
                    List<String> availableChoices = new ArrayList<>(0);
                    List<Double> weights = new ArrayList<>(0);
                    List<Boolean> isMainAss = new ArrayList<>(0);
                    for (Map.Entry<ICourseworkComponent, Double> assessmentResult : mark.getCourseWorkMarks().entrySet()){
                        ICourseworkComponent key = assessmentResult.getKey();
                        if (key instanceof MainComponent) {
                            if ((!key.getComponentName().equals("Exam")) && key.getSubComponents().size() == 0) {
                                availableChoices.add(key.getComponentName());
                                weights.add((double)key.getComponentWeight());
                                isMainAss.add(true);
                            } else {
                                for (ICourseworkComponent subComponent : key.getSubComponents()) {
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
     * @param thisCourseMark the list of mark records belong to a particular course
     * @param thisComponentName the component name interested.
     * @return the sum of component marks
     */
    public double computeMark(List<IMark> thisCourseMark, String thisComponentName){

        double averageMark = 0;
        for (IMark mark : thisCourseMark) {
            Map<ICourseworkComponent, Double> thisComponentMarks = mark.getCourseWorkMarks();
            for (Map.Entry<ICourseworkComponent, Double> entry : thisComponentMarks.entrySet()) {
                ICourseworkComponent key = entry.getKey();
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
