package com.spacecomplexity.longboilife.building;

/**
 * Contains a list of all building category's for calculating student satisfaction.
 */
public enum BuildingCategory {
    ACCOMMODATION("Accommodation"),
    EDUCATIONAL("Educational"),
    FOOD("Food"),
    RECREATIONAL("Recreational"),
    ;

    private final String displayName;

    BuildingCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
