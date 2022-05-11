package ar.edu.itba.paw.model.enums;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public enum DishCategory {
    ALL_DAY_BREAKFAST("ALL_DAY_BREAKFAST", "Desayuno"),
    MAIN_DISH("MAIN_DISH", "Principales"),
    SANDWICHES("SANDWICHES", "Sandwiches"),
    DRINKS("DRINKS", "Bebidas");

    private String description;
    private String spanishDescr;

    DishCategory(String description, String spanishDescr) {
        this.description = description;
        this.spanishDescr = spanishDescr;
    }

    public String getDescription() {
        return description;
    }
    public String getSpanishDescr() {
        return spanishDescr;
    }

    public static List<DishCategory> getAsList() {
        return Arrays.asList(DishCategory.values());
    }

    public static Map<DishCategory, String > getAsMap() {
        Map<DishCategory, String> toRet = new LinkedHashMap<>();
        for (DishCategory category: DishCategory.getAsList()) {
            toRet.put(category, category.getSpanishDescr());
        }
        return toRet;
    }

    @Override
    public String toString() {
        return String.format("%s", getDescription());
    }
}
