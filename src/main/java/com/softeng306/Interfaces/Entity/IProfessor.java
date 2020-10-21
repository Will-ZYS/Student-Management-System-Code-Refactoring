package com.softeng306.Interfaces.Entity;

public interface IProfessor {
	/**
	 * Gets this professor's ID
	 * @return the ID of this professor
	 */

	String getProfID();

	/**
	 * Gets this professor's name
	 * @return the name of this professor
	 */
	String getProfName();

	/**
	 * Gets this professor's department
	 * @return the department of this professor
	 */
	String getProfDepartment();

	/**
	 * Sets the department of the professor
	 * @param profDepartment this professor's department
	 */
	void setProfDepartment(String profDepartment);
}
