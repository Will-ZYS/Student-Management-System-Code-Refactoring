package com.softeng306.Database;

import com.softeng306.Entity.CourseRegistration;
import com.softeng306.Interfaces.Database.ICourseFileMgr;
import com.softeng306.Interfaces.Database.ICourseRegistrationFileMgr;
import com.softeng306.Interfaces.Database.IStudentFileMgr;
import com.softeng306.Interfaces.Entity.ICourse;
import com.softeng306.Interfaces.Entity.ICourseRegistration;
import com.softeng306.Interfaces.Entity.IStudent;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CourseRegistrationFileMgr extends FILEMgrAbstract implements ICourseRegistrationFileMgr {
	private static CourseRegistrationFileMgr instance;
	private ICourseFileMgr courseFileMgr = CourseFileMgr.getInstance();
	private IStudentFileMgr studentFileMgr = StudentFileMgr.getInstance();

	/**
	 * The file name of courseRegistrationFile.csv.
	 */
	private String courseRegistrationFileName = "data/courseRegistrationFile.csv";

	/**
	 * The header of courseRegistrationFile.csv.
	 */
	private final String courseRegistration_HEADER = "studentID,courseID,lectureGroup,tutorialGroup,labGroup";

	/**
	 * The index of studentID in courseRegistrationFile.csv.
	 */
	private final int studentIdInRegistrationIndex = 0;

	/**
	 * The index of courseID in courseRegistrationFile.csv.
	 */
	private final int courseIdInRegistrationIndex = 1;

	/**
	 * The index of lectureGroup in courseRegistrationFile.csv.
	 */
	private final int lectureGroupInRegistrationIndex = 2;

	/**
	 * The index of tutorialGroup in courseRegistrationFile.csv.
	 */
	private final int tutorialGroupInRegistrationIndex = 3;

	/**
	 * The index of labGroup in courseRegistrationFile.csv.
	 */
	private final int labGroupInRegistrationIndex = 4;

	/**
	 * Writes a new course registration record into the file.
	 *
	 * @param courseRegistration courseRegistration to be added into file
	 */
	public void writeCourseRegistrationIntoFile(ICourseRegistration courseRegistration) {
		FileWriter fileWriter = null;
		try {
			fileWriter = initializeCSV(courseRegistrationFileName, courseRegistration_HEADER);

			fileWriter.append(courseRegistration.getStudent().getStudentID());
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(courseRegistration.getCourse().getCourseID());
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(courseRegistration.getLectureGroup());
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(courseRegistration.getTutorialGroup());
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(courseRegistration.getLabGroup());
			fileWriter.append(NEW_LINE_SEPARATOR);
		} catch (Exception e) {
			System.out.println("Error in adding a course registration to the file.");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error occurs when flushing or closing the file.");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Load all the course registration records from file into the system.
	 *
	 * @return a list of all the course registration records.
	 */
	public List<ICourseRegistration> loadCourseRegistration() {
		BufferedReader fileReader = null;
		ArrayList<ICourseRegistration> courseRegistrations = new ArrayList<>(0);
		try {
			String line;
			IStudent currentStudent = null;
			ICourse currentCourse = null;
			List<IStudent> students = studentFileMgr.loadStudents();

			fileReader = new BufferedReader(new FileReader(courseRegistrationFileName));
			fileReader.readLine();//read the header to skip it

			while ((line = fileReader.readLine()) != null) {
				String[] tokens = line.split(COMMA_DELIMITER);
				if (tokens.length > 0) {
					String studentID = tokens[studentIdInRegistrationIndex];

					for (IStudent student : students) {
						if (student.getStudentID().equals(studentID)) {
							currentStudent = student;
							break;
						}
					}
					String courseID = tokens[courseIdInRegistrationIndex];
					List<ICourse> courses = courseFileMgr.loadCourses();
					for (ICourse course : courses) {
						if (course.getCourseID().equals(courseID)) {
							currentCourse = course;
							break;
						}
					}
					courseRegistrations.add(new CourseRegistration(currentStudent, currentCourse, tokens[lectureGroupInRegistrationIndex], tokens[tutorialGroupInRegistrationIndex], tokens[labGroupInRegistrationIndex]));
				}
			}
		} catch (Exception e) {
			System.out.println("Error occurs when loading course registrations.");
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				System.out.println("Error occurs when closing the fileReader.");
				e.printStackTrace();
			}
		}
		return courseRegistrations;
	}

	/**
	 * Get the instance of the CourseRegistrationFileMgr class.
	 * @return the singleton instance.
	 */

	public static CourseRegistrationFileMgr getInstance() {
		if (instance == null) {
			instance = new CourseRegistrationFileMgr();
		}
		return instance;
	}

	public void setCourseRegistrationFileName(String courseRegistrationFileName) {
		this.courseRegistrationFileName = courseRegistrationFileName;
	}


}
