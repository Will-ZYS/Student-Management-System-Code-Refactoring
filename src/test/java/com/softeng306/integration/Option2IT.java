package com.softeng306.integration;

import com.softeng306.Main;
import com.softeng306.helper.CommonPrintingHelper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Option2IT extends IntegrationTestBase{

    @Test
    public void testPrintStudentTranscript(){
        // ============= PREPARING THE INPUTS ===========================
        inputsBuilder.append("10").append(System.lineSeparator());
        inputsBuilder.append("U1822838J").append(System.lineSeparator()); // Course ID
        inputsBuilder.append("11").append(System.lineSeparator()); // Exit

        // ============= SENDING THE INPUTS & RUNNING THE SYSTEM=================
        preparingInputStream();
        Main.main(new String[]{});

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

        assertEquals(expectedOutput.toString(), outContent.toString());
    }

}
