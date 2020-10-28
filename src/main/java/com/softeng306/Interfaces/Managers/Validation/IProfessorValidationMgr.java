package com.softeng306.Interfaces.Managers.Validation;

import com.softeng306.Interfaces.Entity.IProfessor;

public interface IProfessorValidationMgr {

    /**
     * Checks whether this professor ID is used by other professors.
     * @param profID The inputted professor ID.
     * @return the existing professor or else null.
     */
    IProfessor checkProfExists(String profID);

}