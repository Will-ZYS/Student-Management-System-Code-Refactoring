package com.softeng306.integration;

import com.softeng306.Main;
import com.softeng306.helper.CommonPrintingHelper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Option2IT extends IntegrationTestBase{

    @Test
    public void testAddACourse(){
        // ============= PREPARING THE INPUTS ===========================
        inputsBuilder.append("2").append(System.lineSeparator());
        inputsBuilder.append("SE2020").append(System.lineSeparator());
        inputsBuilder.append("Improving Refactoring SKills").append(System.lineSeparator());
        inputsBuilder.append("20").append(System.lineSeparator());
        inputsBuilder.append("10").append(System.lineSeparator());
        inputsBuilder.append("ECSE").append(System.lineSeparator());
        inputsBuilder.append("-h").append(System.lineSeparator());
        inputsBuilder.append("CORE").append(System.lineSeparator());

        inputsBuilder.append("1").append(System.lineSeparator());
        inputsBuilder.append("2").append(System.lineSeparator());
        inputsBuilder.append("LectureGroup1").append(System.lineSeparator());
        inputsBuilder.append("20").append(System.lineSeparator());

        inputsBuilder.append("1").append(System.lineSeparator());
        inputsBuilder.append("2").append(System.lineSeparator());
        inputsBuilder.append("Tutorial1").append(System.lineSeparator());
        inputsBuilder.append("20").append(System.lineSeparator());

        inputsBuilder.append("2").append(System.lineSeparator());
        inputsBuilder.append("2").append(System.lineSeparator());
        inputsBuilder.append("Lab1").append(System.lineSeparator());
        inputsBuilder.append("10").append(System.lineSeparator());
        inputsBuilder.append("Lab2").append(System.lineSeparator());
        inputsBuilder.append("10").append(System.lineSeparator());

        inputsBuilder.append("-h").append(System.lineSeparator());
        inputsBuilder.append("P1234569D").append(System.lineSeparator());

        inputsBuilder.append("2").append(System.lineSeparator());
        inputsBuilder.append("11").append(System.lineSeparator());



        // ============= SENDING THE INPUTS & RUNNING THE SYSTEM=================
        preparingInputStream();
        Main.main(new String[]{});

        // ======================== PREPARING OUTPUT ======================
        CommonPrintingHelper.appendWelcomeMessage(expectedOutput);
        CommonPrintingHelper.appendMenu(expectedOutput);
        expectedOutput.appendLine("Give this course an ID: ");
        expectedOutput.appendLine("Enter course Name: ");
        expectedOutput.appendLine("Enter the total vacancy of this course: ");
        expectedOutput.appendLine("Enter number of academic unit(s): ");
        expectedOutput.appendLine("Enter course's department (uppercase): ");
        expectedOutput.appendLine("Enter -h to print all the departments.");
        expectedOutput.appendLine("Enter course type (uppercase): ");
        expectedOutput.appendLine("Enter -h to print all the course types.");
        expectedOutput.appendLine("1: CORE");
        expectedOutput.appendLine("2: MPE");
        expectedOutput.appendLine("3: GREPEBM");
        expectedOutput.appendLine("4: GREPELA");
        expectedOutput.appendLine("5: GREPESTS");
        expectedOutput.appendLine("6: UE");
        expectedOutput.appendLine("Enter the number of lecture groups: ");
        expectedOutput.appendLine("Enter the weekly lecture hour for this course: ");
        expectedOutput.appendLine("Give a name to the lecture group");
        expectedOutput.appendLine("Enter a group Name: ");
        expectedOutput.appendLine("Enter this lecture group's capacity: ");
        expectedOutput.appendLine("Enter the number of tutorial groups:");
        expectedOutput.appendLine("Enter the weekly tutorial hour for this course: ");
        expectedOutput.appendLine("Give a name to the tutorial group");
        expectedOutput.appendLine("Enter a group Name: ");
        expectedOutput.appendLine("Enter this tutorial group's capacity: ");
        expectedOutput.appendLine("Enter the number of lab groups: ");
        expectedOutput.appendLine("Enter the weekly lab hour for this course: ");
        expectedOutput.appendLine("Give a name to this lab group");
        expectedOutput.appendLine("Enter a group Name: ");
        expectedOutput.appendLine("Enter this lab group's capacity: ");
        expectedOutput.appendLine("Give a name to this lab group");
        expectedOutput.appendLine("Enter a group Name: ");
        expectedOutput.appendLine("Enter this lab group's capacity: ");

        expectedOutput.appendLine("Enter the ID for the professor in charge please:");
        expectedOutput.appendLine("Enter -h to print all the professors in ECSE.");
        expectedOutput.appendLine("P1234561A");
        expectedOutput.appendLine("P1234563B");
        expectedOutput.appendLine("P1234564M");
        expectedOutput.appendLine("P1234565Y");
        expectedOutput.appendLine("P1234566L");
        expectedOutput.appendLine("P1234568K");
        expectedOutput.appendLine("P1234569D");
        expectedOutput.appendLine("Create course components and set component weightage now?");
        expectedOutput.appendLine("1. Yes");
        expectedOutput.appendLine("2. Not yet");
        expectedOutput.appendLine("Course SE2020 is added, but assessment components are not initialized.");
        expectedOutput.appendLine("Course List: ");
        expectedOutput.appendLine("| Course ID | Course Name | Professor in Charge |");
        expectedOutput.appendLine("| SE2001 | Algorithm | Reza Shahamiri |");
        expectedOutput.appendLine("| SE2004 | Human Computer Interface | Oliver Sinnen |");
        expectedOutput.appendLine("| SE2005 | Operating System | Reza Shahamiri |");
        expectedOutput.appendLine("| SE2007 | Database | Ewan Tempero |");
        expectedOutput.appendLine("| SE0001 | ENS | Reza Shahamiri |");
        expectedOutput.appendLine("| SE1006 | COA | Ewan Tempero |");
        expectedOutput.appendLine("| SE2002 | OODP | Reza Shahamiri |");
        expectedOutput.appendLine("| SE2006 | Software Engineering | Ewan Tempero |");
        expectedOutput.appendLine("| SE3005 | AI | Oliver Sinnen |");
        expectedOutput.appendLine("| SE2020 | Improving Refactoring SKills | Nitish Patel |");
        expectedOutput.appendLine();

        CommonPrintingHelper.appendMenu(expectedOutput);
        CommonPrintingHelper.appendByeMessage(expectedOutput);

        assertEquals(expectedOutput.toString(), outContent.toString());
    }

}
