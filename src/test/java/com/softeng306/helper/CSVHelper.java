package com.softeng306.helper;

import com.softeng306.Database.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.apache.commons.io.FileUtils;
/**
 * This class helps to revert the tested files back to their original state (i.e. before testing has occurred).
 * The files that are compared against in testing are in src/test/resources, while the original state files are
 * in src/test/resources/original. This class simply helps replaces the tested files with the original state.
 */
public class CSVHelper {
	/**
	 * The maven resources path where the testing files are found: src/test/resources
	 */
	private static final String testPath = "src" + File.separator + "test" + File.separator + "resources";

	/**
	 * original directory, containing the original states of all files
	 */
	private static final String originalDirectory = "original" + File.separator;

	private static final String sampleOutputDirectory = "sampleOutput" + File.separator;

	private static final String studentFileName = "studentFile.csv";

	private static final String courseFileName = "courseFile.csv";

	private static final String courseRegistrationFileName = "courseRegistrationFile.csv";

	private static final String markFileName = "markFile.csv";

	private static final String professorFileName = "professorFile.csv";

	/**
	 * Reverts all files back to their original state
	 */
	public void revertAll() {
		revertStudentState();
		revertCourseState();
		revertCourseRegistrationState();
		revertMarkState();
		revertProfessorState();
	}


	/**
	 * Helper method that sets the path of all csv files for FILEMgrs to be test versions
	 */
	public void initialiseFileMgrs() {
		String courseFilePath = testPath+File.separator+courseFileName;
		String courseRegistrationFilePath = testPath+File.separator+courseRegistrationFileName;
		String markFilePath = testPath+File.separator+markFileName;
		String professorFilePath = testPath+File.separator+professorFileName;
		String studentFilePath = testPath+File.separator+studentFileName;
		CourseFileMgr.getInstance().setCourseFileName(courseFilePath);
		MarkFileMgr.getInstance().setMarkFileName(markFilePath);
		ProfessorFileMgr.getInstance().setProfessorFileName(professorFilePath);
		StudentFileMgr.getInstance().setStudentFileName(studentFilePath);
		CourseRegistrationFileMgr.getInstance().setCourseRegistrationFileName(courseRegistrationFilePath);
	}

	/**
	 * Testing helper method that reverts the courseFile.csv back to its original state.
	 */
	public void revertCourseState() {
		Path source = Paths.get(testPath, originalDirectory, courseFileName);
		Path target = Paths.get(testPath, courseFileName);

		revertState(source, target);
	}

	/**
	 * Testing helper method that reverts the courseRegistrationFile.csv back to its original state.
	 */
	public void revertCourseRegistrationState() {
		Path source = Paths.get(testPath, originalDirectory, courseRegistrationFileName);
		Path target = Paths.get(testPath, courseRegistrationFileName);

		revertState(source, target);
	}

	/**
	 * Testing helper method that reverts the markFile.csv back to its original state.
	 */
	public void revertMarkState() {
		Path source = Paths.get(testPath, originalDirectory, markFileName);
		Path target = Paths.get(testPath, markFileName);

		revertState(source, target);
	}

	/**
	 * Testing helper method that reverts the professorFile.csv back to its original state.
	 */
	public void revertProfessorState() {
		Path source = Paths.get(testPath, originalDirectory, professorFileName);
		Path target = Paths.get(testPath, professorFileName);

		revertState(source, target);
	}

	/**
	 * Testing helper method that reverts the studentFile.csv back to its original state.
	 */
	public void revertStudentState() {
		Path source = Paths.get(testPath, originalDirectory, studentFileName);
		Path target = Paths.get(testPath, studentFileName);

		revertState(source, target);
	}

	/**
	 * Helper method that does the replacement of the tested files.
	 * @param source - the original file being copied
	 * @param target - the file that will be replaced
	 */
	private void revertState(Path source, Path target) {
		try {
			Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * File Getter for Test csv files
	 * @param toBeTested - the file being tested
	 */
	public File getFileToBeTested(String toBeTested) {
		Path toBeTestedPath = Paths.get(testPath, toBeTested);
		return toBeTestedPath.toFile();
	}

	/**
	 * File Getter for Sample output files
	 * @param sampleOutputs - the file in sample outputs to be compared against
	 */
	public File getFileSampleOutput(String sampleOutputs) {
		Path sampleOutputsPath = Paths.get(testPath, sampleOutputDirectory, sampleOutputs);
		return sampleOutputsPath.toFile();
	}
}
