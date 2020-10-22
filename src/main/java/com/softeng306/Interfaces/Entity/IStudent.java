package com.softeng306.Interfaces.Entity;

public interface IStudent {
    /**
     * Gets the student's ID.
     * @return this student's ID.
     */
    public String getStudentID();

    /**
     * Gets the student's name.
     * @return this student's name.
     */
    public String getStudentName();

    /**
     * Gets the student's school.
     * @return this student's school.
     */
    public String getStudentSchool();

    /**
     * Gets the student's gender.
     * @return this student's gender.
     */
    public String getGender();

    /**
     * Gets the student's GPA.
     * @return this student's GPA.
     */
    public double getGPA();

    /**
     * Gets the student's year.
     * @return this student's year.
     */
    public int getStudentYear();

    /**
     * Sets the ID of this student.
     * @param studentID this student's ID.
     */
    public void setStudentID(String studentID);

    /**
     * Sets the school of this student.
     * @param studentSchool this student's school.
     */
    public void setStudentSchool(String studentSchool);

    /**
     * Sets the gender of this student.
     * @param gender this student's gender.
     */
    public void setGender(String gender);

    /**
     * Sets the GPA of this student.
     * @param GPA this student's GPA.
     */
    public void setGPA(double GPA);

    /**
     * Sets the year of this student.
     * @param studentYear this student's year.
     */
    public void  setStudentYear(int studentYear);
}
