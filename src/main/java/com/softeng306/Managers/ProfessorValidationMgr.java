package com.softeng306.Managers;

import com.softeng306.Database.Database;
import com.softeng306.Interfaces.Database.IDatabase;
import com.softeng306.Interfaces.Entity.IProfessor;
import com.softeng306.Utils.ScannerSingleton;
import com.softeng306.Interfaces.Managers.Validation.IProfessorValidationMgr;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Manages all the professor related operations
 *
 */
public class ProfessorValidationMgr implements IProfessorValidationMgr {
    private static ProfessorValidationMgr instance = null;

    public static ScannerSingleton scanner = ScannerSingleton.getInstance();

    /**
     * Checks whether this professor ID is used by other professors.
     * @param profID The inputted professor ID.
     * @return the existing professor or else null.
     */
    public IProfessor checkProfExists(String profID) {
        IDatabase database = Database.getInstance();
        List<IProfessor> anyProf = database.getProfessors().stream().filter(p->profID.equals(p.getProfID())).collect(Collectors.toList());

        if(anyProf.size() == 0){
            return null;
        }
        // System.out.println("Sorry. The professor ID is used. This professor already exists.");
        return anyProf.get(0);

    }

    /**
     * get the instance of the ProfessorValidationMgr class
     * @return the singleton instance
     */
    public static ProfessorValidationMgr getInstance() {
        if (instance == null) {
            instance = new ProfessorValidationMgr();
        }
        return instance;
    }
}