package ar.edu.itba.paw.model.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User does not exist");
    }
}
