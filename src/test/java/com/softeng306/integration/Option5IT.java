package com.softeng306.integration;

import com.softeng306.Main;
import com.softeng306.helper.CommonPrintingHelper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Option5IT extends IntegrationTestBase{

    @Test
    public void testPrintStudentByClass(){

        inputsBuilder.append("5").append(System.lineSeparator());
        inputsBuilder.append("SE2006").append(System.lineSeparator()); // Student ID
        inputsBuilder.append("1").append(System.lineSeparator()); // Exit
        inputsBuilder.append("11").append(System.lineSeparator()); // Exit

        preparingInputStream();
        Main.main(new String[]{});

        CommonPrintingHelper.appendWelcomeMessage(expectedOutput);
        CommonPrintingHelper.appendMenu(expectedOutput);

        expectedOutput.appendLine("printStudent is called");
        expectedOutput.appendLine("Enter course ID (-h to print all the course ID):");

        expectedOutput.appendLine("Print student by: ");
        expectedOutput.appendLine("(1) Lecture group");
        expectedOutput.appendLine("(2) Tutorial group");
        expectedOutput.appendLine("(3) Lab group");

        expectedOutput.appendLine("------------------------------------------------------");
        expectedOutput.appendLine("No one has registered this course yet.");
        expectedOutput.appendLine("------------------------------------------------------");

        CommonPrintingHelper.appendMenu(expectedOutput);
        CommonPrintingHelper.appendByeMessage(expectedOutput);

        assertEquals(expectedOutput.toString(), outContent.toString());
    }

    @Test
    public void testRegisterStudentThenPrintStudentByClass(){

        inputsBuilder.append("3").append(System.lineSeparator());
        inputsBuilder.append("U1234567L").append(System.lineSeparator()); // Student ID
        inputsBuilder.append("ECSE").append(System.lineSeparator()); // Department course is in
        inputsBuilder.append("-h").append(System.lineSeparator()); // Department course is in
        inputsBuilder.append("SE2007").append(System.lineSeparator()); // Course ID
        inputsBuilder.append("1").append(System.lineSeparator()); // Lecture group
        inputsBuilder.append("1").append(System.lineSeparator()); // tutorial group

        inputsBuilder.append("5").append(System.lineSeparator());
        inputsBuilder.append("SE2007").append(System.lineSeparator()); // Student ID
        inputsBuilder.append("1").append(System.lineSeparator()); // Exit
        inputsBuilder.append("11").append(System.lineSeparator()); // Exit

        preparingInputStream();
        Main.main(new String[]{});

        CommonPrintingHelper.appendWelcomeMessage(expectedOutput);
        CommonPrintingHelper.appendMenu(expectedOutput);

        expectedOutput.appendLine("registerCourse is called");
        expectedOutput.appendLine("Enter Student ID (-h to print all the student ID):");

        expectedOutput.appendLine("Which department's courses are you interested? (-h to print all the departments)");

        expectedOutput.appendLine("Enter course ID (-h to print all the course ID):");
        expectedOutput.appendLine("SE2001");
        expectedOutput.appendLine("SE2004");
        expectedOutput.appendLine("SE2005");
        expectedOutput.appendLine("SE2007");
        expectedOutput.appendLine("SE0001");
        expectedOutput.appendLine("SE1006");
        expectedOutput.appendLine("SE2002");
        expectedOutput.appendLine("SE2006");
        expectedOutput.appendLine("SE3005");

        expectedOutput.appendLine("Student Eric with ID: U1234567L wants to register SE2007 Database");
        expectedOutput.appendLine("Here is a list of all the lecture groups with available slots:");
        expectedOutput.appendLine("1: Lec1 (49 vacancies)");
        expectedOutput.appendLine("Please enter an integer for your choice:");

        expectedOutput.appendLine("Here is a list of all the tutorial groups with available slots:");
        expectedOutput.appendLine("1: SS1 (20 vacancies)");
        expectedOutput.appendLine("2: SS2 (20 vacancies)");
        expectedOutput.appendLine("3: SS3 (20 vacancies)");
        expectedOutput.appendLine("4: SS4 (19 vacancies)");
        expectedOutput.appendLine("5: SS5 (20 vacancies)");
        expectedOutput.appendLine("Please enter an integer for your choice:");

        expectedOutput.appendLine("Course registration successful!");

        expectedOutput.appendLine("Student: Eric\tLecture Group: Lec1\tTutorial Group: SS1");

        CommonPrintingHelper.appendMenu(expectedOutput);

        expectedOutput.appendLine("printStudent is called");
        expectedOutput.appendLine("Enter course ID (-h to print all the course ID):");

        expectedOutput.appendLine("Print student by: ");
        expectedOutput.appendLine("(1) Lecture group");
        expectedOutput.appendLine("(2) Tutorial group");
        expectedOutput.appendLine("(3) Lab group");

        expectedOutput.appendLine("------------------------------------------------------");
        expectedOutput.appendLine("Lecture group : Lec1");
        expectedOutput.appendLine("Student Name: Eric Student ID: U1800001L");
        expectedOutput.appendLine("Student Name: Eric Student ID: U1234567L");
        expectedOutput.appendLine();
        expectedOutput.appendLine("------------------------------------------------------");

        CommonPrintingHelper.appendMenu(expectedOutput);
        CommonPrintingHelper.appendByeMessage(expectedOutput);

        assertEquals(expectedOutput.toString(), outContent.toString());
    }

}