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
	private String courseFileName = "data/courseFile.csv";

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

			writeCourseToCSV(fileWriter, course);

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
					loadGroup(lectureGroupsString, lectureGroups);

					ICourse course = new Course(courseID, courseName, currentProfessor, vacancies, totalSeats, lectureGroups, AU, courseDepartment, courseType, lecWeeklyHr);

					String tutorialGroupsString = tokens[tutorialGroupIndex];
					ArrayList<IGroup> tutorialGroups = new ArrayList<>(0);

					if (!tutorialGroupsString.equals("NULL")) {
						loadGroup(tutorialGroupsString, tutorialGroups);
					}
					course.setTutorialGroups(tutorialGroups);
					course.setTutWeeklyHour(tutWeeklyHr);

					String labGroupsString = tokens[labGroupIndex];
					ArrayList<IGroup> labGroups = new ArrayList<>(0);
					if (!labGroupsString.equals("NULL")) {
						loadGroup(labGroupsString, labGroups);
					}
					course.setLabGroups(labGroups);
					course.setLabWeeklyHour(labWeeklyHr);

					String mainComponentsString = tokens[mainComponentsIndex];
					ArrayList<ICourseworkComponent> mainComponents = new ArrayList<>(0);
					loadComponents(mainComponentsString, mainComponents);
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
	 * Loads MainComponents and SubComponents objects from the CSV file
	 * @param mainComponentsString string containing Components
	 * @param mainComponents List that we add to
	 */
	private void loadComponents(String mainComponentsString, List<ICourseworkComponent> mainComponents) {
		if (!mainComponentsString.equals("NULL")) {
			String[] eachMainComponentsString = mainComponentsString.split(Pattern.quote(LINE_DELIMITER));
			for (String mainComponent : eachMainComponentsString) {
				String[] thisMainComponent = mainComponent.split(EQUAL_SIGN);
				List<ICourseworkComponent> subComponents = new ArrayList<>(0);
				if (thisMainComponent.length > 2) {
					String[] subComponentsString = thisMainComponent[2].split(SLASH);
					for (String subComponent : subComponentsString) {
						String[] thisSubComponent = subComponent.split(HYPHEN);
						subComponents.add(new SubComponent(thisSubComponent[0], Integer.parseInt(thisSubComponent[1])));
					}
				}
				mainComponents.add(new MainComponent(thisMainComponent[0], Integer.parseInt(thisMainComponent[1]), subComponents));
			}
		}
	}

	/**
	 * Loads group objects from the CSV file
	 * @param groupsString the string containing all groups
	 * @param groups list of groups where we add groups to
	 */
	private void loadGroup(String groupsString, List<IGroup> groups) {
		String[] eachGroupsString = groupsString.split(Pattern.quote(LINE_DELIMITER));
		for (String group: eachGroupsString) {
			String[] thisLectureGroup = group.split(EQUAL_SIGN);
			groups.add(new Group(thisLectureGroup[0], Integer.parseInt(thisLectureGroup[1]), Integer.parseInt(thisLectureGroup[2])));
		}
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
				writeCourseToCSV(fileWriter, course);
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
	 * Writes course details to the CSV file
	 * @param fileWriter the fileWriter object
	 * @param course the course to be written
	 * @throws IOException
	 */
	private void writeCourseToCSV(FileWriter fileWriter, ICourse course) throws IOException{
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

		List<IGroup> lectureGroups = course.getLectureGroups();
		writeGroupToCSV(fileWriter, lectureGroups);
		fileWriter.append(COMMA_DELIMITER);

		List<IGroup> tutorialGroups = course.getTutorialGroups();
		writeGroupToCSV(fileWriter, tutorialGroups);
		fileWriter.append(COMMA_DELIMITER);

		List<IGroup> labGroups = course.getLabGroups();
		writeGroupToCSV(fileWriter, labGroups);
		fileWriter.append(COMMA_DELIMITER);

		List<ICourseworkComponent> mainComponents = course.getMainComponents();
		writeCourseworkComponentToCSV(fileWriter, mainComponents);

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

	/**
	 * Writes group details to the CSV file
	 * @param fileWriter the fileWriter object
	 * @param groups the groups to be written
	 * @throws IOException
	 */
	private void writeGroupToCSV(FileWriter fileWriter, List<IGroup> groups) throws IOException{
		if (groups.size() != 0) {
			int index = 0;
			for (IGroup group : groups) {
				fileWriter.append(group.getGroupName());
				fileWriter.append(EQUAL_SIGN);
				fileWriter.append(String.valueOf(group.getAvailableVacancies()));
				fileWriter.append(EQUAL_SIGN);
				fileWriter.append(String.valueOf(group.getTotalSeats()));
				index++;
				if (index != groups.size()) {
					fileWriter.append(LINE_DELIMITER);
				}
			}
		} else {
			fileWriter.append("NULL");
		}
	}

	/**
	 * Writes CourseworkComponent details to the CSV file
	 * @param fileWriter the fileWriter object
	 * @param components the CourseworkComponents to be written
	 * @throws IOException
	 */
	private void writeCourseworkComponentToCSV(FileWriter fileWriter, List<ICourseworkComponent> components) throws IOException {
		if (components.size() != 0) {
			int index = 0;
			for (ICourseworkComponent mainComponent : components) {
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
				if (index != components.size()) {
					fileWriter.append(LINE_DELIMITER);
				}
			}
		} else {
			fileWriter.append("NULL");
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

	public void setCourseFileName(String path){
		courseFileName = path;
	}
}
