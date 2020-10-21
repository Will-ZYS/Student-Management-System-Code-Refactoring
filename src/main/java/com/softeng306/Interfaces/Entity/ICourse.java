package com.softeng306.Interfaces.Entity;

import com.softeng306.Entity.MainComponent;
import com.softeng306.Entity.Professor;

import java.util.ArrayList;

public interface ICourse {
    /**
     * Gets the course's ID.
     * @return the ID of this course.
     */
    public String getCourseID();

    /**
     * Gets the course's name.
     * @return the name of this course.
     */
    public String getCourseName();

    /**
     * Gets the course's AU.
     * @return the AU of this course.
     */
    public int getAU();

    /**
     * Gets the course's professor in charge.
     * @return the professor in charge of this course.
     */
    public Professor getProfInCharge();

    /**
     * Gets the course's current vacancy.
     * @return the current vacancy of this course.
     */
    public int getVacancies();

    /**
     * Gets the course's total seats.
     * @return the total seats of this course.
     */
    public int getTotalSeats();

    /**
     * Gets the course's department.
     * @return the department of this course.
     */
    public String getCourseDepartment();

    /**
     * Gets the course's type.
     * @return the type of this course.
     */
    public String getCourseType();

    /**
     * Gets the course's weekly lecture hour.
     * @return the weekly lecture hour of this course.
     */
    public int getLecWeeklyHour();

    /**
     * Gets the course's weekly tutorial hour.
     * @return the weekly tutorial hour of this course.
     */
    public int getTutWeeklyHour();

    /**
     * Gets the course's weekly lab hour.
     * @return the weekly lab hour of this course.
     */
    public int getLabWeeklyHour();

    /**
     * Gets the course's lecture groups.
     * @return the lecture groups of this course.
     */
    public ArrayList<IGroup> getLectureGroups();

    /**
     * Gets the course's tutorial groups
     * @return the tutorial groups of this course
     */
    public ArrayList<IGroup> getTutorialGroups();

    /**
     * Gets the course's lab groups.
     * @return the lab groups of this course.
     */
    public ArrayList<IGroup> getLabGroups();

    /**
     * Gets the course's main assessment components.
     * @return the main assessment components of this course.
     */
    public ArrayList<MainComponent> getMainComponents();

    /**
     * Sets the course's current current vacancy.
     * @param vacancies this course's vacancy.
     */
    public void setVacancies(int vacancies);

    /**
     * Updates the available vacancies of this course after someone has registered this group.
     */
    public void enrolledIn();

    /**
     * Sets the tutorial groups of the lecture groups.
     * @param tutorialGroups this course's tutorial groups.
     */
    public void setTutorialGroups(ArrayList<IGroup> tutorialGroups);

    /**
     * Sets the lab groups of the lecture groups.
     * @param labGroups this course's lab groups.
     */
    public void setLabGroups(ArrayList<IGroup> labGroups);

    /**
     * Sets the main assessment of the lecture groups.
     * @param mainComponents this course's main assessment.
     */
    public void setMainComponents(ArrayList<MainComponent> mainComponents);

    /**
     * Sets the weekly hour of the tutorials.
     * @param tutWeeklyHour this course's weekly tutorial hour.
     */
    public void setTutWeeklyHour(int tutWeeklyHour);

    /**
     * Sets the weekly hour of the labs.
     * @param labWeeklyHour this course's weekly lab hour.
     */
    public void setLabWeeklyHour(int labWeeklyHour);
}
