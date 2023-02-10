package ar.edu.itba.paw.model.exceptions;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException() {
        super("Reservation does not exist");
    }
}
