package com.softeng306.Managers;

import com.softeng306.Entity.Professor;
import com.softeng306.Interfaces.Managers.IProfessorMgr;

import java.util.Scanner;

/**
 * Manages all the professor related operations
 *

 */
public class ProfessorMgr implements IProfessorMgr {
    private static ProfessorMgr instance = null;
    private Scanner scanner = new Scanner(System.in);

    /**
     * Adds a professor.
     *
     * @return a newly added professor
     */
    public Professor addProfessor() {
        String department, profID;
        while (true) {
            System.out.println("Give this professor an ID: ");
            profID = scanner.nextLine();
            if (ValidationMgr.getInstance().checkValidProfIDInput(profID)) {
                if (ValidationMgr.getInstance().checkProfExists(profID) == null) {
                    break;
                }
            }
        }

        String profName;
        while (true) {
            System.out.println("Enter the professor's name: ");
            profName = scanner.nextLine();
            if (ValidationMgr.getInstance().checkValidPersonNameInput(profName)) {
                break;
            }
        }

        Professor professor = new Professor(profID, profName);
        while (true) {
            System.out.println("Enter professor's Department: ");
            System.out.println("Enter -h to print all the departments.");
            department = scanner.nextLine();
            while (department.equals("-h")) {
                HelpInfoMgr.getInstance().getAllDepartment();
                department = scanner.nextLine();
            }

            if (ValidationMgr.getInstance().checkDepartmentValidation(department)) {
                professor.setProfDepartment(department);
                break;
            }
        }


        return professor;
    }

    /**
     * get the instance of the ProfessorMgr class
     * @return the singleton instance
     */
    public static ProfessorMgr getInstance() {
        if (instance == null) {
            instance = new ProfessorMgr();
        }
        return instance;
    }
}
