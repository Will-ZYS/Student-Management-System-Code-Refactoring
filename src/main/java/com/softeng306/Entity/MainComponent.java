package com.softeng306.Entity;

import com.softeng306.Interfaces.Entity.ICourseworkComponent;

import java.util.ArrayList;

/**
 * Represents a main assessment component of a course.
 * This class is a subclass of {@code CourseworkComponent}.
 * A course can have many main assessment components.

 */
public class MainComponent extends CourseworkComponent {

    /**
     * This main component's sub components.
     */
    private ArrayList<ICourseworkComponent> subComponents;

    /**
     * Creates a main component with component name, component weightage and sub components.
     * @param componentName the name of the assessment component
     * @param componentWeight the componentWeight of the assessment component
     * @param subComponents the sub components of the assessment component
     */
    public MainComponent(String componentName, int componentWeight, ArrayList<ICourseworkComponent> subComponents) {
        super(componentName, componentWeight);
        this.subComponents = subComponents;
    }

    /**
     * Gets the sub components of this main component.
     * @return the sub components of this main component.
     */
    @Override
    public ArrayList<ICourseworkComponent> getSubComponents() {
        return this.subComponents;
    }
}
