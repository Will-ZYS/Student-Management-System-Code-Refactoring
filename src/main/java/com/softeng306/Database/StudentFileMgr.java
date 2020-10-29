package com.softeng306.Database;

import com.softeng306.Entity.Student;
import com.softeng306.Interfaces.Database.IStudentFileMgr;
import com.softeng306.Interfaces.Entity.IStudent;
import com.softeng306.Interfaces.Managers.IStudentMgr;
import com.softeng306.Managers.StudentMgr;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StudentFileMgr extends FILEMgrAbstract implements IStudentFileMgr {
	private static StudentFileMgr instance = null;
	/**
	 * The file name of studentFile.csv.
	 */
	private String studentFileName = "data/studentFile.csv";

	/**
	 * The header of studentFile.csv.
	 */
	private final String studentHeader = "studentID,studentName,studentSchool,studentGender,studentGPA,studentYear";

	/**
	 * The index of the student ID in studentFile.csv.
	 */
	private final int studentIdIndex = 0;

	/**
	 * The index of the student name in studentFile.csv.
	 */
	private final int studentNameIndex = 1;

	/**
	 * The index of the student school in studentFile.csv.
	 */
	private final int studentSchoolIndex = 2;

	/**
	 * The index of the student gender in studentFile.csv.
	 */
	private final int studentGenderIndex = 3;

	/**
	 * The index of the student GPA in studentFile.csv.
	 */
	private final int studentGPAIndex = 4;

	/**
	 * The index of the student year in studentFile.csv.
	 */
	private final int studentYearIndex = 5;

	/**
	 * Write a new student information into the file.
	 *
	 * @param student a student to be added into the file
	 */
	public void writeStudentsIntoFile(IStudent student) {
		FileWriter fileWriter = null;
		try {
			fileWriter = initializeCSV(studentFileName, studentHeader);

			writeToFile(fileWriter,student.getStudentID(),COMMA_DELIMITER);
			writeToFile(fileWriter,student.getStudentName(),COMMA_DELIMITER);
			writeToFile(fileWriter,student.getStudentSchool(),COMMA_DELIMITER);
			writeToFile(fileWriter,student.getGender(),COMMA_DELIMITER);
			writeToFile(fileWriter,String.valueOf(student.getGPA()),COMMA_DELIMITER);
			writeToFile(fileWriter,String.valueOf(student.getStudentYear()),NEW_LINE_SEPARATOR);

		} catch (Exception e) {
			System.out.println("Error in adding a student to the file.");
			e.printStackTrace();
		} finally {
			try {
				Objects.requireNonNull(fileWriter).flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error in flushing or closing the file.");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Load all the students' information from file into the system.
	 *
	 * @return a list of all the students.
	 */
	public List<IStudent> loadStudents() {
		BufferedReader fileReader = null;
		List<IStudent> students = new ArrayList<>(0);
		IStudentMgr studentMgr = StudentMgr.getInstance();
		try {
			String line;
			fileReader = new BufferedReader(new FileReader(studentFileName));
			fileReader.readLine();//read the header to skip it
			int recentStudentID = 0;
			while ((line = fileReader.readLine()) != null) {
				String[] tokens = line.split(COMMA_DELIMITER);
				if (tokens.length > 0) {
					recentStudentID = Math.max(recentStudentID, Integer.parseInt(tokens[studentIdIndex].substring(1, 8)));
					IStudent student = new Student(tokens[studentIdIndex], tokens[studentNameIndex]);
					student.setStudentSchool(tokens[studentSchoolIndex]);
					student.setGender(tokens[studentGenderIndex]);
					student.setGPA(Double.parseDouble(tokens[studentGPAIndex]));
					student.setStudentYear(Integer.parseInt(tokens[studentYearIndex]));
					students.add(student);
				}
			}
			// Set the recent student ID, let the newly added student have the ID onwards.
			// If there is no student in DB, set recentStudentID to 1800000 (2018 into Uni)

			studentMgr.setIdNumber(recentStudentID > 0 ? recentStudentID : 1800000);
		} catch (Exception e) {
			System.out.println("Error occurs when loading students.");
			e.printStackTrace();
		} finally {
			try {
				Objects.requireNonNull(fileReader).close();
			} catch (IOException e) {
				System.out.println("Error occurs when closing the fileReader.");
				e.printStackTrace();
			}
		}
		return students;
	}

	/**
	 * Get the instance of the StudentFileMgr class.
	 * @return the singleton instance.
	 */
	public static StudentFileMgr getInstance() {
		if (instance == null) {
			instance = new StudentFileMgr();
		}
		return instance;
	}

	/**
	 * Method used for testing purposes
	 * @param studentFileName
	 */
	public void setStudentFileName(String studentFileName) {
		this.studentFileName = studentFileName;
	}


}
