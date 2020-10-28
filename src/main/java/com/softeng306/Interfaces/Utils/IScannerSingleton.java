package com.softeng306.Interfaces.Utils;

public interface IScannerSingleton {

    //TODO not sure if method should be static
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
