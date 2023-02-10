package ar.edu.itba.paw.model.exceptions;

public class DateFormatException extends RuntimeException {
    public DateFormatException() {
        super("Date format must be YYYY-MM-DD");
    }
}
