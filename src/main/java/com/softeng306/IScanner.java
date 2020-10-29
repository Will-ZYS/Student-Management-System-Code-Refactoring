package com.softeng306;

public interface IScanner {

    /**
     * Made to reset the Scanner. Main purpose is to ensure consistent testing results
     */
    void refreshSystemIn();

    boolean hasNextInt();

    int nextInt();

    String nextLine();

    Double nextDouble();

    String next();

}
