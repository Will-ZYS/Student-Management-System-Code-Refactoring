package com.softeng306.integration;

import com.softeng306.Main;
import com.softeng306.helper.CommonPrintingHelper;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class Option4IT extends IntegrationTestBase{

    @Test
    public void testCheckAvailableSlotsInClass(){
        try {
            // ============= PREPARING THE INPUTS ===========================
            inputsBuilder.append("4").append(System.lineSeparator());
            inputsBuilder.append("SE2006").append(System.lineSeparator()); // Student ID
            inputsBuilder.append("11").append(System.lineSeparator()); // Exit
            //======================================================================

            // ======================== PREPARING OUTPUT ======================
            CommonPrintingHelper.appendWelcomeMessage(expectedOutput);
            CommonPrintingHelper.appendMenu(expectedOutput);

            expectedOutput.appendLine("checkAvailableSlots is called");
            expectedOutput.appendLine("Enter course ID (-h to print all the course ID):");

            expectedOutput.appendLine("SE2006 Software Engineering (Available/Total): 100/100");

            expectedOutput.appendLine("--------------------------------------------");
            expectedOutput.appendLine("Lecture group LEC1 (Available/Total): 100/100");
            expectedOutput.appendLine();
            expectedOutput.appendLine("Tutorial group TUT1 (Available/Total):  50/50");
            expectedOutput.appendLine("Tutorial group TUT2 (Available/Total):  50/50");
            expectedOutput.appendLine();
            expectedOutput.appendLine("Lab group LAB1 (Available/Total): 100/100");
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