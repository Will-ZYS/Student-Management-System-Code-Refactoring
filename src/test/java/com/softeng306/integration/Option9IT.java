package com.softeng306.integration;

import com.softeng306.Main;
import com.softeng306.helper.CommonPrintingHelper;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class Option9IT extends IntegrationTestBase{

    @Test
    public void testPrintCourseStats(){
        try {
            // ============= PREPARING THE INPUTS ===========================
            inputsBuilder.append("9").append(System.lineSeparator());
            inputsBuilder.append("SE2007").append(System.lineSeparator()); // Course ID
            inputsBuilder.append("11").append(System.lineSeparator()); // Exit
            //======================================================================

            // ======================== PREPARING OUTPUT ======================
            CommonPrintingHelper.appendWelcomeMessage(expectedOutput);
            CommonPrintingHelper.appendMenu(expectedOutput);
            expectedOutput.appendLine("printCourseStatistics is called");
            expectedOutput.appendLine("Enter course ID (-h to print all the course ID):");

            expectedOutput.appendLine("*************** Course Statistic ***************");
            expectedOutput.appendLine("Course ID: SE2007\tCourse Name: Database");
            expectedOutput.appendLine("Course AU: 3");
            expectedOutput.appendLine();
            expectedOutput.appendLine("Total Slots: 50\tEnrolled Student: 1");
            expectedOutput.appendLine(String.format("Enrollment Rate: %4.2f %%\n", (double)2.00));

            expectedOutput.appendLine("Main Component: Coursework\tWeight: 40%\t Average: 93.0");
            expectedOutput.appendLine("Sub Component: Labwork\tWeight: 70% (in main component)\t Average: 90.0");
            expectedOutput.appendLine("Sub Component: Presentation\tWeight: 30% (in main component)\t Average: 100.0");
            expectedOutput.appendLine();
            expectedOutput.appendLine("Final Exam\tWeight: 60%\t Average: 88.0");
            expectedOutput.appendLine();
            expectedOutput.appendLine(String.format("Overall Performance: %4.2f \n", (double)90.00));
            expectedOutput.appendLine("***********************************************");
            expectedOutput.appendLine();

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

}
