package ar.edu.itba.paw.model.exceptions;

public class RestaurantNotFoundException extends RuntimeException {
    public RestaurantNotFoundException() {
        super("Restaurant does not exist");
    }
}
