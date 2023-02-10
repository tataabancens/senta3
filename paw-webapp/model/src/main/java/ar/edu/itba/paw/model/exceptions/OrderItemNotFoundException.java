package ar.edu.itba.paw.model.exceptions;

public class OrderItemNotFoundException extends RuntimeException {
    public OrderItemNotFoundException() {
        super("Order item does not exist");
    }
}
