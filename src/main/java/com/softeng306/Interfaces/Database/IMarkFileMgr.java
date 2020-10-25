package com.softeng306.Interfaces.Database;

import com.softeng306.Database.MarkFileMgr;
import com.softeng306.Interfaces.Entity.IMark;
import com.softeng306.Managers.CourseRegistrationMgr;

import java.util.ArrayList;
import java.util.List;

public interface IMarkFileMgr {

    /**
     * Writes a new student mark record into the file.
     *
     * @param mark mark to be updated into the file
     */
     void updateStudentMarks(IMark mark);

    /**
     * Load all the student mark records from file into the system.
     *
     * @return an array list of all the student mark records.
     */
     ArrayList<IMark> loadStudentMarks();

    /**
     * Backs up all the changes of student mark records made into the file.
     *
     * @param marks marks to be backed up into file
     */
     void backUpMarks(List<IMark> marks);
}
