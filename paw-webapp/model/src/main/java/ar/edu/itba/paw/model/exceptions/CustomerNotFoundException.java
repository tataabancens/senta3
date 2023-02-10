package ar.edu.itba.paw.model.exceptions;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException() {
        super("Customer does not exist");
    }
}
