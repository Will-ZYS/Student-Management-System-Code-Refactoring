package com.softeng306.helper;

public class CommonPrintingHelper {

    public static void appendMenu(StringBuilderPlus expectedOutput){
        expectedOutput.appendLine("************ I can help you with these functions: *************");
        expectedOutput.appendLine(" 0. Print Options");
        expectedOutput.appendLine(" 1. Add a student");
        expectedOutput.appendLine(" 2. Add a course");
        expectedOutput.appendLine(" 3. Register student for a course including tutorial/lab classes");
        expectedOutput.appendLine(" 4. Check available slots in a class (vacancy in a class)");
        expectedOutput.appendLine(" 5. Print student list by lecture, tutorial or laboratory session for a course");
        expectedOutput.appendLine(" 6. Enter course assessment components weightage");
        expectedOutput.appendLine(" 7. Enter coursework mark – inclusive of its components");
        expectedOutput.appendLine(" 8. Enter exam mark");
        expectedOutput.appendLine(" 9. Print course statistics");
        expectedOutput.appendLine("10. Print student transcript");
        expectedOutput.appendLine("11. Quit Main System");
        expectedOutput.appendLine();
        expectedOutput.appendLine("Enter your choice, let me help you:");

    }

    public static void appendWelcomeMessage(StringBuilderPlus expectedOutput){
        expectedOutput.appendLine();
        expectedOutput.appendLine("****************** Hello! Welcome to SOFTENG 306 Project 2! ******************");
        expectedOutput.appendLine("Please note this application is not developed in The University of Auckland. All rights reserved for the original developers.");
        expectedOutput.appendLine("Permission has been granted by the original developers to anonymize the code and use for education purposes.");
        expectedOutput.appendLine("******************************************************************************************************************************");
        expectedOutput.appendLine();
    }

    public static void appendByeMessage(StringBuilderPlus expectedOutput) {

        expectedOutput.appendLine("Backing up data before exiting...");
        expectedOutput.appendLine("********* Bye! Thank you for using Main! *********");
        expectedOutput.appendLine();
        expectedOutput.appendLine("                 ######    #      #   #######                   ");
        expectedOutput.appendLine("                 #    ##    #    #    #                         ");
        expectedOutput.appendLine("                 #    ##     #  #     #                         ");
        expectedOutput.appendLine("                 ######       ##      #######                   ");
        expectedOutput.appendLine("                 #    ##      ##      #                         ");
        expectedOutput.appendLine("                 #    ##      ##      #                         ");
        expectedOutput.appendLine("                 ######       ##      #######                   ");
        expectedOutput.appendLine();
    }
}