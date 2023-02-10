package ar.edu.itba.paw.model.exceptions;

public class DishCategoryNotFoundException extends RuntimeException {
    public DishCategoryNotFoundException() {
        super("Dish category does not exist");
    }
}
