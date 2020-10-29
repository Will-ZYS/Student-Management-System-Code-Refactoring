package com.softeng306;

import java.util.List;

public interface IProfessorFileMgr {

    /**
     * Load all the professors' information from file into the system.
     *
     * @return a list of all the professors.
     */
    List<IProfessor> loadProfessors();
}
