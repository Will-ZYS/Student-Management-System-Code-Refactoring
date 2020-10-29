package com.softeng306;

/**
 * Defines the type of groups
 * Used in CourseMgr and CourseRegistrationMgr
 */
public enum GroupType {
    LECTURE("lecture"),
    TUTORIAL("tutorial"),
    LAB("lab");

    private final String name;
    GroupType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
