package com.softeng306.Interfaces.Entity;

public interface IGroup {
    /**
     * Gets the name of this group.
     *
     * @return this group's name.
     */
    public String getGroupName();

    /**
     * Gets the current available vacancies for this group.
     *
     * @return this group's current available vacancy.
     */
    public int getAvailableVacancies();

    /**
     * Gets the total seats for this group.
     *
     * @return this group's total seats.
     */
    public int getTotalSeats();

    /**
     * Updates the available vacancies of this group after someone has registered this group.
     */
    public void enrolledIn();
}
