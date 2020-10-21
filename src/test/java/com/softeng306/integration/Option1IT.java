package com.softeng306.integration;

import com.softeng306.Main;
import com.softeng306.helper.StringBuilderPlus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.Assert.*;

public class Option1IT {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    private StringBuilder inputsBuilder;
    private StringBuilderPlus expectedOutput;

    @Before
    public void setup() {
        // Set up a new string for series of inputs
        inputsBuilder = new StringBuilder();

        // Set up an output builder
        expectedOutput = new StringBuilderPlus();

        // Switch the output stream for testing purposes
//        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void cleanup() {
        // Set the input, output and error streams back to original
        System.setIn(originalIn);
        System.setOut(originalOut);
        System.setErr(originalErr);
        Main.scanner = new Scanner(originalIn);
    }

    private void preparingInputStream(){
        ByteArrayInputStream in = new ByteArrayInputStream(inputsBuilder.toString().getBytes());
        System.setIn(in);
        Main.scanner = new Scanner(System.in);
    }

    @Test
    public void testAddStudentAuto(){
        // Store current student list as string

        inputsBuilder.append("1").append(System.lineSeparator());
        inputsBuilder.append("1").append(System.lineSeparator()); // Enter ID manually
        inputsBuilder.append("U1234567A").append(System.lineSeparator()); // Student ID
        inputsBuilder.append("Wongabe").append(System.lineSeparator()); // Name
        inputsBuilder.append("ECSE").append(System.lineSeparator()); // School
        inputsBuilder.append("MALE").append(System.lineSeparator()); // Gender
        inputsBuilder.append("1").append(System.lineSeparator()); // School year
        inputsBuilder.append("11").append(System.lineSeparator()); // School year

        System.out.println(inputsBuilder.toString());

        preparingInputStream();
        Main.main(new String[]{});

        expectedOutput.appendLine();
        expectedOutput.appendLine("****************** Hello! Welcome to SOFTENG 306 Project 2! ******************");
        expectedOutput.appendLine("Please note this application is not developed in The University of Auckland. All rights reserved for the original developers.");
        expectedOutput.appendLine("Permission has been granted by the original developers to anonymize the code and use for education purposes.");
        expectedOutput.appendLine("******************************************************************************************************************************");
        expectedOutput.appendLine();

        expectedOutput.appendLine("************ I can help you with these functions: *************");
        expectedOutput.appendLine(" 0. Print Options");
        expectedOutput.appendLine(" 1. Add a student");
        expectedOutput.appendLine(" 2. Add a course");
        expectedOutput.appendLine(" 3. Register student for a course including tutorial/lab classes");
        expectedOutput.appendLine(" 4. Check available slots in a class (vacancy in a class)");
        expectedOutput.appendLine(" 5. Print student list by lecture, tutorial or laboratory session for a course");
        expectedOutput.appendLine(" 6. Enter course assessment components weightage");
        expectedOutput.appendLine(" 7. Enter coursework mark – inclusive of its components");
        expectedOutput.appendLine(" 8. Enter exam mark");
        expectedOutput.appendLine(" 9. Print course statistics");
        expectedOutput.appendLine("10. Print student transcript");
        expectedOutput.appendLine("11. Quit Main System");
        expectedOutput.appendLine();

        expectedOutput.appendLine("Enter your choice, let me help you:");

        expectedOutput.appendLine("addStudent is called");
        expectedOutput.appendLine("Choose the way you want to add a student:");
        expectedOutput.appendLine("1. Manually input the student ID.");
        expectedOutput.appendLine("2. Let the system self-generate the student ID.");
        expectedOutput.appendLine("Please input your choice:");

        expectedOutput.appendLine("The student ID should follow:");
        expectedOutput.appendLine("Length is exactly 9");
        expectedOutput.appendLine("Start with U (Undergraduate)");
        expectedOutput.appendLine("End with a uppercase letter between A and L");
        expectedOutput.appendLine("Seven digits in the middle");
        expectedOutput.appendLine();
        expectedOutput.appendLine("Give this student an ID: ");

        expectedOutput.appendLine("Enter student Name: ");

        expectedOutput.appendLine("Enter student's school (uppercase): ");
        expectedOutput.appendLine("Enter -h to print all the schools.");

        expectedOutput.appendLine("Enter student gender (uppercase): ");
        expectedOutput.appendLine("Enter -h to print all the genders.");

        expectedOutput.appendLine("Enter student's school year (1-4) : ");

        expectedOutput.appendLine("********* Bye! Thank you for using Main! *********");
        expectedOutput.appendLine();
        expectedOutput.appendLine("                 ######    #      #   #######                   ");
        expectedOutput.appendLine("                 #    ##    #    #    #                         ");
        expectedOutput.appendLine("                 #    ##     #  #     #                         ");
        expectedOutput.appendLine("                 ######       ##      #######                   ");
        expectedOutput.appendLine("                 #    ##      ##      #                         ");
        expectedOutput.appendLine("                 #    ##      ##      #                         ");
        expectedOutput.appendLine("                 ######       ##      #######                   ");
        expectedOutput.appendLine();

        assertEquals(expectedOutput.toString(), outContent.toString());
    }

}
