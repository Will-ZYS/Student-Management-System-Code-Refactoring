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

public class MainIT {

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
        System.setOut(new PrintStream(outContent));
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
    public void testMain(){
        inputsBuilder.append("11" + System.lineSeparator());

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

        expectedOutput.appendLine("Backing up data before exiting...");
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

    @Test
    public void testPrintOptions(){
        inputsBuilder.append("0" + System.lineSeparator());
        inputsBuilder.append("11" + System.lineSeparator());

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

        expectedOutput.appendLine("Backing up data before exiting...");
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
