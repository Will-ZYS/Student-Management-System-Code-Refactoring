package com.softeng306.Database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public abstract class FILEMgrAbstract {

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
     * @throws IOException
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
}
