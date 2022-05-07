package ar.edu.itba.paw.webapp.exceptions;

public class OrderByException extends RuntimeException{
    public OrderByException(String input){
        super("Can not order by " + input);
    }
}
