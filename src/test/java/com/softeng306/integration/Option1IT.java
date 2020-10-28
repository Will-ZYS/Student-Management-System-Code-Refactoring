package com.softeng306.integration;


import com.softeng306.Main;
import com.softeng306.helper.CommonPrintingHelper;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.Assert.*;
public class Option1IT extends IntegrationTestBase{

    @Test
    public void testAddStudent(){
        // Store current student list as string

        inputsBuilder.append("1").append(System.lineSeparator());
        inputsBuilder.append("1").append(System.lineSeparator()); // Enter ID manually
        inputsBuilder.append("U1234567A").append(System.lineSeparator()); // Student ID
        inputsBuilder.append("Wongabe").append(System.lineSeparator()); // Name
        inputsBuilder.append("ECSE").append(System.lineSeparator()); // School
        inputsBuilder.append("MALE").append(System.lineSeparator()); // Gender
        inputsBuilder.append("1").append(System.lineSeparator()); // School year
        inputsBuilder.append("11").append(System.lineSeparator()); // Exit
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
        try {
            assertEquals("The files differ!",
                    FileUtils.readFileToString(csvHelper.getFileToBeTested("studentFile.csv"), "utf-8").trim(),
                    FileUtils.readFileToString(csvHelper.getFileSampleOutput("studentFileAddedStudentWongabe.csv"), "utf-8").trim());

        } catch (IOException e) {
            System.err.println("File Not found error - Most likely to do with sample output not being found.");
        }
    }

    @Test
    public void testAddExistingStudent(){
        // Store current student list as string

        inputsBuilder.append("1").append(System.lineSeparator());
        inputsBuilder.append("1").append(System.lineSeparator()); // Enter ID manually
        inputsBuilder.append("U1234567A").append(System.lineSeparator()); // Student ID
        inputsBuilder.append("Wongabe").append(System.lineSeparator()); // Name
        inputsBuilder.append("ECSE").append(System.lineSeparator()); // School
        inputsBuilder.append("MALE").append(System.lineSeparator()); // Gender
        inputsBuilder.append("1").append(System.lineSeparator()); // School year

        inputsBuilder.append("1").append(System.lineSeparator());
        inputsBuilder.append("1").append(System.lineSeparator()); // Enter ID manually
        inputsBuilder.append("U1234567A").append(System.lineSeparator()); // Student ID
        inputsBuilder.append("U1234567B").append(System.lineSeparator()); // Student ID
        inputsBuilder.append("WongabesWifu").append(System.lineSeparator()); // Name
        inputsBuilder.append("ECSE").append(System.lineSeparator()); // School
        inputsBuilder.append("FEMALE").append(System.lineSeparator()); // Gender
        inputsBuilder.append("2").append(System.lineSeparator()); // School year

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

        expectedOutput.appendLine("Sorry. The student ID is used. This student already exists.");
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
        expectedOutput.appendLine("Student named: WongabesWifu is added, with ID: U1234567B");
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
        expectedOutput.appendLine(" U1234567B | WongabesWifu | ECSE | FEMALE | 2 | not available");
        CommonPrintingHelper.appendMenu(expectedOutput);
        CommonPrintingHelper.appendByeMessage(expectedOutput);

        assertEquals(expectedOutput.toString(), outContent.toString());
    }


}
