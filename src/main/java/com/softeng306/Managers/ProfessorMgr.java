package com.softeng306.Managers;

import com.softeng306.Entity.Professor;

import com.softeng306.Interfaces.Managers.IHelpInfoMgr;
import com.softeng306.Interfaces.Managers.IProfessorMgr;
import com.softeng306.Interfaces.Managers.IValidationMgr;
import com.softeng306.Interfaces.Entity.IProfessor;
import com.softeng306.Utils.ScannerSingleton;

import java.util.Scanner;

/**
 * Manages all the professor related operations
 *

 */
public class ProfessorMgr implements IProfessorMgr {
    private static ProfessorMgr instance = null;
    public static ScannerSingleton scanner = ScannerSingleton.getInstance();

    private IValidationMgr validationMgr = ValidationMgr.getInstance();
    private IHelpInfoMgr helpInfoMgr = HelpInfoMgr.getInstance();

    /**
     * Adds a professor.
     *
     * @return a newly added professor
     */
    public IProfessor addProfessor() {
        String department, profID;
        while (true) {
            System.out.println("Give this professor an ID: ");
            profID = scanner.nextLine();
            if (validationMgr.checkValidProfIDInput(profID)) {
                if (validationMgr.checkProfExists(profID) == null) {
                    break;
                }
            }
        }

        String profName;
        while (true) {
            System.out.println("Enter the professor's name: ");
            profName = scanner.nextLine();
            if (validationMgr.checkValidPersonNameInput(profName)) {
                break;
            }
        }

        IProfessor professor = new Professor(profID, profName);
        while (true) {
            System.out.println("Enter professor's Department: ");
            System.out.println("Enter -h to print all the departments.");
            department = scanner.nextLine();
            while (department.equals("-h")) {
                helpInfoMgr.getAllDepartment();
                department = scanner.nextLine();
            }

            if (validationMgr.checkDepartmentValidation(department)) {
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
