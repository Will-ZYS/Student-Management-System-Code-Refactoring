package com.softeng306.Utils;

import com.softeng306.Interfaces.Utils.IScannerSingleton;

import java.util.Scanner;

/**
 * Scanner used to read the users input.
 */
public class ScannerSingleton implements IScannerSingleton {

    private static Scanner scanner = null;
    private static ScannerSingleton instance = null;

    public static ScannerSingleton getInstance() {
        if (instance == null) {
            scanner = new Scanner(System.in);
            instance = new ScannerSingleton();
        }
        return instance;
    }

    //TODO STATIC? For setting test to normal but using getInstance works
    public void refreshSystemIn(){
        scanner = new Scanner(System.in);
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
