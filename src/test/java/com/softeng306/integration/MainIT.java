package com.softeng306.integration;

import com.softeng306.Main;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import static org.junit.Assert.*;

import org.junit.contrib.java.lang.system.ExpectedSystemExit;

public class MainIT {
    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();
    //        exit.expectSystemExit(); Use this line if system expects to exit by calling System.exit()

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;


    StringBuilder inputsBuilder;
    StringBuilder expectedOutput;
    InputStream sysInBackup;

    @Before
    public void setup() {
        // Set up a new string for series of inputs
        inputsBuilder = new StringBuilder();

        // Set up an output builder
        expectedOutput = new StringBuilder();

        // Switch the output stream for testing purposes
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void cleanup() {
        // Set the input, output and error streams back to original
        System.setIn(originalIn);
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void testMain(){
        inputsBuilder.append("11" + System.lineSeparator());

        preparingInputStream();
//        Main.main(new String[]{});

        System.out.println("abc");
        System.out.println();
        System.out.println("abc");

        expectedOutput.append(System.lineSeparator());
        expectedOutput.append("****************** Hello! Welcome to SOFTENG 306 Project 2! ******************");
        expectedOutput.append("Please note this application is not developed in The University of Auckland. All rights reserved for the original developers.");
        expectedOutput.append("Permission has been granted by the original developers to anonymize the code and use for education purposes.");
        expectedOutput.append("******************************************************************************************************************************");
        expectedOutput.append(System.lineSeparator());

        expectedOutput.append("************ I can help you with these functions: *************");
        expectedOutput.append(" 0. Print Options");
        expectedOutput.append(" 1. Add a student");
        expectedOutput.append(" 2. Add a course");
        expectedOutput.append(" 3. Register student for a course including tutorial/lab classes");
        expectedOutput.append(" 4. Check available slots in a class (vacancy in a class)");
        expectedOutput.append(" 5. Print student list by lecture, tutorial or laboratory session for a course");
        expectedOutput.append(" 6. Enter course assessment components weightage");
        expectedOutput.append(" 7. Enter coursework mark – inclusive of its components");
        expectedOutput.append(" 8. Enter exam mark");
        expectedOutput.append(" 9. Print course statistics");
        expectedOutput.append("10. Print student transcript");
        expectedOutput.append("11. Quit Main System");
        expectedOutput.append(System.lineSeparator());

        expectedOutput.append("Backing up data before exiting...");
        expectedOutput.append("********* Bye! Thank you for using Main! *********");
        expectedOutput.append(System.lineSeparator());
        expectedOutput.append("                 ######    #      #   #######                   ");
        expectedOutput.append("                 #    ##    #    #    #                         ");
        expectedOutput.append("                 #    ##     #  #     #                         ");
        expectedOutput.append("                 ######       ##      #######                   ");
        expectedOutput.append("                 #    ##      ##      #                         ");
        expectedOutput.append("                 #    ##      ##      #                         ");
        expectedOutput.append("                 ######       ##      #######                   ");
        expectedOutput.append(System.lineSeparator());

        assertEquals(expectedOutput.toString(), outContent.toString());
    }

    private void preparingInputStream(){
        ByteArrayInputStream in = new ByteArrayInputStream(inputsBuilder.toString().getBytes());
        System.setIn(in);
    }

    private void appendLine
}
