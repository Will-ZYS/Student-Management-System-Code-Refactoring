package com.softeng306.Utils;

import com.softeng306.Interfaces.Utils.IScanner;

import java.io.InputStream;

public class Scanner extends java.util.Scanner implements IScanner {

    private static java.util.Scanner instance = null;

    public Scanner(InputStream source) {
        super(source);
    }

    /**
     * Get the instance of the ValidationMgr class.
     * @return the singleton instance
     */
    public static java.util.Scanner getInstance() {
        if (instance == null) {
            instance = new java.util.Scanner(System.in);
        }
        return instance;
    }
}
