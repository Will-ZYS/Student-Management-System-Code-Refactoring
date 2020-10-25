package com.softeng306.integration;

import com.softeng306.Main;
import com.softeng306.helper.CSVHelper;
import com.softeng306.helper.CommonPrintingHelper;
import com.softeng306.helper.StringBuilderPlus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class Option3IT {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    private StringBuilder inputsBuilder;
    private StringBuilderPlus expectedOutput;

    private CSVHelper csvHelper = new CSVHelper();

    @Before
    public void setup() {
        // Set up a new string for series of inputs
        inputsBuilder = new StringBuilder();

        // Set up an output builder
        expectedOutput = new StringBuilderPlus();

        // Switch the output stream for testing purposes
//        System.setOut(new PrintStream(outContent));
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
        Main.scanner = new Scanner(originalIn);

        // Reverting all files after test
        csvHelper.revertAll();
    }

    private void preparingInputStream(){
        ByteArrayInputStream in = new ByteArrayInputStream(inputsBuilder.toString().getBytes());
        System.setIn(in);
        Main.scanner = new Scanner(System.in);
    }

    @Test
    public void testRegisterStudentForCourse(){
        // Store current student list as string

        inputsBuilder.append("3").append(System.lineSeparator());
        inputsBuilder.append("U1234567L").append(System.lineSeparator()); // Student ID
        inputsBuilder.append("ECSE").append(System.lineSeparator()); // Department course is in
        inputsBuilder.append("SE2007").append(System.lineSeparator()); // Course ID
        inputsBuilder.append("1").append(System.lineSeparator()); // Lecture group
        inputsBuilder.append("1").append(System.lineSeparator()); // tutorial group
        inputsBuilder.append("11").append(System.lineSeparator()); // Exit

        preparingInputStream();
        Main.main(new String[]{});

        CommonPrintingHelper.appendWelcomeMessage(expectedOutput);
        CommonPrintingHelper.appendMenu(expectedOutput);

        expectedOutput.appendLine("registerCourse is called");
        expectedOutput.appendLine("Enter Student ID (-h to print all the student ID):");

        expectedOutput.appendLine("Which department's courses are you interested? (-h to print all the departments)");

        expectedOutput.appendLine("Enter course ID (-h to print all the course ID):");
        expectedOutput.appendLine("SE2001");
        expectedOutput.appendLine("SE2004");
        expectedOutput.appendLine("SE2005");
        expectedOutput.appendLine("SE2007");
        expectedOutput.appendLine("SE0001");
        expectedOutput.appendLine("SE1006");
        expectedOutput.appendLine("SE2002");
        expectedOutput.appendLine("SE2006");
        expectedOutput.appendLine("SE3005");

        expectedOutput.appendLine("Student Eric with ID: U1234567L wants to register SE2007 Database");
        expectedOutput.appendLine("Here is a list of all the lecture groups with available slots:");
        expectedOutput.appendLine("1: Lec1 (49 vacancies)");

        expectedOutput.appendLine("Here is a list of all the tutorial groups with available slots:");
        expectedOutput.appendLine("1: SS1 (20 vacancies)");
        expectedOutput.appendLine("2: SS2 (20 vacancies)");
        expectedOutput.appendLine("3: SS3 (20 vacancies)");
        expectedOutput.appendLine("4: SS4 (19 vacancies)");
        expectedOutput.appendLine("5: SS5 (20 vacancies)");
        expectedOutput.appendLine("Please enter an integer for your choice:");

        expectedOutput.appendLine("Course registration successful!");

        expectedOutput.appendLine("Student: Eric\tLecture Group: Lec1\tTutorial Group: SS1");

        CommonPrintingHelper.appendMenu(expectedOutput);
        CommonPrintingHelper.appendByeMessage(expectedOutput);

        assertEquals(expectedOutput.toString(), outContent.toString());
    }


}
