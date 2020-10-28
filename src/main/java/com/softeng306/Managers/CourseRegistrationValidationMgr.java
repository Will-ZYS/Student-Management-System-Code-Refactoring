package com.softeng306.Managers;

import com.softeng306.Database.Database;
import com.softeng306.Interfaces.Database.IDatabase;
import com.softeng306.Interfaces.Entity.ICourseRegistration;
import com.softeng306.Interfaces.Managers.Validation.ICourseRegistrationValidationMgr;

import java.util.List;
import java.util.stream.Collectors;

public class CourseRegistrationValidationMgr implements ICourseRegistrationValidationMgr{
    private static CourseRegistrationValidationMgr instance = null;

    /**
     * Checks whether this course registration record exists.
     * @param studentID The inputted student ID.
     * @param courseID The inputted course ID.
     * @return the existing course registration record or else null.
     */
    public ICourseRegistration checkCourseRegistrationExists(String studentID, String courseID) {
        IDatabase database = Database.getInstance();
        List<ICourseRegistration> courseRegistrations = database.getCourseRegistrations().stream().filter(cr->studentID.equals(cr.getStudent().getStudentID())).filter(cr->courseID.equals(cr.getCourse().getCourseID())).collect(Collectors.toList());

        if(courseRegistrations.size() == 0){
            return null;
        }
        System.out.println("Sorry. This student already registers this course.");
        return courseRegistrations.get(0);

    }

    /**
     * get the instance of the CourseRegistrationValidationMgr class
     * @return the singleton instance
     */
    public static CourseRegistrationValidationMgr getInstance() {
        if (instance == null) {
            instance = new CourseRegistrationValidationMgr();
        }
        return instance;
    }
}
