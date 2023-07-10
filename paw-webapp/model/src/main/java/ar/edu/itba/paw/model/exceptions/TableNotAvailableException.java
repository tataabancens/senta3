package ar.edu.itba.paw.model.exceptions;

public class TableNotAvailableException extends RuntimeException {
    public TableNotAvailableException() {
        super("Table not available");
    }
}
