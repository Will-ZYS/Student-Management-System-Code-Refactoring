package com.softeng306.Interfaces.Utils;

public interface IScannerSingleton {

    //TODO not sure if method should be static
    void refreshSystemIn();

    boolean hasNextInt();

    int nextInt();

    String nextLine();

    Double nextDouble();

    String next();

}
