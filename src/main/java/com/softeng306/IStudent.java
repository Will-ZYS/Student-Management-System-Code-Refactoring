package com.softeng306;

public interface IStudent {
    /**
     * Gets the student's ID.
     * @return this student's ID.
     */
    String getStudentID();

    /**
     * Gets the student's name.
     * @return this student's name.
     */
    String getStudentName();

    /**
     * Gets the student's school.
     * @return this student's school.
     */
    String getStudentSchool();

    /**
     * Gets the student's gender.
     * @return this student's gender.
     */
    String getGender();

    /**
     * Gets the student's GPA.
     * @return this student's GPA.
     */
    double getGPA();

    /**
     * Gets the student's year.
     * @return this student's year.
     */
    int getStudentYear();

    /**
     * Sets the ID of this student.
     * @param studentID this student's ID.
     */
    void setStudentID(String studentID);

    /**
     * Sets the school of this student.
     * @param studentSchool this student's school.
     */
    void setStudentSchool(String studentSchool);

    /**
     * Sets the gender of this student.
     * @param gender this student's gender.
     */
    void setGender(String gender);

    /**
     * Sets the GPA of this student.
     * @param GPA this student's GPA.
     */
    void setGPA(double GPA);

    /**
     * Sets the year of this student.
     * @param studentYear this student's year.
     */
    void  setStudentYear(int studentYear);
}
