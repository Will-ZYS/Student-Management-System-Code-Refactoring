package com.softeng306.unit;


import com.softeng306.Managers.ValidationMgr;
import org.junit.Assert;
import org.junit.Test;


/**
 * Most Likely to be changed as we will be refactoring the ValidationMgr class
 * This Test class is just a place holder for unit test,
 * so we can copy paste the format and structure.
 */
public class ValidationMgrTest {

    @Test
    public void testCheckDepartmentValidation(){
        ValidationMgr vmgr = new ValidationMgr();
        Assert.assertTrue(vmgr.checkDepartmentValidation("ECSE"));
    }

}
