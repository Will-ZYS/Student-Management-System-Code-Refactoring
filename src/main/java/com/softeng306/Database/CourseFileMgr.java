package com.softeng306.Database;

import com.softeng306.Entity.Course;
import com.softeng306.Entity.Group;
import com.softeng306.Entity.MainComponent;
import com.softeng306.Entity.SubComponent;
import com.softeng306.Interfaces.Database.ICourseFileMgr;
import com.softeng306.Interfaces.Database.IProfessorFileMgr;
import com.softeng306.Interfaces.Entity.ICourse;
import com.softeng306.Interfaces.Entity.ICourseworkComponent;
import com.softeng306.Interfaces.Entity.IGroup;
import com.softeng306.Interfaces.Entity.IProfessor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CourseFileMgr extends FILEMgrAbstract implements ICourseFileMgr {
	private static CourseFileMgr instance;
	private IProfessorFileMgr professorFileMgr = ProfessorFileMgr.getInstance();

	/**
	 * The file name of courseFile.csv.
	 */
	private final String courseFileName = "data/courseFile.csv";

	/**
	 * The header of courseFile.csv.
	 */
	private final String course_HEADER = "courseID,courseName,profInCharge,vacancies,totalSeats,lectureGroups,TutorialGroups,LabGroups,MainComponents,AU,courseDepartment,courseType,lecHr,tutHr,labHr";

	/**
	 * The index of the course ID in courseFile.csv.
	 */
	private final int courseIdIndex = 0;

	/**
	 * The index of the course name in courseFile.csv.
	 */
	private final int courseNameIndex = 1;

	/**
	 * The index of the professor in charge of this course in courseFile.csv.
	 */
	private final int profInChargeIndex = 2;
	/**
	 * The index of course vacancies in courseFile.csv.
	 */
	private final int vacanciesIndex = 3;

	/**
	 * The index of course total seats in courseFile.csv.
	 */
	private final int totalSeatsIndex = 4;

	/**
	 * The index of course lecture groups in courseFile.csv.
	 */
	private final int lectureGroupsIndex = 5;

	/**
	 * The index of course tutorial groups in courseFile.csv.
	 */
	private final int tutorialGroupIndex = 6;

	/**
	 * The index of course lab group in courseFile.csv.
	 */
	private final int labGroupIndex = 7;

	/**
	 * The index of course main components in courseFile.csv.
	 */
	private final int mainComponentsIndex = 8;

	/**
	 * The index of course AU in courseFile.csv.
	 */
	private final int AUIndex = 9;

	/**
	 * The index of course department in courseFile.csv.
	 */
	private final int courseDepartmentIndex = 10;

	/**
	 * The index of course type in courseFile.csv.
	 */
	private final int courseTypeIndex = 11;

	/**
	 * The index of course weekly lecture hour in courseFile.csv.
	 */
	private final int lecHrIndex = 12;

	/**
	 * The index of course weekly tutorial hour in courseFile.csv.
	 */
	private final int tutHrIndex = 13;

	/**
	 * The index of course weekly lab hour in courseFile.csv.
	 */
	private final int labHrIndex = 14;

	/**
	 * Write a new course information into the file.
	 *
	 * @param course a course to be added into file
	 */
	public void writeCourseIntoFile(ICourse course) {
		File file;
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(courseFileName, true);
			//initialize file header if have not done so
			file = new File(courseFileName);
			if (file.length() == 0) {
				fileWriter.append(course_HEADER);
				fileWriter.append(NEW_LINE_SEPARATOR);
			}

			fileWriter.append(course.getCourseID());
			fileWriter.append(COMMA_DELIMITER);

			fileWriter.append(course.getCourseName());
			fileWriter.append(COMMA_DELIMITER);

			fileWriter.append(course.getProfInCharge().getProfID());
			fileWriter.append(COMMA_DELIMITER);

			fileWriter.append(String.valueOf(course.getVacancies()));
			fileWriter.append(COMMA_DELIMITER);

			fileWriter.append(String.valueOf(course.getTotalSeats()));
			fileWriter.append(COMMA_DELIMITER);

			ArrayList<IGroup> lectureGroups = course.getLectureGroups();
			if (lectureGroups.size() != 0) {
				int index = 0;
				for (IGroup lectureGroup : lectureGroups) {
					fileWriter.append(lectureGroup.getGroupName());
					fileWriter.append(EQUAL_SIGN);
					fileWriter.append(String.valueOf(lectureGroup.getAvailableVacancies()));
					fileWriter.append(EQUAL_SIGN);
					fileWriter.append(String.valueOf(lectureGroup.getTotalSeats()));
					index++;
					if (index != lectureGroups.size()) {
						fileWriter.append(LINE_DELIMITER);
					}
				}
			} else {
				fileWriter.append("NULL");
			}
			fileWriter.append(COMMA_DELIMITER);

			ArrayList<IGroup> tutorialGroups = course.getTutorialGroups();
			if (tutorialGroups.size() != 0) {
				int index = 0;
				for (IGroup tutorialGroup : tutorialGroups) {
					fileWriter.append(tutorialGroup.getGroupName());
					fileWriter.append(EQUAL_SIGN);
					fileWriter.append(String.valueOf(tutorialGroup.getAvailableVacancies()));
					fileWriter.append(EQUAL_SIGN);
					fileWriter.append(String.valueOf(tutorialGroup.getTotalSeats()));
					index++;
					if (index != tutorialGroups.size()) {
						fileWriter.append(LINE_DELIMITER);
					}
				}
			} else {
				fileWriter.append("NULL");
			}
			fileWriter.append(COMMA_DELIMITER);

			ArrayList<IGroup> labGroups = course.getLabGroups();
			if (labGroups.size() != 0) {
				int index = 0;
				for (IGroup labGroup : labGroups) {
					fileWriter.append(labGroup.getGroupName());
					fileWriter.append(EQUAL_SIGN);
					fileWriter.append(String.valueOf(labGroup.getAvailableVacancies()));
					fileWriter.append(EQUAL_SIGN);
					fileWriter.append(String.valueOf(labGroup.getTotalSeats()));
					index++;
					if (index != labGroups.size()) {
						fileWriter.append(LINE_DELIMITER);
					}
				}
			} else {
				fileWriter.append("NULL");
			}
			fileWriter.append(COMMA_DELIMITER);

			ArrayList<ICourseworkComponent> mainComponents = course.getMainComponents();
			if (mainComponents.size() != 0) {
				int index = 0;
				for (ICourseworkComponent mainComponent : mainComponents) {
					fileWriter.append(mainComponent.getComponentName());
					fileWriter.append(EQUAL_SIGN);
					fileWriter.append(String.valueOf(mainComponent.getComponentWeight()));
					fileWriter.append(EQUAL_SIGN);
					ArrayList<ICourseworkComponent> subComponents = mainComponent.getSubComponents();
					int inner_index = 0;
					for (ICourseworkComponent subComponent : subComponents) {
						fileWriter.append(subComponent.getComponentName());
						fileWriter.append(HYPHEN);
						fileWriter.append(String.valueOf(subComponent.getComponentWeight()));
						inner_index++;
						if (inner_index != subComponents.size()) {
							fileWriter.append(SLASH);
						}
					}
					index++;
					if (index != mainComponents.size()) {
						fileWriter.append(LINE_DELIMITER);
					}
				}
			} else {
				fileWriter.append("NULL");
			}
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(course.getAU()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(course.getCourseDepartment());
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(course.getCourseType());
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(course.getLecWeeklyHour()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(course.getTutWeeklyHour()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(course.getLabWeeklyHour()));
			fileWriter.append(NEW_LINE_SEPARATOR);
		} catch (Exception e) {
			System.out.println("Error in adding a course to the file.");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error occurs occurs when flushing or closing the file.");
				e.printStackTrace();
			}
		}

	}

	/**
	 * Load all the courses' information from file into the system.
	 *
	 * @return an array list of all the courses.
	 */
	public ArrayList<ICourse> loadCourses() {
		ArrayList<ICourse> courses = new ArrayList<>(0);
		BufferedReader fileReader = null;
		try {
			String line;
			int thisProfessor = 0;
			IProfessor currentProfessor = null;
			ArrayList<IProfessor> professors = professorFileMgr.loadProfessors();
			fileReader = new BufferedReader(new FileReader(courseFileName));
			fileReader.readLine();//read the header to skip it
			while ((line = fileReader.readLine()) != null) {
				String[] tokens = line.split(COMMA_DELIMITER);
				if (tokens.length > 0) {
					String courseID = tokens[courseIdIndex];
					String courseName = tokens[courseNameIndex];
					String profInCharge = tokens[profInChargeIndex];
					for (IProfessor professor : professors) {
						if (professor.getProfID().equals(profInCharge)) {
							currentProfessor = professor;
							break;
						}
					}
					int vacancies = Integer.parseInt(tokens[vacanciesIndex]);
					int totalSeats = Integer.parseInt(tokens[totalSeatsIndex]);
					int AU = Integer.parseInt(tokens[AUIndex]);
					String courseDepartment = tokens[courseDepartmentIndex];
					String courseType = tokens[courseTypeIndex];
					int lecWeeklyHr = Integer.parseInt(tokens[lecHrIndex]);
					int tutWeeklyHr = Integer.parseInt(tokens[tutHrIndex]);
					int labWeeklyHr = Integer.parseInt(tokens[labHrIndex]);

					String lectureGroupsString = tokens[lectureGroupsIndex];
					ArrayList<IGroup> lectureGroups = new ArrayList<>(0);
					String[] eachLectureGroupsString = lectureGroupsString.split(Pattern.quote(LINE_DELIMITER));

					for (int i = 0; i < eachLectureGroupsString.length; i++) {
						String[] thisLectureGroup = eachLectureGroupsString[i].split(EQUAL_SIGN);
						lectureGroups.add(new Group(thisLectureGroup[0], Integer.parseInt(thisLectureGroup[1]), Integer.parseInt(thisLectureGroup[2])));
					}

					ICourse course = new Course(courseID, courseName, currentProfessor, vacancies, totalSeats, lectureGroups, AU, courseDepartment, courseType, lecWeeklyHr);

					String tutorialGroupsString = tokens[tutorialGroupIndex];
					ArrayList<IGroup> tutorialGroups = new ArrayList<>(0);

					if (!tutorialGroupsString.equals("NULL")) {
						String[] eachTutorialGroupsString = tutorialGroupsString.split(Pattern.quote(LINE_DELIMITER));
						for (int i = 0; i < eachTutorialGroupsString.length; i++) {
							String[] thisTutorialGroup = eachTutorialGroupsString[i].split(EQUAL_SIGN);
							tutorialGroups.add(new Group(thisTutorialGroup[0], Integer.parseInt(thisTutorialGroup[1]), Integer.parseInt(thisTutorialGroup[2])));
						}
					}
					course.setTutorialGroups(tutorialGroups);
					course.setTutWeeklyHour(tutWeeklyHr);

					String labGroupsString = tokens[labGroupIndex];
					ArrayList<IGroup> labGroups = new ArrayList<>(0);
					if (!labGroupsString.equals("NULL")) {
						String[] eachLabGroupString = labGroupsString.split(Pattern.quote(LINE_DELIMITER));
						for (int i = 0; i < eachLabGroupString.length; i++) {
							String[] thisLabGroup = eachLabGroupString[i].split(EQUAL_SIGN);
							labGroups.add(new Group(thisLabGroup[0], Integer.parseInt(thisLabGroup[1]), Integer.parseInt(thisLabGroup[2])));
						}
					}
					course.setLabGroups(labGroups);
					course.setLabWeeklyHour(labWeeklyHr);

					String mainComponentsString = tokens[mainComponentsIndex];
					ArrayList<ICourseworkComponent> mainComponents = new ArrayList<>(0);
					if (!mainComponentsString.equals("NULL")) {
						String[] eachMainComponentsString = mainComponentsString.split(Pattern.quote(LINE_DELIMITER));
						for (int i = 0; i < eachMainComponentsString.length; i++) {
							String[] thisMainComponent = eachMainComponentsString[i].split(EQUAL_SIGN);
							ArrayList<ICourseworkComponent> subComponents = new ArrayList<>(0);
							if (thisMainComponent.length > 2) {
								String[] subComponentsString = thisMainComponent[2].split(SLASH);
								for (int j = 0; j < subComponentsString.length; j++) {
									String[] thisSubComponent = subComponentsString[j].split(HYPHEN);
									subComponents.add(new SubComponent(thisSubComponent[0], Integer.parseInt(thisSubComponent[1])));
								}
							}

							mainComponents.add(new MainComponent(thisMainComponent[0], Integer.parseInt(thisMainComponent[1]), subComponents));
						}
					}
					course.setMainComponents(mainComponents);
					course.setVacancies(vacancies);
					courses.add(course);
				}
			}
		} catch (Exception e) {
			System.out.println("Error happens when loading courses.");
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				System.out.println("Error happens when closing the fileReader.");
				e.printStackTrace();
			}
		}
		return courses;
	}

	/**
	 * Backs up all the changes of courses made into the file.
	 *
	 * @param courses courses to be backed up
	 */

	public void backUpCourse(List<ICourse> courses) {
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(courseFileName);


			fileWriter.append(course_HEADER);
			fileWriter.append(NEW_LINE_SEPARATOR);

			for (ICourse course : courses) {
				fileWriter.append(course.getCourseID());
				fileWriter.append(COMMA_DELIMITER);

				fileWriter.append(course.getCourseName());
				fileWriter.append(COMMA_DELIMITER);

				fileWriter.append(course.getProfInCharge().getProfID());
				fileWriter.append(COMMA_DELIMITER);

				fileWriter.append(String.valueOf(course.getVacancies()));
				fileWriter.append(COMMA_DELIMITER);

				fileWriter.append(String.valueOf(course.getTotalSeats()));
				fileWriter.append(COMMA_DELIMITER);

				ArrayList<IGroup> lectureGroups = course.getLectureGroups();

				if (lectureGroups.size() != 0) {
					int index = 0;
					for (IGroup lectureGroup : lectureGroups) {
						fileWriter.append(lectureGroup.getGroupName());
						fileWriter.append(EQUAL_SIGN);
						fileWriter.append(String.valueOf(lectureGroup.getAvailableVacancies()));
						fileWriter.append(EQUAL_SIGN);
						fileWriter.append(String.valueOf(lectureGroup.getTotalSeats()));
						index++;
						if (index != lectureGroups.size()) {
							fileWriter.append(LINE_DELIMITER);
						}
					}
				} else {
					fileWriter.append("NULL");
				}

				fileWriter.append(COMMA_DELIMITER);

				ArrayList<IGroup> tutorialGroups = course.getTutorialGroups();
				if (tutorialGroups.size() != 0) {
					int index = 0;
					for (IGroup tutorialGroup : tutorialGroups) {
						fileWriter.append(tutorialGroup.getGroupName());
						fileWriter.append(EQUAL_SIGN);
						fileWriter.append(String.valueOf(tutorialGroup.getAvailableVacancies()));
						fileWriter.append(EQUAL_SIGN);
						fileWriter.append(String.valueOf(tutorialGroup.getTotalSeats()));
						index++;
						if (index != tutorialGroups.size()) {
							fileWriter.append(LINE_DELIMITER);
						}
					}
				} else {
					fileWriter.append("NULL");
				}
				fileWriter.append(COMMA_DELIMITER);

				ArrayList<IGroup> labGroups = course.getLabGroups();
				if (labGroups.size() != 0) {
					int index = 0;
					for (IGroup labGroup : labGroups) {
						fileWriter.append(labGroup.getGroupName());
						fileWriter.append(EQUAL_SIGN);
						fileWriter.append(String.valueOf(labGroup.getAvailableVacancies()));
						fileWriter.append(EQUAL_SIGN);
						fileWriter.append(String.valueOf(labGroup.getTotalSeats()));
						index++;
						if (index != labGroups.size()) {
							fileWriter.append(LINE_DELIMITER);
						}
					}
				} else {
					fileWriter.append("NULL");
				}

				fileWriter.append(COMMA_DELIMITER);

				ArrayList<ICourseworkComponent> mainComponents = course.getMainComponents();
				if (mainComponents.size() != 0) {
					int index = 0;
					for (ICourseworkComponent mainComponent : mainComponents) {
						fileWriter.append(mainComponent.getComponentName());
						fileWriter.append(EQUAL_SIGN);
						fileWriter.append(String.valueOf(mainComponent.getComponentWeight()));
						fileWriter.append(EQUAL_SIGN);
						ArrayList<ICourseworkComponent> subComponents = mainComponent.getSubComponents();
						int inner_index = 0;
						for (ICourseworkComponent subComponent : subComponents) {
							fileWriter.append(subComponent.getComponentName());
							fileWriter.append(HYPHEN);
							fileWriter.append(String.valueOf(subComponent.getComponentWeight()));
							inner_index++;
							if (inner_index != subComponents.size()) {
								fileWriter.append(SLASH);
							}
						}
						index++;
						if (index != mainComponents.size()) {
							fileWriter.append(LINE_DELIMITER);
						}
					}
				} else {
					fileWriter.append("NULL");
				}
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(course.getAU()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(course.getCourseDepartment());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(course.getCourseType());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(course.getLecWeeklyHour()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(course.getTutWeeklyHour()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(course.getLabWeeklyHour()));
				fileWriter.append(NEW_LINE_SEPARATOR);

			}

		} catch (Exception e) {
			System.out.println("Error in backing up courses.");
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
	 * Get the instance of the CourseFileMgr class.
	 * @return the singleton instance.
	 */

	public static CourseFileMgr getInstance() {
		if (instance == null) {
			instance = new CourseFileMgr();
		}
		return instance;
	}
}
