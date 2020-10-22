package com.softeng306.integration;

import com.softeng306.Main;
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

        preparingInputStream();
        Main.main(new String[]{});

        CommonPrintingHelper.appendWelcomeMessage(expectedOutput);
        CommonPrintingHelper.appendMenu(expectedOutput);

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

        // Printing results
        expectedOutput.appendLine("Student named: Wongabe is added, with ID: U1234567A");
        expectedOutput.appendLine("Student List: ");
        expectedOutput.appendLine("| Student ID | Student Name | Student School | Gender | Year | GPA |");
        expectedOutput.appendLine(" U1734756J | Eric | SCSE | MALE | 1 | not available");
        expectedOutput.appendLine(" U1822832L | Chloe | SPMS | FEMALE | 3 | not available");
        expectedOutput.appendLine(" U1723456K | Ashley | SBS | MALE | 4 | not available");
        expectedOutput.appendLine(" U1234567J | Daniel | SCSE | MALE | 2 | not available");
        expectedOutput.appendLine(" U1822835D | Charlie | NIE | MALE | 3 | not available");
        expectedOutput.appendLine(" U1822836B | Gauss | ADM | MALE | 3 | not available");
        expectedOutput.appendLine(" U1822837K | Clara | HSS | FEMALE | 1 | not available");
        expectedOutput.appendLine(" U1822838J | Kevin | SCSE | MALE | 2 | not available");
        expectedOutput.appendLine(" U1822839K | Shannon | NBS | FEMALE | 1 | not available");
        expectedOutput.appendLine(" U1822840D | Shaun | WKWSCI | MALE | 3 | not available");
        expectedOutput.appendLine(" U1822841A | Steven | EEE | MALE | 4 | not available");
        expectedOutput.appendLine(" U1822842F | Stefan | NBS | MALE | 1 | not available");
        expectedOutput.appendLine(" U1822843I | Sebas | SCSE | MALE | 1 | not available");
        expectedOutput.appendLine(" U1822821E | Freddy | EEE | MALE | 1 | not available");
        expectedOutput.appendLine(" U1712312B | Leigh | EEE | MALE | 2 | not available");
        expectedOutput.appendLine(" U1812312D | Hakeem | SCSE | MALE | 1 | not available");
        expectedOutput.appendLine(" U1829392I | Kourtney | MAE | FEMALE | 1 | not available");
        expectedOutput.appendLine(" U1822023E | Elaina | MAE | FEMALE | 1 | not available");
        expectedOutput.appendLine(" U1829393K | Chloe | CBE | MALE | 1 | not available");
        expectedOutput.appendLine(" U1722744J | Chloe | SCSE | FEMALE | 2 | not available");
        expectedOutput.appendLine(" U1800001L | Eric | SCSE | MALE | 2 | not available");
        expectedOutput.appendLine(" U1234567L | Eric | SCSE | MALE | 2 | not available");
        expectedOutput.appendLine(" U1234567A | Wongabe | ECSE | MALE | 1 | not available");

        CommonPrintingHelper.appendMenu(expectedOutput);
        CommonPrintingHelper.appendByeMessage(expectedOutput);

        assertEquals(expectedOutput.toString(), outContent.toString());
    }


}
