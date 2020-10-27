package com.softeng306.Entity;

import com.softeng306.Interfaces.Entity.ICourseworkComponent;

import java.util.List;

public abstract class CourseworkComponent implements ICourseworkComponent {
    /**
     * The name of this coursework.
     */
    private String componentName;
    /**
     * The weight of this course component.
     */
    private int componentWeight;

    /**
     * Creates a course work components with component name and component weight
     *
     * @param componentName   the name of this coursework component
     * @param componentWeight the weight of this coursework component
     */
    public CourseworkComponent(String componentName, int componentWeight) {
        this.componentName = componentName;
        this.componentWeight = componentWeight;
    }

    /**
     * Gets the component name
     *
     * @return the name of this component
     */
    public String getComponentName() { return this.componentName; }

    /**
     * Gets the weight of this component
     *
     * @return the weight of this component
     */
    public int getComponentWeight() {
        return this.componentWeight;
    }

    /**
     * Gets the sub components of this main component.
     * @return the sub components of this main component.
     */
    public abstract List<ICourseworkComponent> getSubComponents();
}
