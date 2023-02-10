package ar.edu.itba.paw.model.exceptions;

public class LongParseException extends RuntimeException{
    public LongParseException(String input){
        super(input + " is not a number");
    }
}
