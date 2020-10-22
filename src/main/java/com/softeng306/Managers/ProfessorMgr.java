package com.softeng306.Managers;

import com.softeng306.Entity.Professor;
import com.softeng306.Interfaces.Entity.IProfessor;

import java.util.Scanner;

/**
 * Manages all the professor related operations
 *

 */
public class ProfessorMgr {
    private Scanner scanner = new Scanner(System.in);

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
            if (ValidationMgr.checkValidProfIDInput(profID)) {
                if (ValidationMgr.checkProfExists(profID) == null) {
                    break;
                }
            }
        }

        String profName;
        while (true) {
            System.out.println("Enter the professor's name: ");
            profName = scanner.nextLine();
            if (ValidationMgr.checkValidPersonNameInput(profName)) {
                break;
            }
        }

        IProfessor professor = new Professor(profID, profName);
        while (true) {
            System.out.println("Enter professor's Department: ");
            System.out.println("Enter -h to print all the departments.");
            department = scanner.nextLine();
            while (department.equals("-h")) {
                HelpInfoMgr.getAllDepartment();
                department = scanner.nextLine();
            }

            if (ValidationMgr.checkDepartmentValidation(department)) {
                professor.setProfDepartment(department);
                break;
            }
        }


        return professor;
    }

}
