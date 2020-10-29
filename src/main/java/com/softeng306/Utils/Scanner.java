package com.softeng306.Utils;

public class Scanner {

    private static java.util.Scanner scanner = null;
    private static Scanner instance = null;

    /**
     * 
     * @return
     */
    public static Scanner getInstance() {
        if (instance == null) {
            scanner = new java.util.Scanner(System.in);
            instance = new Scanner();
        }
        return instance;
    }

    public static void refreshSystemIn(){
        scanner = new java.util.Scanner(System.in);
    }

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
