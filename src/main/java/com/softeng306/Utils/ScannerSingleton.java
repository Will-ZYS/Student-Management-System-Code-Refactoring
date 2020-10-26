package com.softeng306.Utils;

import java.util.Scanner;

public class ScannerSingleton {

    private static Scanner scanner = null;
    private static ScannerSingleton instance = null;

    public static ScannerSingleton getInstance() {
        if (instance == null) {
            scanner = new Scanner(System.in);
            instance = new ScannerSingleton();
        }
        return instance;
    }

    public static void refreshSystemIn(){
        scanner = new Scanner(System.in);
    }

    public boolean hasNextInt() {
        return scanner.hasNextInt();
    }

    public int nextInt() {
        return  scanner.nextInt();
    }

    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    public String nextLine() {
        return scanner.nextLine();
    }

    public boolean hasNextDouble() {
        return scanner.hasNextDouble();
    }

    public Double nextDouble() {
        return scanner.nextDouble();
    }

    public boolean hasNext() {
        return scanner.hasNext();
    }

    public String next() {
        return scanner.next();
    }
}
