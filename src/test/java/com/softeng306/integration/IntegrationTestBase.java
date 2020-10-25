package com.softeng306.integration;

import com.softeng306.Database.Database;
import com.softeng306.Interfaces.Entity.*;
import com.softeng306.Utils.ScannerSingleton;
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
    protected final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    protected final InputStream originalIn = System.in;
    protected final PrintStream originalOut = System.out;
    protected final PrintStream originalErr = System.err;

    protected List<IStudent> students = new ArrayList<>(Database.getInstance().getStudents());
    protected List<ICourse> courses = new ArrayList<>(Database.getInstance().getCourses());
    protected List<ICourseRegistration> courseRegistrations = new ArrayList<>(Database.getInstance().getCourseRegistrations());
    protected List<IMark> marks = new ArrayList<>(Database.getInstance().getMarks());
    protected List<IProfessor> professors = new ArrayList<>(Database.getInstance().getProfessors());

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
        System.setErr(new PrintStream(errContent));

        // Set the fileMGRs csv file path to the test ones
        csvHelper.initialiseFileMgrs();
    }

    @After
    public void cleanup() {
        // Set the input, output and error streams back to original
        System.setIn(originalIn);
        System.setOut(originalOut);
        System.setErr(originalErr);
        ScannerSingleton.refreshSystemIn();

        // Reverting all files after test
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
        ScannerSingleton.refreshSystemIn();
    }

}
