package com.softeng306.Interfaces.Entity;

import java.util.List;

public interface ICourse {
    /**
     * Gets the course's ID.
     * @return the ID of this course.
     */
    String getCourseID();

    /**
     * Gets the course's name.
     * @return the name of this course.
     */
    String getCourseName();

    /**
     * Gets the course's AU.
     * @return the AU of this course.
     */
    int getAcademicUnit();

    /**
     * Gets the course's professor in charge.
     * @return the professor in charge of this course.
     */
    IProfessor getProfInCharge();

    /**
     * Gets the course's current vacancy.
     * @return the current vacancy of this course.
     */
    int getVacancies();

    /**
     * Gets the course's total seats.
     * @return the total seats of this course.
     */
    int getTotalSeats();

    /**
     * Gets the course's department.
     * @return the department of this course.
     */
    String getCourseDepartment();

    /**
     * Gets the course's type.
     * @return the type of this course.
     */
    String getCourseType();

    /**
     * Gets the course's weekly lecture hour.
     * @return the weekly lecture hour of this course.
     */
    int getLecWeeklyHour();

    /**
     * Gets the course's weekly tutorial hour.
     * @return the weekly tutorial hour of this course.
     */
    int getTutWeeklyHour();

    /**
     * Gets the course's weekly lab hour.
     * @return the weekly lab hour of this course.
     */
    int getLabWeeklyHour();

    /**
     * Gets the course's lecture groups.
     * @return the lecture groups of this course.
     */
    List<IGroup> getLectureGroups();

    /**
     * Gets the course's tutorial groups
     * @return the tutorial groups of this course
     */
    List<IGroup> getTutorialGroups();

    /**
     * Gets the course's lab groups.
     * @return the lab groups of this course.
     */
    List<IGroup> getLabGroups();

    /**
     * Gets the course's main assessment components.
     * @return the main assessment components of this course.
     */
    List<ICourseworkComponent> getMainComponents();

    /**
     * Sets the course's current current vacancy.
     * @param vacancies this course's vacancy.
     */
    void setVacancies(int vacancies);

    /**
     * Updates the available vacancies of this course after someone has registered this group.
     */
    void decrementCourseVacancy();

    /**
     * Sets the tutorial groups of the lecture groups.
     * @param tutorialGroups this course's tutorial groups.
     */
    void setTutorialGroups(List<IGroup> tutorialGroups);

    /**
     * Sets the lab groups of the lecture groups.
     * @param labGroups this course's lab groups.
     */
    void setLabGroups(List<IGroup> labGroups);

    /**
     * Sets the main assessment of the lecture groups.
     * @param mainComponents this course's main assessment.
     */
    void setMainComponents(List<ICourseworkComponent> mainComponents);

    /**
     * Sets the weekly hour of the tutorials.
     * @param tutWeeklyHour this course's weekly tutorial hour.
     */
    void setTutWeeklyHour(int tutWeeklyHour);

    /**
     * Sets the weekly hour of the labs.
     * @param labWeeklyHour this course's weekly lab hour.
     */
    void setLabWeeklyHour(int labWeeklyHour);
}
