package com.softeng306.Database;

import com.softeng306.Entity.MainComponent;
import com.softeng306.Entity.Mark;
import com.softeng306.Entity.SubComponent;
import com.softeng306.Interfaces.Database.ICourseFileMgr;
import com.softeng306.Interfaces.Database.IMarkFileMgr;
import com.softeng306.Interfaces.Database.IStudentFileMgr;
import com.softeng306.Interfaces.Entity.ICourse;
import com.softeng306.Interfaces.Entity.ICourseworkComponent;
import com.softeng306.Interfaces.Entity.IMark;
import com.softeng306.Interfaces.Entity.IStudent;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class MarkFileMgr extends FILEMgrAbstract implements IMarkFileMgr {
	private static MarkFileMgr instance = null;
	private ICourseFileMgr courseFileMgr = CourseFileMgr.getInstance();
	private IStudentFileMgr studentFileMgr = StudentFileMgr.getInstance();

	/**
	 * The file name of markFile.csv.
	 */
	private String markFileName = "data/markFile.csv";

	/**
	 * The header of markFile.csv.
	 */
	private final String mark_HEADER = "studentID,courseID,courseWorkMarks,totalMark";

	/**
	 * The index of studentID in markFile.csv.
	 */
	private final int studentIdIndexInMarks = 0;

	/**
	 * The index of courseID in markFile.csv.
	 */
	private final int courseIdIndexInMarks = 1;

	/**
	 * The index of courseWorkMark in markFile.csv..
	 */
	private final int courseWorkMarksIndex = 2;

	/**
	 * The index of totalMark in markFile.csv.
	 */
	private final int totalMarkIndex = 3;

	/**
	 * Writes a new student mark record into the file.
	 *
	 * @param mark mark to be updated into the file
	 */
	public void updateStudentMarks(IMark mark) {
		File file;
		FileWriter fileWriter = null;
		try {
			file = new File(markFileName);
			//initialize file header if have not done so
			fileWriter = new FileWriter(markFileName, true);
			if (file.length() == 0) {
				fileWriter.append(mark_HEADER);
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
			fileWriter.append(mark.getStudent().getStudentID());
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(mark.getCourse().getCourseID());
			fileWriter.append(COMMA_DELIMITER);
			HashMap<ICourseworkComponent, Double> courseworkMarks = mark.getCourseWorkMarks();
			if (!courseworkMarks.isEmpty()) {
				int index = 0;
				for (Map.Entry<ICourseworkComponent, Double> entry : courseworkMarks.entrySet()) {
					ICourseworkComponent key = entry.getKey();
					Double value = entry.getValue();
					if (key instanceof MainComponent) {
						fileWriter.append(key.getComponentName());
						fileWriter.append(EQUAL_SIGN);
						fileWriter.append(String.valueOf(key.getComponentWeight()));
						fileWriter.append(EQUAL_SIGN);
						fileWriter.append(String.valueOf(value));
						fileWriter.append(EQUAL_SIGN);
						ArrayList<ICourseworkComponent> subComponents = key.getSubComponents();
						int subComponent_index = 0;
						for (ICourseworkComponent subComponent : subComponents) {
							fileWriter.append(subComponent.getComponentName());
							fileWriter.append(SLASH);
							fileWriter.append(String.valueOf(subComponent.getComponentWeight()));
							fileWriter.append(SLASH);
							fileWriter.append(String.valueOf(0.0));
							subComponent_index++;
							if (subComponent_index != subComponents.size()) {
								fileWriter.append(EQUAL_SIGN);
							}
						}
					}
					index++;
					if (index != courseworkMarks.size() && (key instanceof MainComponent)) {
						fileWriter.append(LINE_DELIMITER);
					}
				}
			} else {
				fileWriter.append("NULL");
			}
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(mark.getTotalMark()));
			fileWriter.append(NEW_LINE_SEPARATOR);
		} catch (Exception e) {
			System.out.println("Error in adding a mark to the file.");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error occurs in flushing or closing the file.");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Load all the student mark records from file into the system.
	 *
	 * @return an array list of all the student mark records.
	 */
	public ArrayList<IMark> loadStudentMarks() {
		BufferedReader fileReader = null;
		ArrayList<IMark> marks = new ArrayList<>(0);
		try {
			String line;

			ArrayList<IStudent> students = studentFileMgr.loadStudents();
			ArrayList<ICourse> courses = courseFileMgr.loadCourses();

			fileReader = new BufferedReader(new FileReader(markFileName));
			//read the header to skip it
			fileReader.readLine();
			while ((line = fileReader.readLine()) != null) {
				IStudent currentStudent = null;
				ICourse currentCourse = null;

				HashMap<ICourseworkComponent, Double> courseWorkMarks = new HashMap<>(0);
				String[] thisCourseWorkMark;

				String[] tokens = line.split(COMMA_DELIMITER);
				if (tokens.length > 0) {
					String studentID = tokens[studentIdIndexInMarks];

					for (IStudent student : students) {
						if (student.getStudentID().equals(studentID)) {
							currentStudent = student;
							break;
						}
					}

					String courseID = tokens[courseIdIndexInMarks];

					for (ICourse course : courses) {
						if (course.getCourseID().equals(courseID)) {
							currentCourse = course;
							break;
						}
					}

					String courseWorkMarksString = tokens[courseWorkMarksIndex];
//                    System.out.println("From File, This course work components is: " + courseWorkMarksString);

					String[] eachCourseWorkMark = courseWorkMarksString.split(Pattern.quote(LINE_DELIMITER));
					// Get all the main components
//                    System.out.println("From the file: " + eachCourseWorkMark.length + " main components.");

					for (int i = 0; i < eachCourseWorkMark.length; i++) {
						thisCourseWorkMark = eachCourseWorkMark[i].split(EQUAL_SIGN);

						ArrayList<ICourseworkComponent> subComponents = new ArrayList<>(0);
						HashMap<ICourseworkComponent, Double> subComponentMarks = new HashMap<>();
						for (int j = 3; j < thisCourseWorkMark.length; j++) {
							if (thisCourseWorkMark[3].equals("")) {
								break;
							}
							String[] thisSubComponent = thisCourseWorkMark[j].split(SLASH);
							subComponents.add(new SubComponent(thisSubComponent[0], Integer.parseInt(thisSubComponent[1])));
							subComponentMarks.put(new SubComponent(thisSubComponent[0], Integer.parseInt(thisSubComponent[1])), Double.parseDouble(thisSubComponent[2]));
						}

						courseWorkMarks.put(new MainComponent(thisCourseWorkMark[0], Integer.parseInt(thisCourseWorkMark[1]), subComponents), Double.parseDouble(thisCourseWorkMark[2]));
						// Put sub component
						for (Map.Entry<ICourseworkComponent, Double> entry : subComponentMarks.entrySet()) {
							ICourseworkComponent subComponent = entry.getKey();
							Double subComponentResult = entry.getValue();
							courseWorkMarks.put(subComponent, subComponentResult);
						}
					}
					Double totalMark = Double.parseDouble(tokens[totalMarkIndex]);
					IMark mark = new Mark(currentStudent, currentCourse, courseWorkMarks, totalMark);
//                    System.out.println();
//                    System.out.println("Loaded mark...");
//                    System.out.println("Student ID: " + mark.getStudent().getStudentID() + " Student name: " + mark.getStudent().getStudentName());
//                    System.out.println("Course ID: " + mark.getCourse().getCourseID() + " Course name: " + mark.getCourse().getCourseName());
//                    for (HashMap.Entry<CourseworkComponent, Double> entry : mark.getCourseWorkMarks().entrySet()) {
//                        System.out.println("Course Components: " + entry.getKey().getComponentName());
//                        System.out.println("Course Component weightage " + entry.getKey().getComponentWeight());
//                    }
//                    System.out.println();
					marks.add(mark);
				}
			}
		} catch (Exception e) {
			System.out.println("Error occurs when loading student marks.");
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				System.out.println("Error occurs when closing the fileReader.");
				e.printStackTrace();
			}
		}
		return marks;
	}

	/**
	 * Backs up all the changes of student mark records made into the file.
	 *
	 * @param marks marks to be backed up into file
	 */

	public void backUpMarks(List<IMark> marks) {
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(markFileName);

			fileWriter.append(mark_HEADER);
			fileWriter.append(NEW_LINE_SEPARATOR);

			for (IMark mark : marks) {
				fileWriter.append(mark.getStudent().getStudentID());
				fileWriter.append(COMMA_DELIMITER);

				fileWriter.append(mark.getCourse().getCourseID());
				fileWriter.append(COMMA_DELIMITER);

				if (!mark.getCourseWorkMarks().isEmpty()) {
					int index = 0;
					for (Map.Entry<ICourseworkComponent, Double> entry : mark.getCourseWorkMarks().entrySet()) {
						ICourseworkComponent key = entry.getKey();
						Double value = entry.getValue();
						if (key instanceof MainComponent) {
							fileWriter.append(key.getComponentName());
							fileWriter.append(EQUAL_SIGN);
							fileWriter.append(String.valueOf(key.getComponentWeight()));
							fileWriter.append(EQUAL_SIGN);
							fileWriter.append(String.valueOf(value));
							fileWriter.append(EQUAL_SIGN);
							ArrayList<ICourseworkComponent> subComponents = key.getSubComponents();
							int subComponent_index = 0;
							for (ICourseworkComponent subComponent : subComponents) {
								fileWriter.append(subComponent.getComponentName());
								fileWriter.append(SLASH);
								fileWriter.append(String.valueOf(subComponent.getComponentWeight()));
								fileWriter.append(SLASH);
								String subComponentName = subComponent.getComponentName();
								double subComponentMark = 0d;
								for (Map.Entry<ICourseworkComponent, Double> subEntry : mark.getCourseWorkMarks().entrySet()) {
									ICourseworkComponent subKey = subEntry.getKey();
									Double subValue = subEntry.getValue();
									if (subKey instanceof SubComponent && subKey.getComponentName().equals(subComponentName)) {
										subComponentMark = subValue;
										break;
									}
								}
								fileWriter.append(String.valueOf(subComponentMark));
								subComponent_index++;
								if (subComponent_index != subComponents.size()) {
									fileWriter.append(EQUAL_SIGN);
								}
							}
						}
						index++;
						if (index != mark.getCourseWorkMarks().size() && (key instanceof MainComponent)) {
							fileWriter.append(LINE_DELIMITER);
						}
					}
				} else {
					fileWriter.append("NULL");
				}
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(mark.getTotalMark()));
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
		} catch (Exception e) {
			System.out.println("Error in adding a mark to the file.");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error occurs in flushing or closing the file.");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Get the instance of the MarkFileMgr class.
	 * @return the singleton instance.
	 */

	public static MarkFileMgr getInstance() {
		if (instance == null) {
			instance = new MarkFileMgr();
		}
		return instance;
	}

	public void setMarkFileName(String markFileName) {
		this.markFileName = markFileName;
	}
}