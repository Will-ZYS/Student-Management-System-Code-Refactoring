package com.softeng306.integration;

import com.softeng306.Main;
import com.softeng306.helper.CommonPrintingHelper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Option7IT extends IntegrationTestBase{

    @Test
    public void testEnterCourseworkMark(){
        // ============= PREPARING THE INPUTS ===========================
        inputsBuilder.append("7").append(System.lineSeparator());
        inputsBuilder.append("U1800001L").append(System.lineSeparator()); // Student ID
        inputsBuilder.append("SE0001").append(System.lineSeparator()); // Course ID
        inputsBuilder.append("1").append(System.lineSeparator()); // 1 for Coursework Weight in total
        inputsBuilder.append("50").append(System.lineSeparator()); // Enter mark for the courseWork
        inputsBuilder.append("11").append(System.lineSeparator()); // Exit

        // ============= SENDING THE INPUTS & RUNNING THE SYSTEM=================
        preparingInputStream();
        Main.main(new String[]{});

        // ======================== PREPARING OUTPUT ======================
        CommonPrintingHelper.appendWelcomeMessage(expectedOutput);
        CommonPrintingHelper.appendMenu(expectedOutput);

        expectedOutput.appendLine("enterCourseWorkMark is called");
        expectedOutput.appendLine("Enter Student ID (-h to print all the student ID):");

        expectedOutput.appendLine("Enter course ID (-h to print all the course ID):");

        expectedOutput.appendLine("Here are the choices you can have: ");
        expectedOutput.appendLine("1. CourseWork Weight in Total: 40.0%");
        expectedOutput.appendLine("2. Quit");

        expectedOutput.appendLine("Enter your choice");

        expectedOutput.appendLine("Enter the mark for this assessment:");

        expectedOutput.appendLine("The course work component is successfully set to: 50.0");
        expectedOutput.appendLine("The course total mark is updated to: 20.0");

        CommonPrintingHelper.appendMenu(expectedOutput);
        CommonPrintingHelper.appendByeMessage(expectedOutput);
        //======================================================================

        assertEquals(expectedOutput.toString(), outContent.toString());
    }

}
