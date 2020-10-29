package com.softeng306;

public interface IGroupValidationMgr {
    /**
     * Checks whether the inputted group name is in the correct format.
     * @param groupName The inputted group name.
     * @return boolean indicates whether the inputted group name is valid.
     */
    boolean checkValidGroupNameInput(String groupName);
}
