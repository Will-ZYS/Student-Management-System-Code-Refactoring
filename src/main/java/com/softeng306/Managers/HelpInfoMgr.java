package com.softeng306.Managers;

import com.softeng306.Enum.*;

import com.softeng306.Interfaces.Managers.IHelpInfoMgr;

import java.util.*;

/**
 * Manages all the help information display in the system.
 */

public class HelpInfoMgr implements IHelpInfoMgr {
    private static HelpInfoMgr instance = null;

    /**
     * Gets all the departments as an array list.
     *
     * @return an array list of all the departments.
     */
    public ArrayList<String> getAllDepartment() {
        Set<Department> departmentEnumSet = EnumSet.allOf(Department.class);
        ArrayList<String> departmentStringList = new ArrayList<String>(0);
        Iterator iter = departmentEnumSet.iterator();
        while (iter.hasNext()) {
            departmentStringList.add(iter.next().toString());
        }
        return departmentStringList;

    }

    /**
     * Gets all the genders as an array list.
     *
     * @return an array list of all the genders.
     */
    public ArrayList<String> getAllGender() {
        Set<Gender> genderEnumSet = EnumSet.allOf(Gender.class);
        ArrayList<String> genderStringList = new ArrayList<String>(0);
        Iterator iter = genderEnumSet.iterator();
        while (iter.hasNext()) {
            genderStringList.add(iter.next().toString());
        }
        return genderStringList;
    }

    /**
     * Gets all the course types as an array list.
     *
     * @return an array list of all the course types.
     */
    public ArrayList<String> getAllCourseType() {
        Set<CourseType> courseTypeEnumSet = EnumSet.allOf(CourseType.class);
        ArrayList<String> courseTypeStringSet = new ArrayList<String>(0);
        Iterator iter = courseTypeEnumSet.iterator();
        while (iter.hasNext()) {
            courseTypeStringSet.add(iter.next().toString());
        }
        return courseTypeStringSet;
    }

    /**
     * Get the instance of the HelpInfoMgr class.
     * @return the singleton instance
     */

    public static HelpInfoMgr getInstance() {
        if (instance == null) {
            instance = new HelpInfoMgr();
        }
        return instance;
    }
}
