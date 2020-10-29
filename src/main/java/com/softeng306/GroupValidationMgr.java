package com.softeng306;

import java.util.regex.Pattern;

public class GroupValidationMgr implements IGroupValidationMgr {

    private static GroupValidationMgr instance = null;


    /**
     * Checks whether the inputted group name is in the correct format.
     * @param groupName The inputted group name.
     * @return boolean indicates whether the inputted group name is valid.
     */
    public boolean checkValidGroupNameInput(String groupName) {
        String REGEX = "^[a-zA-Z0-9]+$";
        boolean valid =  Pattern.compile(REGEX).matcher(groupName).matches();
        if(!valid){
            System.out.println("Wrong format of group name.");
        }
        return valid;
    }

    /**
     * Get the instance of the GroupValidationMgr class.
     * @return the singleton instance
     */
    public static GroupValidationMgr getInstance() {
        if (instance == null) {
            instance = new GroupValidationMgr();
        }
        return instance;
    }

}
