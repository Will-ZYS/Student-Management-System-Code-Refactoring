package com.softeng306;

public class Scanner implements IScanner {

    private static java.util.Scanner scanner = null;
    private static Scanner instance = null;

    /**
     * Get the instance of the Scanner class.
     * @return the singleton instance
     */
    public static Scanner getInstance() {
        if (instance == null) {
            scanner = new java.util.Scanner(System.in);
            instance = new Scanner();
        }
        return instance;
    }

    /**
     * Made to reset the Scanner. Main purpose is to ensure consistent testing results
     */
    public void refreshSystemIn(){
        scanner = new java.util.Scanner(System.in);
    }

    // Wrapping Methods

    public boolean hasNextInt() {
        return scanner.hasNextInt();
    }

    public int nextInt() {
        return  scanner.nextInt();
    }

    public String nextLine() {
        return scanner.nextLine();
    }

    public Double nextDouble() {
        return scanner.nextDouble();
    }

    public String next() {
        return scanner.next();
    }
}
