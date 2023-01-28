package ar.edu.itba.paw.webapp.exceptions;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException() {
        super("Customer does not exist");
    }
}
