package com.softeng306.Database;

import com.softeng306.Entity.Professor;
import com.softeng306.Interfaces.Database.IProfessorFileMgr;
import com.softeng306.Interfaces.Entity.IProfessor;

import java.io.*;
import java.util.ArrayList;

public class ProfessorFileMgr extends FILEMgrAbstract implements IProfessorFileMgr {
	private static ProfessorFileMgr instance;
	/**
	 * The file name of professorFile.csv.
	 */
	private String professorFileName = "data/professorFile.csv";

	/**
	 * The header of professorFile.csv.
	 */
	private final String professor_HEADER = "professorID,professorName,profDepartment";

	/**
	 * The index of professor ID in professorFile.csv.
	 */
	private final int professorIdIndex = 0;

	/**
	 * The index of professor name in professorFile.csv.
	 */
	private final int professorNameIndex = 1;

	/**
	 * The index of professor department in professorFile.csv.
	 */
	private final int professorDepartmentIndex = 2;

	/**
	 * Writes a new professor information into the file.
	 *
	 * @param professor professor to be added into file
	 */
	public void writeProfIntoFile(IProfessor professor) {
		File file;
		FileWriter fileWriter = null;
		try {
			file = new File(professorFileName);
			//initialize file header if have not done so
			fileWriter = new FileWriter(professorFileName, true);
			if (file.length() == 0) {
				fileWriter.append(professor_HEADER);
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
			fileWriter.append(professor.getProfID());
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(professor.getProfName());
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(professor.getProfDepartment());
			fileWriter.append(NEW_LINE_SEPARATOR);
		} catch (Exception e) {
			System.out.println("Error in adding a professor to the file.");
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
	 * Load all the professors' information from file into the system.
	 *
	 * @return an array list of all the professors.
	 */
	public ArrayList<IProfessor> loadProfessors() {
		BufferedReader fileReader = null;
		ArrayList<IProfessor> professors = new ArrayList<>(0);
		try {
			String line;
			fileReader = new BufferedReader(new FileReader(professorFileName));
			//read the header to skip it
			fileReader.readLine();
			while ((line = fileReader.readLine()) != null) {
				String[] tokens = line.split(COMMA_DELIMITER);
				if (tokens.length > 0) {
					IProfessor professor = new Professor(tokens[professorIdIndex], tokens[professorNameIndex]);
					professor.setProfDepartment(tokens[professorDepartmentIndex]);
					professors.add(professor);
				}
			}
		} catch (Exception e) {
			System.out.println("Error occurs when loading professors.");
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				System.out.println("Error occurs when closing the fileReader.");
				e.printStackTrace();
			}
		}
		return professors;
	}

	/**
	 * Get the instance of the ProfessorFileMgr class.
	 * @return the singleton instance.
	 */

	public static ProfessorFileMgr getInstance() {
		if (instance == null) {
			instance = new ProfessorFileMgr();
		}
		return instance;
	}

	public void setProfessorFileName(String professorFileName) {
		this.professorFileName = professorFileName;
	}

}
