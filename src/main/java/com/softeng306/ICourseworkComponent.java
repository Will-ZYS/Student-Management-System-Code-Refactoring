package com.softeng306;

import java.util.List;

public interface ICourseworkComponent {
    /**
     * Gets the component name
     *
     * @return the name of this component
     */
    String getComponentName();

    /**
     * Gets the weight of this component
     *
     * @return the weight of this component
     */
    int getComponentWeight();

    /**
     * Gets the sub components of this main component.
     * @return the sub components of this main component.
     */
    List<ICourseworkComponent> getSubComponents();
}
