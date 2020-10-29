package com.softeng306.integration;

import com.softeng306.Database.*;
import com.softeng306.Interfaces.Entity.*;
import com.softeng306.Utils.Scanner;
import com.softeng306.helper.CSVHelper;
import com.softeng306.helper.StringBuilderPlus;
import org.junit.After;
import org.junit.Before;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class IntegrationTestBase {

    protected final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    protected final InputStream originalIn = System.in;
    protected final PrintStream originalOut = System.out;

    protected List<IStudent> students;
    protected List<ICourse> courses;
    protected List<ICourseRegistration> courseRegistrations;
    protected List<IMark> marks;
    protected List<IProfessor> professors;

    protected StringBuilder inputsBuilder;
    protected StringBuilderPlus expectedOutput;

    protected CSVHelper csvHelper = new CSVHelper();

    @Before
    public void setup() {
        // Set up a new string for series of inputs
        inputsBuilder = new StringBuilder();

        // Set up an output builder
        expectedOutput = new StringBuilderPlus();

        // Switch the output stream for testing purposes
        System.setOut(new PrintStream(outContent));

        // Reverting all files before test
        csvHelper.revertAll();

        // Set the fileMGRs csv file path to the test ones
        csvHelper.initialiseFileMgrs();

        // Initialise the original for database arraylists
        students = StudentFileMgr.getInstance().loadStudents();
        courses = CourseFileMgr.getInstance().loadCourses();
        courseRegistrations = CourseRegistrationFileMgr.getInstance().loadCourseRegistration();
        marks = MarkFileMgr.getInstance().loadStudentMarks();
        professors = ProfessorFileMgr.getInstance().loadProfessors();

        // Set the new ones into the Database arraylists
        Database.getInstance().setStudents(new ArrayList<>(students));
        Database.getInstance().setCourses(new ArrayList<>(courses));
        Database.getInstance().setCourseRegistrations(new ArrayList<>(courseRegistrations));
        Database.getInstance().setMarks(new ArrayList<>(marks));
        Database.getInstance().setProfessors(new ArrayList<>(professors));

    }

    @After
    public void cleanup() {
        // Set the input, output and error streams back to original
        System.setIn(originalIn);
        System.setOut(originalOut);
        Scanner.getInstance().refreshSystemIn();

        // Reverting all files before test
        csvHelper.revertAll();

        Database.getInstance().setStudents(new ArrayList<>(students));
        Database.getInstance().setCourses(new ArrayList<>(courses));
        Database.getInstance().setCourseRegistrations(new ArrayList<>(courseRegistrations));
        Database.getInstance().setMarks(new ArrayList<>(marks));
        Database.getInstance().setProfessors(new ArrayList<>(professors));
    }

    protected void preparingInputStream(){
        ByteArrayInputStream in = new ByteArrayInputStream(inputsBuilder.toString().getBytes());
        System.setIn(in);
        Scanner.getInstance().refreshSystemIn();
    }

}
