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
import java.util.*;
import java.util.regex.Pattern;

public class MarkFileMgr extends FileMgr implements IMarkFileMgr {
	private static MarkFileMgr instance = null;

	/**
	 * The file name of markFile.csv.
	 */
	private String markFileName = "data/markFile.csv";

	/**
	 * The header of markFile.csv.
	 */
	private final String markHeader = "studentID,courseID,courseWorkMarks,totalMark";

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
		FileWriter fileWriter = null;
		try {
			fileWriter = initializeCSV(markFileName, markHeader);

			writeMarkToCSV(fileWriter, mark, false);
		} catch (Exception e) {
			System.out.println("Error in adding a mark to the file.");
			e.printStackTrace();
		} finally {
			try {
				Objects.requireNonNull(fileWriter).flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error occurs in flushing or closing the file.");
				e.printStackTrace();
			}
		}
	}

	/**
	 * private helper method to write marks back into the CSV file
	 * @param fileWriter the file writer to write
	 * @param mark the mark to be written to the CSV file
	 * @param isSubComponentInitialized true is subComponent is initialized
	 * @throws IOException throws IOException to the caller
	 */
	private void writeMarkToCSV(FileWriter fileWriter, IMark mark, boolean isSubComponentInitialized) throws IOException {

		writeToFile(fileWriter,mark.getStudent().getStudentID(),COMMA_DELIMITER);
		writeToFile(fileWriter,mark.getCourse().getCourseID(),COMMA_DELIMITER);

		Map<ICourseworkComponent, Double> courseworkMarks = mark.getCourseWorkMarks();
		if (!courseworkMarks.isEmpty()) {
			int index = 0;
			for (Map.Entry<ICourseworkComponent, Double> entry : courseworkMarks.entrySet()) {
				ICourseworkComponent key = entry.getKey();
				Double value = entry.getValue();
				if (key instanceof MainComponent) {

					writeToFile(fileWriter,key.getComponentName(),EQUAL_SIGN);
					writeToFile(fileWriter,String.valueOf(key.getComponentWeight()),EQUAL_SIGN);
					writeToFile(fileWriter,String.valueOf(value),EQUAL_SIGN);

					List<ICourseworkComponent> subComponents = key.getSubComponents();
					int subComponent_index = 0;
					for (ICourseworkComponent subComponent : subComponents) {
						fileWriter.append(subComponent.getComponentName());
						fileWriter.append(SLASH);
						fileWriter.append(String.valueOf(subComponent.getComponentWeight()));
						fileWriter.append(SLASH);

						if (isSubComponentInitialized) {
							// used by backUpMarks
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
						} else {
							// used by updateStudentMarks
							fileWriter.append(String.valueOf(0.0));
						}

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
		writeToFile(fileWriter,String.valueOf(mark.getTotalMark()),NEW_LINE_SEPARATOR);

	}

	/**
	 * Load all the student mark records from file into the system.
	 *
	 * @return a list of all the student mark records.
	 */
	public List<IMark> loadStudentMarks() {
		BufferedReader fileReader = null;
		List<IMark> marks = new ArrayList<>(0);
		ICourseFileMgr courseFileMgr = CourseFileMgr.getInstance();
		IStudentFileMgr studentFileMgr = StudentFileMgr.getInstance();
		try {
			String line;

			List<IStudent> students = studentFileMgr.loadStudents();
			List<ICourse> courses = courseFileMgr.loadCourses();

			fileReader = new BufferedReader(new FileReader(markFileName));
			//read the header to skip it
			fileReader.readLine();
			while ((line = fileReader.readLine()) != null) {
				IStudent currentStudent = null;
				ICourse currentCourse = null;

				Map<ICourseworkComponent, Double> courseWorkMarks = new HashMap<>(0);
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

					String[] eachCourseWorkMark = courseWorkMarksString.split(Pattern.quote(LINE_DELIMITER));

					// Get all the main components
					for (String s : eachCourseWorkMark) {
						thisCourseWorkMark = s.split(EQUAL_SIGN);

						List<ICourseworkComponent> subComponents = new ArrayList<>(0);
						Map<ICourseworkComponent, Double> subComponentMarks = new HashMap<>();
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
					double totalMark = Double.parseDouble(tokens[totalMarkIndex]);
					IMark mark = new Mark(currentStudent, currentCourse, courseWorkMarks, totalMark);
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

			fileWriter.append(markHeader);
			fileWriter.append(NEW_LINE_SEPARATOR);

			for (IMark mark : marks) {
				writeMarkToCSV(fileWriter, mark, true);
			}
		} catch (Exception e) {
			System.out.println("Error in adding a mark to the file.");
			e.printStackTrace();
		} finally {
			try {
				Objects.requireNonNull(fileWriter).flush();
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
