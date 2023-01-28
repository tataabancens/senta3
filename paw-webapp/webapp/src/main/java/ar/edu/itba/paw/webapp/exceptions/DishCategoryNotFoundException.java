package ar.edu.itba.paw.webapp.exceptions;

public class DishCategoryNotFoundException extends RuntimeException {
    public DishCategoryNotFoundException() {
        super("Dish category does not exist");
    }
}
