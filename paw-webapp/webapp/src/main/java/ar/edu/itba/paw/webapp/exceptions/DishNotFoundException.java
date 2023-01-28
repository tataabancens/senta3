package ar.edu.itba.paw.webapp.exceptions;

public class DishNotFoundException extends RuntimeException {
    public DishNotFoundException() {
        super("Dish does not exist");
    }
}
