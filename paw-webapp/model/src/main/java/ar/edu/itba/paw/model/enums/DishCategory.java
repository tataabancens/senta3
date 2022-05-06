package ar.edu.itba.paw.model.enums;

import java.util.Arrays;
import java.util.List;

public enum DishCategory {
    ALL_DAY_BREAKFAST("ALL_DAY_BREAKFAST"),
    MAIN_DISH("MAIN_DISHES"),
    SANDWICHES("SANDWICHES"),
    DRINKS("DRINKS");

    private String description;

    DishCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static List<DishCategory> getAsList() {
        return Arrays.asList(DishCategory.values());
    }

    @Override
    public String toString() {
        return String.format("%s", getDescription());
    }
}
