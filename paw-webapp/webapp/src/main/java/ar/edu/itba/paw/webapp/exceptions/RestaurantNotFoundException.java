package ar.edu.itba.paw.webapp.exceptions;

public class RestaurantNotFoundException extends RuntimeException {
    public RestaurantNotFoundException() {
        super("Restaurant does not exist");
    }
}
