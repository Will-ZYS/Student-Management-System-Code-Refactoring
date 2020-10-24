package com.softeng306.Interfaces.Database;

import com.softeng306.Database.ProfessorFileMgr;
import com.softeng306.Interfaces.Entity.IProfessor;

import java.util.ArrayList;

public interface IProfessorFileMgr {

    /**
     * Writes a new professor information into the file.
     *
     * @param professor professor to be added into file
     */
    void writeProfIntoFile(IProfessor professor);

    /**
     * Load all the professors' information from file into the system.
     *
     * @return an array list of all the professors.
     */
    ArrayList<IProfessor> loadProfessors();
}
