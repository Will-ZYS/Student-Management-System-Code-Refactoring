package com.softeng306.integration;

import com.softeng306.Main;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

public class MainIT {
    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();
    //        exit.expectSystemExit(); Use this line if system expects to exit by calling System.exit()

    StringBuilder sb;
    InputStream sysInBackup;

    @Before
    public void setup() {
        sb = new StringBuilder();
        sysInBackup = System.in; // backup System.in to restore it later
    }

    @Before
    public void cleanup() {
        System.setIn(sysInBackup);
    }

    @Test
    public void testMain(){
        sb.append("11" + System.lineSeparator());

        ByteArrayInputStream in = new ByteArrayInputStream(sb.toString().getBytes());
        System.setIn(in);

        Main main = new Main();
        main.main(new String[]{});

    }

}
