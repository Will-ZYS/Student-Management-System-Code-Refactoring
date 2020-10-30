package com.softeng306.Database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * contains common fields which all FILEMgr's use
 */
public abstract class FileMgr {

    /**
     * The string of {@code COMMA_DELIMITER}.
     */
    protected static final String COMMA_DELIMITER = ",";

    /**
     * The string of {@code NEW_LINE_SEPARATOR}.
     */
    protected static final String NEW_LINE_SEPARATOR = "\n";

    /**
     * The string of {@code LINE_DELIMITER}.
     */
    protected static final String LINE_DELIMITER = "|";

    /**
     * The string of {@code EQUAL_SIGN}.
     */
    protected static final String EQUAL_SIGN = "=";

    /**
     * The string of {@code HYPHEN}.
     */
    protected static final String HYPHEN = "-";

    /**
     * The string of {@code SLASH}.
     */
    protected static final String SLASH = "/";

    /**
     * Initialises CSV FileWriter and headers
     * @param filename The CSV files
     * @param header The headers for the CSV file
     * @return fileWriter object for the CSV
     * @throws IOException throws IOException to the caller
     */
    protected FileWriter initializeCSV(String filename, String header) throws IOException {
        File file = new File(filename);
        //initialize file header if have not done so
        FileWriter fileWriter = new FileWriter(filename, true);
        if (file.length() == 0) {
            fileWriter.append(header);
            fileWriter.append(NEW_LINE_SEPARATOR);
        }
        return fileWriter;
    }

    /**
     * helper method to insert content into the file, alongside the appropriate symbol
     * @param fileWriter the file writer to write
     * @param content the string to be inserted
     * @param separator the separator to be inserted
     * @throws IOException throws IOException to the caller
     */
    protected void writeToFile(FileWriter fileWriter, String content, String separator)  throws IOException{
        fileWriter.append(content);
        fileWriter.append(separator);
    }
}
