package ar.edu.itba.paw.model.exceptions;

public class DishNotFoundException extends RuntimeException {
    public DishNotFoundException() {
        super("Dish does not exist");
    }
}
