package ar.edu.itba.paw.model.exceptions;

public class NoAvailableHoursFoundException extends RuntimeException {
    public NoAvailableHoursFoundException() {
        super("No available shift for that day");
    }
}
