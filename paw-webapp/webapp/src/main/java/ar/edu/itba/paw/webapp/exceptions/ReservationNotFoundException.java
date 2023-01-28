package ar.edu.itba.paw.webapp.exceptions;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException() {
        super("Reservation does not exist");
    }
}
