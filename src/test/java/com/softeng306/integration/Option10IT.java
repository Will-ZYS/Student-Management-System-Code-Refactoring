package com.softeng306.integration;

import com.softeng306.Main;
import com.softeng306.helper.CommonPrintingHelper;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class Option10IT extends IntegrationTestBase{
    @Test
    public void testPrintStudentTranscriptNotAvailable(){
        try {
            // ============= PREPARING THE INPUTS ===========================
            inputsBuilder.append("1").append(System.lineSeparator());
            inputsBuilder.append("1").append(System.lineSeparator()); // Enter ID manually
            inputsBuilder.append("U1234567A").append(System.lineSeparator()); // Student ID
            inputsBuilder.append("Wongabe").append(System.lineSeparator()); // Name
            inputsBuilder.append("ECSE").append(System.lineSeparator()); // School
            inputsBuilder.append("MALE").append(System.lineSeparator()); // Gender
            inputsBuilder.append("1").append(System.lineSeparator()); // School year

            inputsBuilder.append("10").append(System.lineSeparator());
            inputsBuilder.append("U1234567A").append(System.lineSeparator()); // Course ID
            inputsBuilder.append("11").append(System.lineSeparator()); // Exit
            //======================================================================

            // ======================== PREPARING OUTPUT ======================
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
            expectedOutput.appendLine("Enter Student ID (-h to print all the student ID):");
            expectedOutput.appendLine("------ No transcript ready for this student yet ------");
            CommonPrintingHelper.appendMenu(expectedOutput);
            CommonPrintingHelper.appendByeMessage(expectedOutput);
            //======================================================================

            // ============= SENDING THE INPUTS & RUNNING THE SYSTEM=================
            preparingInputStream();
            Main.main(new String[]{});
            //======================================================================

            assertEquals(expectedOutput.toString(), outContent.toString());
        } catch (NoSuchElementException e) {
            assertEquals(expectedOutput.toString(), outContent.toString());
            fail();
        }
    }

    @Test
    public void testPrintStudentTranscript(){
        try {
            // ============= PREPARING THE INPUTS ===========================
            inputsBuilder.append("10").append(System.lineSeparator());
            inputsBuilder.append("U1822838J").append(System.lineSeparator()); // Course ID
            inputsBuilder.append("11").append(System.lineSeparator()); // Exit
            //======================================================================

            // ======================== PREPARING OUTPUT ======================
            CommonPrintingHelper.appendWelcomeMessage(expectedOutput);
            CommonPrintingHelper.appendMenu(expectedOutput);
            expectedOutput.appendLine("Enter Student ID (-h to print all the student ID):");

            expectedOutput.appendLine("----------------- Official Transcript ------------------");
            expectedOutput.appendLine("Student Name: Kevin\tStudent ID: U1822838J");
            expectedOutput.appendLine("AU for this semester: 6");
            expectedOutput.appendLine();
            expectedOutput.appendLine("Course ID: SE2005\tCourse Name: Operating System");
            expectedOutput.appendLine("Main Assessment: Exam ----- (60%)");
            expectedOutput.appendLine("Main Assessment Total: 85.0");
            expectedOutput.appendLine();
            expectedOutput.appendLine("Main Assessment: Lab Assignments ----- (40%)");
            expectedOutput.appendLine("Sub Assessment: Oral Assessment -- (25% * 40%) --- Mark: 100.0");
            expectedOutput.appendLine("Sub Assessment: Lab Report -- (75% * 40%) --- Mark: 80.0");
            expectedOutput.appendLine("Main Assessment Total: 85.0");
            expectedOutput.appendLine();
            expectedOutput.appendLine("Course Total: 85.0");
            expectedOutput.appendLine();
            expectedOutput.appendLine("Course ID: SE0001\tCourse Name: ENS");
            expectedOutput.appendLine("Main Assessment: Exam ----- (60%)");
            expectedOutput.appendLine("Main Assessment Total: 0.0");
            expectedOutput.appendLine();
            expectedOutput.appendLine("Main Assessment: CourseWork ----- (40%)");
            expectedOutput.appendLine("Main Assessment Total: 0.0");
            expectedOutput.appendLine();
            expectedOutput.appendLine("Course Total: 0.0");
            expectedOutput.appendLine();
            expectedOutput.appendLine("GPA for this semester: 2.25");
            expectedOutput.appendLine("Advice: Study hard");
            expectedOutput.appendLine("------------------ End of Transcript -------------------");
            CommonPrintingHelper.appendMenu(expectedOutput);
            CommonPrintingHelper.appendByeMessage(expectedOutput);
            //======================================================================

            // ============= SENDING THE INPUTS & RUNNING THE SYSTEM=================
            preparingInputStream();
            Main.main(new String[]{});
            //======================================================================

            assertEquals(expectedOutput.toString().length(), outContent.toString().length());
        } catch (NoSuchElementException e) {
            assertEquals(expectedOutput.toString(), outContent.toString());
            fail();
        }
    }

}
