package com.softeng306.integration;

import com.softeng306.Main;
import com.softeng306.helper.CommonPrintingHelper;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class Option6IT extends IntegrationTestBase{

    @Test
    public void testEnterCourseComponentWeightage(){
        try {
            // ============= PREPARING THE INPUTS ===========================
            inputsBuilder.append("6").append(System.lineSeparator());
            inputsBuilder.append("SE2006").append(System.lineSeparator()); // Course ID
            inputsBuilder.append("1").append(System.lineSeparator()); // Have a final exam
            inputsBuilder.append("50").append(System.lineSeparator()); // Exam Weightage
            inputsBuilder.append("2").append(System.lineSeparator()); // 2 Main components to add

            inputsBuilder.append("component1").append(System.lineSeparator()); // name for the first component
            inputsBuilder.append("25").append(System.lineSeparator()); // component 1 weightage
            inputsBuilder.append("0").append(System.lineSeparator()); // component 1's number of sub components

            inputsBuilder.append("component2").append(System.lineSeparator()); // name for second component
            inputsBuilder.append("25").append(System.lineSeparator()); // component 2 weightage
            inputsBuilder.append("1").append(System.lineSeparator()); // component 2's number of sub components

            inputsBuilder.append("subcomponent1").append(System.lineSeparator()); // sub component name
            inputsBuilder.append("10").append(System.lineSeparator()); // sub component weighting, not valid

            inputsBuilder.append("subcomponent1").append(System.lineSeparator()); // sub component's name again
            inputsBuilder.append("100").append(System.lineSeparator()); // assign sub component's weighting

            inputsBuilder.append("11").append(System.lineSeparator()); // Exit
            //======================================================================

            // ======================== PREPARING OUTPUT ======================
            CommonPrintingHelper.appendWelcomeMessage(expectedOutput);
            CommonPrintingHelper.appendMenu(expectedOutput);

            expectedOutput.appendLine("enterCourseWorkComponentWeightage is called");
            expectedOutput.appendLine("Enter course ID (-h to print all the course ID):");

            expectedOutput.appendLine("Currently course SE2006 Software Engineering does not have any assessment component.");
            expectedOutput.appendLine("Does this course have a final exam? Enter your choice:");
            expectedOutput.appendLine("1. Yes! ");
            expectedOutput.appendLine("2. No, all CAs.");

            expectedOutput.appendLine("Please enter weight of the exam: ");

            expectedOutput.appendLine("Enter number of main component(s) to add:");

            expectedOutput.appendLine("Total weightage left to assign: 50");
            expectedOutput.appendLine("Enter main component 1 name: ");

            expectedOutput.appendLine("Enter main component 1 weightage: ");

            expectedOutput.appendLine("Enter number of sub component under main component 1:");

            expectedOutput.appendLine("Total weightage left to assign: 25");
            expectedOutput.appendLine("Enter main component 2 name: ");

            expectedOutput.appendLine("Enter main component 2 weightage: ");

            expectedOutput.appendLine("Enter number of sub component under main component 2:");

            expectedOutput.appendLine("Total weightage left to assign to sub component: 100");
            expectedOutput.appendLine("Enter sub component 1 name: ");

            expectedOutput.appendLine("Enter sub component 1 weightage: ");

            expectedOutput.appendLine("ERROR! sub component weightage does not tally to 100");
            expectedOutput.appendLine("You have to reassign!");
            expectedOutput.appendLine("Total weightage left to assign to sub component: 100");
            expectedOutput.appendLine("Enter sub component 1 name: ");

            expectedOutput.appendLine("Enter sub component 1 weightage: ");

            expectedOutput.appendLine("SE2006 Software Engineering components: ");
            expectedOutput.appendLine("    Exam : 50%");
            expectedOutput.appendLine("    component1 : 25%");
            expectedOutput.appendLine("    component2 : 25%");
            expectedOutput.appendLine("        subcomponent1 : 100%");

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
    public void testEnterCourseComponentWeightageAlreadySettled(){
        try {
            // ============= PREPARING THE INPUTS ===========================
            inputsBuilder.append("6").append(System.lineSeparator());
            inputsBuilder.append("SE2007").append(System.lineSeparator()); // Course ID
            inputsBuilder.append("11").append(System.lineSeparator()); // Exit
            //======================================================================

            // ======================== PREPARING OUTPUT ======================
            CommonPrintingHelper.appendWelcomeMessage(expectedOutput);
            CommonPrintingHelper.appendMenu(expectedOutput);

            expectedOutput.appendLine("enterCourseWorkComponentWeightage is called");
            expectedOutput.appendLine("Enter course ID (-h to print all the course ID):");

            expectedOutput.appendLine("Course Assessment has been settled already!");
            expectedOutput.appendLine("SE2007 Database components: ");
            expectedOutput.appendLine("    Exam : 60%");
            expectedOutput.appendLine("    Coursework : 40%");
            expectedOutput.appendLine("        Labwork : 70%");
            expectedOutput.appendLine("        Presentation : 30%");

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